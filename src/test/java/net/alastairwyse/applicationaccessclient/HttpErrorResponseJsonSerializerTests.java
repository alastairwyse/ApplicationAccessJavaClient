package net.alastairwyse.applicationaccessclient;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.alastairwyse.applicationaccessclient.exceptions.DeserializationException;
import net.alastairwyse.applicationaccessclient.models.HttpErrorResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the net.alastairwyse.applicationaccessclient.HttpErrorResponseJsonSerializer class.
 */
public class HttpErrorResponseJsonSerializerTests {
    
    private ObjectMapper objectMapper;
    private HttpErrorResponseJsonSerializer testHttpErrorResponseJsonSerializer;

    @Before
    public void setUp() {

        objectMapper = new ObjectMapper();
        testHttpErrorResponseJsonSerializer = new HttpErrorResponseJsonSerializer();
    }

    @Test
    public void serialize_HttpErrorResponseWithCodeAndMessage() {
        var errorResponse = new HttpErrorResponse (
            "ArgumentException",
            "Argument 'recordCount' must be greater than or equal to 0."
        );
        String expectedJsonString = """
        {
            "error" : {
                "code" : "ArgumentException", 
                "message" : "Argument 'recordCount' must be greater than or equal to 0."
            }
        }
        """;
        ObjectNode expectedJson = null;
        try {
            expectedJson = (ObjectNode)objectMapper.readTree(expectedJsonString);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        ObjectNode result = testHttpErrorResponseJsonSerializer.serialize(errorResponse);

        assertEquals(expectedJson, result);
    }

    @Test
    public void serialize_HttpErrorResponseWithCodeMessageAndTarget() {
        var errorResponse = new HttpErrorResponse (
            "ArgumentException",
            "Argument 'recordCount' must be greater than or equal to 0.", 
            "recordCount"
        );
        String expectedJsonString = """
        {
            "error" : {
                "code" : "ArgumentException", 
                "message" : "Argument 'recordCount' must be greater than or equal to 0.", 
                "target" : "recordCount"
            }
        }
        """;
        ObjectNode expectedJson = null;
        try {
            expectedJson = (ObjectNode)objectMapper.readTree(expectedJsonString);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        ObjectNode result = testHttpErrorResponseJsonSerializer.serialize(errorResponse);

        assertEquals(expectedJson, result);
    }

    @Test
    public void serialize_HttpErrorResponseWithCodeMessageAndAttributes() {
        var attributes = new ArrayList<Entry<String, String>>();
        attributes.add(new SimpleEntry<String, String>("user", "user1"));
        attributes.add(new SimpleEntry<String, String>("group", "group1"));
        var errorResponse = new HttpErrorResponse (
            "ArgumentException",
            "Argument 'recordCount' must be greater than or equal to 0.", 
            attributes
        );
        String expectedJsonString = """
        {
            "error" : {
                "code" : "ArgumentException", 
                "message" : "Argument 'recordCount' must be greater than or equal to 0.", 
                "attributes" : 
                [
                    { "name": "user", "value": "user1" }, 
                    { "name": "group", "value": "group1" }
                ]
            }
        }
        """;
        ObjectNode expectedJson = null;
        try {
            expectedJson = (ObjectNode)objectMapper.readTree(expectedJsonString);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        ObjectNode result = testHttpErrorResponseJsonSerializer.serialize(errorResponse);

        assertEquals(expectedJson, result);
    }

    @Test
    public void serialize_HttpErrorResponseWithCodeMessageAndInnerError() {
        var innerError = new HttpErrorResponse (
            "ArgumentException",
            "An edge already exists between vertices 'child' and 'parent'."
        );
        var errorResponse = new HttpErrorResponse (
            "ArgumentException",
            "A mapping between user 'user1' and group 'group1' already exists.", 
            innerError
        );
        String expectedJsonString = """
        {
            "error" : {
                "code" : "ArgumentException", 
                "message" : "A mapping between user 'user1' and group 'group1' already exists.", 
                "innererror" : 
                {
                    "code" : "ArgumentException", 
                    "message" : "An edge already exists between vertices 'child' and 'parent'."
                }
            }
        }
        """;
        ObjectNode expectedJson = null;
        try {
            expectedJson = (ObjectNode)objectMapper.readTree(expectedJsonString);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        ObjectNode result = testHttpErrorResponseJsonSerializer.serialize(errorResponse);

        assertEquals(expectedJson, result);
    }

    @Test
    public void serialize_HttpErrorResponseWithAllProperties() {        
        var attributes = new ArrayList<Entry<String, String>>();
        attributes.add(new SimpleEntry<String, String>("fromVertex", "child"));
        attributes.add(new SimpleEntry<String, String>("toVertex", "parent"));
        var innerError = new HttpErrorResponse (
            "ArgumentException",
            "An edge already exists between vertices 'child' and 'parent'."
        );
        var errorResponse = new HttpErrorResponse (
            "ArgumentException",
            "Failed to add edge to graph.", 
            "graph",
            attributes, 
            innerError
        );
        String expectedJsonString = """
        {
            "error" : {
                "code" : "ArgumentException", 
                "message" : "Failed to add edge to graph.", 
                "target" : "graph", 
                "attributes" : 
                [
                    { "name": "fromVertex", "value": "child" }, 
                    { "name": "toVertex", "value": "parent" }
                ], 
                "innererror" : 
                {
                    "code" : "ArgumentException", 
                    "message" : "An edge already exists between vertices 'child' and 'parent'."
                }
            }
        }
        """;
        ObjectNode expectedJson = null;
        try {
            expectedJson = (ObjectNode)objectMapper.readTree(expectedJsonString);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        ObjectNode result = testHttpErrorResponseJsonSerializer.serialize(errorResponse);

        assertEquals(expectedJson, result);
    }

    @Test
    public void deserialize_JsonDoesntContainErrorProperty()
    {
        DeserializationException e = assertThrows(DeserializationException.class, () -> {
            testHttpErrorResponseJsonSerializer.deserialize(objectMapper.createObjectNode());
        });

        assertTrue(e.getMessage().contains("Failed to deserialize HttpErrorResponse.  The specified JsonNode did not contain an 'error' property."));
    }

    @Test
    public void deserialize_ErrorPropertyNotObjectNode()
    {
        ObjectNode testJsonObject = objectMapper.createObjectNode();
        testJsonObject.set("error", objectMapper.createArrayNode());

        DeserializationException e = assertThrows(DeserializationException.class, () -> {
            testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        });

        assertTrue(e.getMessage().contains("Failed to deserialize HttpErrorResponse.  The specified JsonNode did not contain an 'error' property."));
    }

    @Test
    public void deserialize_ErrorObjectDoesntContainCodeProperty()
    {
        String testJsonObjectString = """
        {
            "error" : {
                "message" : "Failed to add edge to graph."
            }
        }
        """;
        DeserializationException e = assertThrows(DeserializationException.class, () -> {
            ObjectNode testJsonObject = (ObjectNode)objectMapper.readTree(testJsonObjectString);

            testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        });

        assertTrue(e.getMessage().contains("Failed to deserialize HttpErrorResponse 'error' or 'innererror' property.  The specified JsonNode did not contain a 'code' property."));
    }

    @Test
    public void deserialize_ErrorObjectDoesntContainMessageProperty()
    {
        String testJsonObjectString = """
        {
            "error" : {
                "code" : "ArgumentException"
            }
        }
        """;
        DeserializationException e = assertThrows(DeserializationException.class, () -> {
            ObjectNode testJsonObject = (ObjectNode)objectMapper.readTree(testJsonObjectString);

            testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        });

        assertTrue(e.getMessage().contains("Failed to deserialize HttpErrorResponse 'error' or 'innererror' property.  The specified JsonNode did not contain a 'message' property."));
    }

    @Test
    public void deserialize_NoInnerError()
    {
        final String testCode = "ArgumentException";
        final String testMessage = "Failed to add edge to graph.";
        final String testTarget = "graph";
        var testAttributes = new ArrayList<Entry<String, String>>();
        testAttributes.add(new SimpleEntry<String, String>("fromVertex", "child"));
        testAttributes.add(new SimpleEntry<String, String>("toVertex", "parent"));
        var testErrorResponse = new HttpErrorResponse(testCode, testMessage, testTarget, testAttributes);
        ObjectNode testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        HttpErrorResponse result = null;
        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertEquals(testTarget, result.getTarget());
        assertEquals(testAttributes.get(0), getIterableElement(result.getAttributes(), 0));
        assertEquals(testAttributes.get(1), getIterableElement(result.getAttributes(), 1));
        assertNull(result.getInnerError());


        testErrorResponse = new HttpErrorResponse(testCode, testMessage, testTarget);
        testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertEquals(testTarget, result.getTarget());
        assertEquals(0, result.getAttributes().spliterator().getExactSizeIfKnown());
        assertNull(result.getInnerError());


        testErrorResponse = new HttpErrorResponse(testCode, testMessage, testAttributes);
        testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertNull(result.getTarget());
        assertEquals(testAttributes.get(0), getIterableElement(result.getAttributes(), 0));
        assertEquals(testAttributes.get(1), getIterableElement(result.getAttributes(), 1));
        assertNull(result.getInnerError());


        testErrorResponse = new HttpErrorResponse(testCode, testMessage);
        testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertNull(result.getTarget());
        assertEquals(0, result.getAttributes().spliterator().getExactSizeIfKnown());
        assertNull(result.getInnerError());
    }

    @Test
    public void deserialize_IncludingInnerError()
    {
        final String testCode = "ArgumentException";
        final String testMessage = "Failed to add edge to graph.";
        final String testTarget = "graph";
        var testAttributes = new ArrayList<Entry<String, String>>();
        testAttributes.add(new SimpleEntry<String, String>("fromVertex", "child"));
        testAttributes.add(new SimpleEntry<String, String>("toVertex", "parent"));
        final String testInnerErrorCode = "Exception";
        final String testInnerErrorMessage = "An edge already exists between vertices 'child' and 'parent'.";
        var testInnerError = new HttpErrorResponse(testInnerErrorCode, testInnerErrorMessage);
        var testErrorResponse = new HttpErrorResponse(testCode, testMessage, testTarget, testAttributes, testInnerError);
        ObjectNode testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        HttpErrorResponse result = null;
        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertEquals(testTarget, result.getTarget());
        assertEquals(testAttributes.get(0), getIterableElement(result.getAttributes(), 0));
        assertEquals(testAttributes.get(1), getIterableElement(result.getAttributes(), 1));
        assertNotNull(result.getInnerError());
        assertEquals(testInnerErrorCode, result.getInnerError().getCode());
        assertEquals(testInnerErrorMessage, result.getInnerError().getMessage());


        testInnerError = new HttpErrorResponse(testInnerErrorCode, testInnerErrorMessage);
        testErrorResponse = new HttpErrorResponse(testCode, testMessage, testTarget, testInnerError);
        testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertEquals(testTarget, result.getTarget());
        assertEquals(0, result.getAttributes().spliterator().getExactSizeIfKnown());
        assertNotNull(result.getInnerError());
        assertEquals(testInnerErrorCode, result.getInnerError().getCode());
        assertEquals(testInnerErrorMessage, result.getInnerError().getMessage());


        testInnerError = new HttpErrorResponse(testInnerErrorCode, testInnerErrorMessage);
        testErrorResponse = new HttpErrorResponse(testCode, testMessage, testAttributes, testInnerError);
        testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertNull(result.getTarget());
        assertEquals(testAttributes.get(0), getIterableElement(result.getAttributes(), 0));
        assertEquals(testAttributes.get(1), getIterableElement(result.getAttributes(), 1));
        assertNotNull(result.getInnerError());
        assertEquals(testInnerErrorCode, result.getInnerError().getCode());
        assertEquals(testInnerErrorMessage, result.getInnerError().getMessage());


        testInnerError = new HttpErrorResponse(testInnerErrorCode, testInnerErrorMessage);
        testErrorResponse = new HttpErrorResponse(testCode, testMessage, testInnerError);
        testJsonObject = testHttpErrorResponseJsonSerializer.serialize(testErrorResponse);

        try {
            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(testCode, result.getCode());
        assertEquals(testMessage, result.getMessage());
        assertNull(result.getTarget());
        assertEquals(0, result.getAttributes().spliterator().getExactSizeIfKnown());
        assertNotNull(result.getInnerError());
        assertEquals(testInnerErrorCode, result.getInnerError().getCode());
        assertEquals(testInnerErrorMessage, result.getInnerError().getMessage());
    }

    @Test
    public void deserialize_MultiLevelInnerErrors()
    {
        var testJsonObjectString = """
        {
            "error": {
                "code": "BufferFlushingException",
                "message": "Exception occurred on buffer flushing worker thread at 2023-01-29 12:29:12.075 +09:00.",
                "target": "Throw",
                "innererror": {
                    "code": "Exception",
                    "message": "Failed to process buffers and persist flushed events.",
                    "target": "Flush",
                    "innererror": {
                        "code": "Exception",
                        "message": "Failed to execute stored procedure 'ProcessEvents' in SQL Server.",
                        "target": "ExecuteStoredProcedure"
                    }
                }
            }
        }
        """;
        HttpErrorResponse result = null;
        try {
            ObjectNode testJsonObject = (ObjectNode)objectMapper.readTree(testJsonObjectString);

            result = testHttpErrorResponseJsonSerializer.deserialize(testJsonObject);
        } 
        catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals("BufferFlushingException", result.getCode());
        assertEquals("Exception occurred on buffer flushing worker thread at 2023-01-29 12:29:12.075 +09:00.", result.getMessage());
        assertEquals("Throw", result.getTarget());
        assertEquals("Exception", result.getInnerError().getCode());
        assertEquals("Failed to process buffers and persist flushed events.", result.getInnerError().getMessage());
        assertEquals("Flush", result.getInnerError().getTarget());
        assertEquals("Exception", result.getInnerError().getInnerError().getCode());
        assertEquals("Failed to execute stored procedure 'ProcessEvents' in SQL Server.", result.getInnerError().getInnerError().getMessage());
        assertEquals("ExecuteStoredProcedure", result.getInnerError().getInnerError().getTarget());
    }

    //#region Private/Protected Methods

    /**
     * Returns the element at the specified index from an {@link Iterable}.
     * @param <T> The type of element held by the {@link Iterable}.
     * @param iterable The {@link Iterable} to search.
     * @param index The index to retrive.
     * @return The element at the specified index.
     */
    protected <T> T getIterableElement(Iterable<T> iterable, int index) {
        int currentIndex = 0;
        for (T currentElement : iterable) {
            if (currentIndex == index) {
                return currentElement;
            }
            else {
                currentIndex++;
            }
        }

        throw new IndexOutOfBoundsException(String.format("Element at index %d did not exist in the %s.", index, Iterable.class.getSimpleName()));
    }

    //#endregion
}

