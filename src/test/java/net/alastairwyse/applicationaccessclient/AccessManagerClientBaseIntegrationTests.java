package net.alastairwyse.applicationaccessclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.alastairwyse.applicationaccessclient.exceptions.ElementNotFoundException;
import net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndGroup;

/**
 * Integration tests for the net.alastairwyse.applicationaccessclient.AccessManagerClientBase class.
 * 
 * Since AccessManagerClientBase is abstract, tests are performed through inner class AccessManagerClientBaseWithProtectedMethods.
 */
public class AccessManagerClientBaseIntegrationTests {
    

    private String serverUrl = "http://127.0.0.1:5170/";
    private AccessManagerClientBaseWithProtectedMethods<String, String, String, String> testAccessManagerClientBase;

    @Before
    public void setUp() {

        URI serveUri = null;
        try {
            serveUri = new URI(serverUrl);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("Failed to create URI from string '%s'.", serverUrl), e);
        }

        testAccessManagerClientBase = new AccessManagerClientBaseWithProtectedMethods<String, String, String, String>(
            serveUri, 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier() 
        );
    }

    @After
    public void tearDown() {
        try {
            testAccessManagerClientBase.close();
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void sendGetRequest_IllegalArgumentException() {

        URI requestUrl = testAccessManagerClientBase.appendPathToBaseUrl("userToGroupMappings/user/user1?includeIndirectMappings=");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            testAccessManagerClientBase.sendGetRequest(requestUrl, new TypeReference<ArrayList<UserAndGroup>>(){});
        });

        assertTrue(e.getMessage().contains("One or more validation errors occurred."));
    }

    @Test
    public void sendGetRequest_ElementNotFoundException() {

        URI requestUrl = testAccessManagerClientBase.appendPathToBaseUrl("userToGroupMappings/user/invalid?includeIndirectMappings=false");

        ElementNotFoundException e = assertThrows(ElementNotFoundException.class, () -> {
            testAccessManagerClientBase.sendGetRequest(requestUrl, new TypeReference<ArrayList<UserAndGroup>>(){});
        });
        
        assertTrue(e.getMessage().contains("User 'invalid' does not exist. (Parameter 'user')"));
        assertEquals("User", e.getElementType());
    }

    //#region Inner Classes

    /**
     * Version of AccessManagerClientBase where protected members are exposed as public so that they can be unit tested.
     */
    private class AccessManagerClientBaseWithProtectedMethods<TUser, TGroup, TComponent, TAccess>  extends AccessManagerClientBase<TUser, TGroup, TComponent, TAccess>  {

        /**
         * Constructs an AccessManagerClientBaseWithProtectedMethods.
         * 
         * @param baseUrl The base URL for the hosted Web API.
         * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
         * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
         * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
         * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
         */
        public AccessManagerClientBaseWithProtectedMethods(
            URI baseUrl, 
            UniqueStringifier<TUser> userStringifier, 
            UniqueStringifier<TGroup> groupStringifier, 
            UniqueStringifier<TComponent> applicationComponentStringifier, 
            UniqueStringifier<TAccess> accessLevelStringifier
        ) {
            super(baseUrl, userStringifier, groupStringifier, applicationComponentStringifier, accessLevelStringifier);
        }

        /**
         * Constructs an AccessManagerClientBaseWithProtectedMethods.
         * 
         * @param httpClient The client to use to connect.
         * @param baseUrl The base URL for the hosted Web API.
         * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
         * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
         * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
         * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
         */
        public AccessManagerClientBaseWithProtectedMethods(
            HttpClient httpClient, 
            URI baseUrl, 
            UniqueStringifier<TUser> userStringifier, 
            UniqueStringifier<TGroup> groupStringifier, 
            UniqueStringifier<TComponent> applicationComponentStringifier, 
            UniqueStringifier<TAccess> accessLevelStringifier
        ) {
            super(httpClient, baseUrl, userStringifier, groupStringifier, applicationComponentStringifier, accessLevelStringifier);
        }

        /**
         * Constructs an AccessManagerClientBaseWithProtectedMethods.
         * 
         * @param httpClient The client to use to connect.
         * @param baseUrl The base URL for the hosted Web API.
         * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
         * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
         * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
         * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
         * @param requestHeaders HTTP headers to send with each request.
         */
        public AccessManagerClientBaseWithProtectedMethods(
            HttpClient httpClient, 
            URI baseUrl, 
            UniqueStringifier<TUser> userStringifier, 
            UniqueStringifier<TGroup> groupStringifier, 
            UniqueStringifier<TComponent> applicationComponentStringifier, 
            UniqueStringifier<TAccess> accessLevelStringifier, 
            Map<String, String> requestHeaders
        ) {
            super(httpClient, baseUrl, userStringifier, groupStringifier, applicationComponentStringifier, accessLevelStringifier, requestHeaders);
        }
    
        /**
         * Concatenates the specified path (with no leading forward slash) to the 'baseUrl' property and returns it as a new {@link URI} 
         * 
         * @param path The path to concatenate.
         * @return The concatenated URL.
         */
        public URI appendPathToBaseUrl(String path) {
            return super.appendPathToBaseUrl(path);
        }

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
        public <T> T sendGetRequest(URI requestUrl, TypeReference<T> returnType) throws IOException, InterruptedException {
            return super.sendGetRequest(requestUrl, returnType);
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
        public boolean sendGetRequestForContainsMethod(URI requestUrl) throws IOException, InterruptedException {
            return super.sendGetRequestForContainsMethod(requestUrl);
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
        public void sendPostRequest(URI requestUrl) throws IOException, InterruptedException {
            super.sendPostRequest(requestUrl);
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
        public void sendDeleteRequest(URI requestUrl) throws IOException, InterruptedException {
            super.sendDeleteRequest(requestUrl);
        }
    }

    //#endregion
}
