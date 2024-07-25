package net.alastairwyse.applicationaccessclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.alastairwyse.applicationaccessclient.exceptions.DeserializationException;
import net.alastairwyse.applicationaccessclient.exceptions.ElementNotFoundException;
import net.alastairwyse.applicationaccessclient.exceptions.NotFoundException;
import net.alastairwyse.applicationaccessclient.models.HttpErrorResponse;
import net.alastairwyse.applicationaccessclient.HttpMethod;;

/**
 * Base for client classes which interface to AccessManager instances hosted as REST web APIs.
 * 
 * @param <TUser> The type of users in the AccessManager.
 * @param <TGroup> The type of groups in the AccessManager.
 * @param <TComponent> The type of components in the AccessManager.
 * @param <TAccess> The type of levels of access which can be assigned to an application component.
 */
public abstract class AccessManagerClientBase<TUser, TGroup, TComponent, TAccess> implements AutoCloseable {
    
    // TODO:
    //   Params
    //   Timeout?
    //   Headers
    //   Proxy setting
    //   Cert

    /** The client to use to connect. */
    protected HttpClient httpClient;
    /** The base URL for the hosted Web API. */
    protected URI baseUrl;
    /** Deserializer for HttpErrorResponse objects. */
    protected HttpErrorResponseJsonSerializer errorResponseDeserializer;
    /** Maps an HTTP status code to an Consumer which throws a matching Exception to the status code.  The Consumer accepts 1 parameter: the {@link HttpErrorResponse} representing the exception. */
    protected Map<Integer, Consumer<HttpErrorResponse>> statusCodeToExceptionThrowingActionMap;
    /** Used to deserialize DTO objects returned from the web API. */
    protected ObjectMapper objectMapper;
    /** A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances. */
    protected UniqueStringifier<TUser> userStringifier;
    /** A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances. */
    protected UniqueStringifier<TGroup> groupStringifier;
    /** A string converter for application components.  Used to convert strings sent to and received from the web API from/to TComponent instances. */
    protected UniqueStringifier<TComponent> applicationComponentStringifier;
    /** A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances. */
    protected UniqueStringifier<TAccess> accessLevelStringifier;
    /** Whether the HttpClient member was instantiated within the class constructor */
    protected Boolean httpClientInstantiatedInConstructor;

    /**
     * Constructs an AccessManagerClientBase.
     * 
     * @param baseUrl The base URL for the hosted Web API.
     * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
     * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
     * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     */
    public AccessManagerClientBase(
        URI baseUrl, 
        UniqueStringifier<TUser> userStringifier, 
        UniqueStringifier<TGroup> groupStringifier, 
        UniqueStringifier<TComponent> applicationComponentStringifier, 
        UniqueStringifier<TAccess> accessLevelStringifier
    ) {
        httpClient = HttpClient.newHttpClient();
        setBaseConstructorParameters(baseUrl, userStringifier, groupStringifier, applicationComponentStringifier, accessLevelStringifier);
        httpClientInstantiatedInConstructor = true;
    }

    /**
     * Constructs an AccessManagerClientBase.
     * 
     * @param httpClient The client to use to connect.
     * @param baseUrl The base URL for the hosted Web API.
     * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
     * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
     * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     */
    public AccessManagerClientBase(
        HttpClient httpClient, 
        URI baseUrl, 
        UniqueStringifier<TUser> userStringifier, 
        UniqueStringifier<TGroup> groupStringifier, 
        UniqueStringifier<TComponent> applicationComponentStringifier, 
        UniqueStringifier<TAccess> accessLevelStringifier
    ) {
        this.httpClient = httpClient;
        setBaseConstructorParameters(baseUrl, userStringifier, groupStringifier, applicationComponentStringifier, accessLevelStringifier);
        httpClientInstantiatedInConstructor = false;
    }

    //#region Private/Protected Methods

    /**
     * Sends an HTTP GET request, expecting a 200 status returned to indicate success, and attempting to deserialize the response body to the specified type.
     * 
     * @param <T> The type to deserialize the response body to. 
     * @param requestUrl The URL of the request.
     * @param returnType The type to deserialize the response to and return.
     * @return The response body deserialized to the specified type.
     * 
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    protected <T> T sendGetRequest(URI requestUrl, TypeReference<T> returnType) throws IOException, InterruptedException {

        Builder requestBuilder = HttpRequest.newBuilder(requestUrl).GET();
        setHttpRequestAcceptHeader(requestBuilder);
        HttpResponse<String> response = HttpClient.newHttpClient().send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            handleNonSuccessResponseStatus(HttpMethod.GET, requestUrl, response.statusCode(), response.body());
        }
        try {
            return objectMapper.readValue(response.body(), returnType);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(
                String.format(
                    "Failed to call URL '%s' with '%s' method.  Error deserializing response body from JSON to type.", 
                    requestUrl.toString(), 
                    HttpMethod.GET
                ), 
                e
            );
        }
    }

    /**
     * Sends an HTTP GET request, expecting either a 200 or 404 status returned, and converting the status to an equivalent boolean value.
     * 
     * @param requestUrl The URL of the request.
     * @return True in the case a 200 response status is received, or false in the case a 404 status is received.
     *
     * @exception RuntimeException If an unexpected response status was received.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    protected boolean sendGetRequestForContainsMethod(URI requestUrl) throws IOException, InterruptedException {

        boolean returnValue = false;
        Builder requestBuilder = HttpRequest.newBuilder(requestUrl).GET();
        setHttpRequestAcceptHeader(requestBuilder);
        HttpResponse<String> response = HttpClient.newHttpClient().send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        if (!((response.statusCode() != 200) || (response.statusCode() != 404))) {
            handleNonSuccessResponseStatus(HttpMethod.GET, requestUrl, response.statusCode(), response.body());
        }
        if (response.statusCode() == 404) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * Sends an HTTP POST request, expecting a 201 status returned to indicate success.
     * 
     * @param requestUrl The URL of the request.
     * 
     * @exception RuntimeException If a non-success response status was received.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    protected void sendPostRequest(URI requestUrl) throws IOException, InterruptedException {

        Builder requestBuilder = HttpRequest.newBuilder(requestUrl).POST(HttpRequest.BodyPublishers.noBody());
        setHttpRequestAcceptHeader(requestBuilder);
        HttpResponse<String> response = HttpClient.newHttpClient().send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            handleNonSuccessResponseStatus(HttpMethod.POST, requestUrl, response.statusCode(), response.body());
        }
    }

    /**
     * Sends an HTTP DELETE request, expecting a 200 status returned to indicate success.
     * 
     * @param requestUrl The URL of the request.
     * 
     * @exception RuntimeException If a non-success response status was received.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    protected void sendDeleteRequest(URI requestUrl) throws IOException, InterruptedException {

        Builder requestBuilder = HttpRequest.newBuilder(requestUrl).DELETE();
        setHttpRequestAcceptHeader(requestBuilder);
        HttpResponse<String> response = HttpClient.newHttpClient().send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            handleNonSuccessResponseStatus(HttpMethod.DELETE, requestUrl, response.statusCode(), response.body());
        }
    }

    /**
     * Performs setup for a minimal/common set of constructor parameters.
     * 
     * @param baseUrl The base URL for the hosted Web API.
     * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
     * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
     * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     */
    protected void setBaseConstructorParameters (
        URI baseUrl, 
        UniqueStringifier<TUser> userStringifier, 
        UniqueStringifier<TGroup> groupStringifier, 
        UniqueStringifier<TComponent> applicationComponentStringifier, 
        UniqueStringifier<TAccess> accessLevelStringifier
    ) {
        initializeBaseUrk(baseUrl);
        errorResponseDeserializer = new HttpErrorResponseJsonSerializer();
        initializeStatusCodeToExceptionThrowingActionMap();
        objectMapper = new ObjectMapper();
        this.userStringifier = userStringifier;
        this.groupStringifier = groupStringifier;
        this.applicationComponentStringifier = applicationComponentStringifier;
        this.accessLevelStringifier = accessLevelStringifier;
    }

    /**
     * Adds an appropriate path suffix to the specified 'baseUrl' constructor parameter.
     * @param baseUrl The base URL to initialize.
     */
    protected void initializeBaseUrk(URI baseUrl) {
        
        try {
            this.baseUrl = new URI(baseUrl.toString() + "api/v1/");
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Failed to append API suffix to base URL '%s'.", baseUrl.toString()), e);
        }
    }

    /**
     * Concatenates the specified path (with no leading forward slash) to the 'baseUrl' property and returns it as a new {@link URI} 
     * 
     * @param path The path to concatenate.
     * @return The concatenated URL.
     */
    protected URI appendPathToBaseUrl(String path) {
        try {
            return new URI(baseUrl + path);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to append path '%s' to base URL '%s'.", path, baseUrl.toString()), e);
        }
    }

    /**
     * Sets appropriate 'Accept' headers on the specified HTTP request builder.
     * 
     * @param httpClient The HTTP request builder to set the header(s) on.
     * @return The updated HTTP request builder.
     */
    protected Builder setHttpRequestAcceptHeader(Builder httpRequestBuilder)
    {
        final String acceptHeaderName = "Accept";
        final String acceptHeaderValue = "application/json";

        return httpRequestBuilder.setHeader(acceptHeaderName, acceptHeaderValue);
    }

    /**
     * Initializes the 'statusCodeToExceptionThrowingActionMap' member.
     */
    protected void initializeStatusCodeToExceptionThrowingActionMap() {

        statusCodeToExceptionThrowingActionMap = new HashMap<Integer, Consumer<HttpErrorResponse>>();
        statusCodeToExceptionThrowingActionMap.put(
            Integer.valueOf(500), // Internal Server Error
            (HttpErrorResponse httpErrorResponse) -> {  
                throw new RuntimeException(httpErrorResponse.getMessage());
            }
        );
        statusCodeToExceptionThrowingActionMap.put(
            Integer.valueOf(400), // Bad Request
            (HttpErrorResponse httpErrorResponse) -> {  
                if (httpErrorResponse.getCode().equals("ArgumentNullException")) {
                    throw new NullPointerException(httpErrorResponse.getMessage());
                }
                else {
                    throw new IllegalArgumentException(httpErrorResponse.getMessage());
                }
            }
        );
        statusCodeToExceptionThrowingActionMap.put(
            Integer.valueOf(404), // Not Found
            (HttpErrorResponse httpErrorResponse) -> {  

                if (httpErrorResponse.getCode() == "UserNotFoundException") {
                    String user = getHttpErrorResponseAttributeValue(httpErrorResponse, "User");
                    throw new ElementNotFoundException(httpErrorResponse.getMessage(), "User", user);
                }
                else if (httpErrorResponse.getCode() == "GroupNotFoundException") {
                    String group = getHttpErrorResponseAttributeValue(httpErrorResponse, "Group");
                    throw new ElementNotFoundException(httpErrorResponse.getMessage(), "Group", group);
                }
                else if (httpErrorResponse.getCode() == "EntityTypeNotFoundException") {
                    String entityType = getHttpErrorResponseAttributeValue(httpErrorResponse, "EntityType");
                    throw new ElementNotFoundException(httpErrorResponse.getMessage(), "EntityType", entityType);
                }
                else if (httpErrorResponse.getCode() == "EntityNotFoundException") {
                    String entity = getHttpErrorResponseAttributeValue(httpErrorResponse, "Entity");
                    throw new ElementNotFoundException(httpErrorResponse.getMessage(), "Entity", entity);
                }
                else {
                    String resourceId = getHttpErrorResponseAttributeValue(httpErrorResponse, "ResourceId");
                    throw new NotFoundException(httpErrorResponse.getMessage(), resourceId);
                }
            }
        );
    }

    /**
     * Handles receipt of a non-success HTTP response status, by converting the status and response body to an appropriate Exception and throwing that Exception.
     * 
     * @param method The {@link HttpMethod} used in the request which generated the response.
     * @param requestUrl The URL of the request which generated the response.
     * @param responseStatus The received HTTP response status.
     * @param responseBody The received response body.
     * 
     * @exception RuntimeException The exception generated from the HTTP response.
     */
    protected void handleNonSuccessResponseStatus(HttpMethod method, URI requestUrl, int responseStatus, String responseBody) {

        String baseExceptionMessage = String.format(
            "Failed to call URL '%s' with '%s' method.  Received non-succces HTTP response status %d",
            method, 
            responseStatus
        );

        // Attempt to deserialize a HttpErrorResponse from the body
        HttpErrorResponse httpErrorResponse = deserializeResponseBodyToHttpErrorResponse(responseBody);
        if (httpErrorResponse != null) {
            if (statusCodeToExceptionThrowingActionMap.containsKey(responseStatus) == true) {
                statusCodeToExceptionThrowingActionMap.get(responseStatus).accept(httpErrorResponse);
            }
            else {
                String exceptionMessagePostfix = String.format(
                    ", error code '%s', and error message '%s'.", 
                    httpErrorResponse.getCode(), 
                    httpErrorResponse.getMessage()
                );
                throw new RuntimeException(baseExceptionMessage + exceptionMessagePostfix);
            }
        }
        else {
            if (responseBody == null || responseBody.isBlank()) {
                throw new RuntimeException(baseExceptionMessage + ".");
            }
            else {
                throw new RuntimeException(baseExceptionMessage + String.format(" and response body '%s'.", responseBody));
            }
        }
    }

    /**
     * Attempts to deserialize the body of a HTTP response received as a string to an {@link HttpErrorResponse} instance.
     * 
     * @param responseBody The response body to deserialize.
     * @return The deserialized response body, or null if the reponse could not be deserialized (e.g. was empty, or did not contain JSON).
     */
    protected HttpErrorResponse deserializeResponseBodyToHttpErrorResponse(String responseBody)
    {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode bodyAsJsonNode = null;
        try {
            bodyAsJsonNode = objectMapper.readTree(responseBody);
        }
        catch (JsonProcessingException processingException) {
            return null;
        }

        ObjectNode bodyAsJson = null;
        if (bodyAsJsonNode instanceof ObjectNode) {
            bodyAsJson = (ObjectNode)bodyAsJsonNode;
        }
        else {
            return null;
        }

        try {
            return errorResponseDeserializer.deserialize(bodyAsJson);
        }
        catch (DeserializationException deserializationException) {
            return null;
        }
    }

    /**
     * Gets the value of the specified {@link HttpErrorResponse} attribute.
     * 
     * @param httpErrorResponse The {@link HttpErrorResponse} to retrieve the attribute from.
     * @param attributeKey The key of the attribute to retrieve.
     * @return The value of the attribute, or a blank string if no attribute with that key exists.
     */
    protected String getHttpErrorResponseAttributeValue(HttpErrorResponse httpErrorResponse, String attributeKey) {
        for (Entry<String, String> currentAttribute : httpErrorResponse.getAttributes()) {
            if (currentAttribute.getKey().equals(attributeKey)) {
                return currentAttribute.getValue();
            }
        }

        return "";
    }

    ////#endregion

    //#region Close Method

    @Override
    public void close() throws IOException {
        if (httpClientInstantiatedInConstructor == true) {
            httpClient.close();
        }
    }

    ////#endregion

    //#region Nested Classes

    /**
     * Container/model class holding parameters passed to a routine which handles a {@link HttpResponse}.
     */
    protected class ResponseFunctionParameters {

        /** The HTTP method called in the request which creatd the response. */
        protected HttpMethod httpMethod;
        /** The URL called in the request which creatd the response. */
        protected URI requestUrl;
        /** The HTTP status of the response. */
        protected int responseStatus;
        /** The body of the response as a string. */
        protected String responseBody;

        /** 
         * @return The HTTP method called in the request which creatd the response.
         */
        public HttpMethod getHttpMethod() {
            return httpMethod; 
        }

        /** 
         * @return The URL called in the request which creatd the response.
         */
        public URI getRequestUrl() {
            return requestUrl; 
        }

        /** 
         * @return The HTTP status of the response.
         */
        public int getResponseStatus() {
            return responseStatus; 
        }

        /** 
         * @return The body of the response as a string.
         */
        public String getResponseBody() {
            return responseBody; 
        }

        /**
         * Constructs a ResponseFunctionParameters.
         * 
         * @param httpMethod The HTTP method called in the request which creatd the response.
         * @param requestUrl The URL called in the request which creatd the response.
         * @param responseStatus The HTTP status of the response.
         * @param responseBody The body of the response as a string.
         */
        public ResponseFunctionParameters(HttpMethod httpMethod, URI requestUrl, int responseStatus, String responseBody) {

            this.httpMethod = httpMethod;
            this.requestUrl = requestUrl;
            this.responseStatus = responseStatus;
            this.responseBody = responseBody;
        }
    }

    ////#endregion
}
