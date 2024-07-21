package net.alastairwyse.applicationaccessclient;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.alastairwyse.applicationaccessclient.models.HttpErrorResponse;
import net.alastairwyse.applicationaccessclient.exceptions.DeserializationException;

/**
 * Serializes and deserializes {@link HttpErrorResponse} instances to and from JSON documents.
 */
public class HttpErrorResponseJsonSerializer {
    
    protected static final String errorPropertyName = "error";
    protected static final String codePropertyName = "code";
    protected static final String messagePropertyName = "message";
    protected static final String targetPropertyName = "target";
    protected static final String attributesPropertyName = "attributes";
    protected static final String namePropertyName = "name";
    protected static final String valuePropertyName = "value";
    protected static final String innerErrorPropertyName = "innererror";

    protected ObjectMapper objectMapper;

    /**
     * Constructs an HttpErrorResponseJsonSerializer.
     */
    public HttpErrorResponseJsonSerializer() {

        objectMapper = new ObjectMapper();
    }

    /**
     * Serializes the specified <see cref="HttpErrorResponse"/> to a JSON document.
     * 
     * @param httpErrorResponse The {@link HttpErrorResponse} object to serialize.
     * @return A JSON document representing the HttpErrorResponse.
     */
    public ObjectNode serialize(HttpErrorResponse httpErrorResponse) {

        ObjectNode returnDocument = objectMapper.createObjectNode();

        returnDocument.set(errorPropertyName, serializeError(httpErrorResponse));

        return returnDocument;
    }

    /**
     * Deserializes the specified JSON object to a {@link HttpErrorResponse} object.
     * 
     * @param jsonObject The deserialized HttpErrorResponse.
     * @return Failed to deserialize the HttpErrorResponse.
     * @throws DeserializationException Failed to deserialize.
     */
    public HttpErrorResponse deserialize(JsonNode jsonObject) throws DeserializationException {
    
        if (jsonObject.has(errorPropertyName) == true && jsonObject.get(errorPropertyName).getClass() == ObjectNode.class) {
            return deserializeError((JsonNode)jsonObject.get(errorPropertyName));
        }
        else {
            throw new DeserializationException(
                String.format("Failed to deserialize %s.  The specified %s did not contain an '%s' property.", 
                    HttpErrorResponse.class.getSimpleName(), 
                    JsonNode.class.getSimpleName(), 
                    errorPropertyName
                )
            );
        }
    }

    //#region Private/Protected Methods

    /**
     * Serializes the 'error' and 'innererror' properties of the JSON document returned by the Serialize() method.
     * 
     * @param httpErrorResponse The HttpErrorResponse object to serialize.
     * @return The 'error' or 'innererror' property of the JSON document.
     */
    protected ObjectNode serializeError(HttpErrorResponse httpErrorResponse) {
        
        ObjectNode returnDocument = objectMapper.createObjectNode();

        returnDocument.put(codePropertyName, httpErrorResponse.getCode());
        returnDocument.put(messagePropertyName, httpErrorResponse.getMessage());
        if (httpErrorResponse.getTarget() != null) {
            returnDocument.put(targetPropertyName, httpErrorResponse.getTarget());
        }
        ArrayNode attributesJson = objectMapper.createArrayNode();
        for (Entry<String, String> currentAttribute : httpErrorResponse.getAttributes()) {
            ObjectNode currentAttributeJson = objectMapper.createObjectNode();
            currentAttributeJson.put(namePropertyName, currentAttribute.getKey());
            currentAttributeJson.put(valuePropertyName, currentAttribute.getValue());
            attributesJson.add(currentAttributeJson);
        }
        if (attributesJson.size() > 0) {
            returnDocument.set(attributesPropertyName, attributesJson);
        }
        if (httpErrorResponse.getInnerError() != null) {
            returnDocument.set(innerErrorPropertyName, serializeError(httpErrorResponse.getInnerError()));
        }

        return returnDocument;
    }

    /**
     * Deserializes the 'error' and 'innererror' properties of a JSON document containing a serialized {@link HttpErrorResponse}.
     * 
     * @param jsonObject The 'error' or 'innererror' property of the JSON object to deserialize.
     * @return The deserialized 'error' or 'innererror' property, or null if the property could not be deserialized.
     * @throws DeserializationException Failed to deserialize a property.
     */
    protected HttpErrorResponse deserializeError(JsonNode jsonObject) throws DeserializationException {
        
        String exceptionMessage = "Failed to deserialize %s 'error' or 'innererror' property.  The specified %s did not contain a '%s' property.";
        if (jsonObject.has(codePropertyName) == false)
            throw new DeserializationException(
                String.format(exceptionMessage, 
                    HttpErrorResponse.class.getSimpleName(), 
                    JsonNode.class.getSimpleName(), 
                    codePropertyName
                )
            );
        if (jsonObject.has(messagePropertyName) == false)
            throw new DeserializationException(
                String.format(exceptionMessage, 
                    HttpErrorResponse.class.getSimpleName(), 
                    JsonNode.class.getSimpleName(), 
                    messagePropertyName
                )
            );

        String code = jsonObject.get(codePropertyName).asText();
        String message = jsonObject.get(messagePropertyName).asText();
        String target = null;
        var attributes = new ArrayList<Entry<String, String>>();
        HttpErrorResponse innerError = null;

        // Deserialize optional properties
        if (jsonObject.has(targetPropertyName) == true) {
            target = jsonObject.get(targetPropertyName).asText();
        }
        if (jsonObject.has(attributesPropertyName) == true && jsonObject.get(attributesPropertyName).getClass() == ArrayNode.class) {
            var attributesJson = (ArrayNode)jsonObject.get(attributesPropertyName);
            for (JsonNode currentAttributeJson : attributesJson) {
                if (currentAttributeJson.has(namePropertyName) == true && currentAttributeJson.has(valuePropertyName) == true) {
                    attributes.add(new SimpleEntry<String, String>(currentAttributeJson.get(namePropertyName).asText(), currentAttributeJson.get(valuePropertyName).asText()));
                }
            }
        }
        if (jsonObject.has(innerErrorPropertyName) == true && jsonObject.get(innerErrorPropertyName).getClass() == ObjectNode.class) {
            innerError = deserializeError((ObjectNode)jsonObject.get(innerErrorPropertyName));
        }

        // Create the return HttpErrorResponse
        if (target != null && attributes.size() > 0 && innerError != null) {
            return new HttpErrorResponse(code, message, target, attributes, innerError);
        }
        else if (target != null && attributes.size() > 0) {
            return new HttpErrorResponse(code, message, target, attributes);
        }
        else if (target != null && innerError != null) {
            return new HttpErrorResponse(code, message, target, innerError);
        }
        else if (attributes.size() > 0 && innerError != null) {
            return new HttpErrorResponse(code, message, attributes, innerError);
        }
        else if (target != null) {
            return new HttpErrorResponse(code, message, target);
        }
        else if (attributes.size() > 0) {
            return new HttpErrorResponse(code, message, attributes);
        }
        else if (innerError != null) {
            return new HttpErrorResponse(code, message, innerError);
        }
        else {
            return new HttpErrorResponse(code, message);
        }
    }

    //#endregion
}
