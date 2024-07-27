package net.alastairwyse.applicationaccessclient;

import java.net.URI;
import java.util.List;

import net.alastairwyse.applicationaccessclient.AccessManagerClient;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;


/**
 * Integration tests for the net.alastairwyse.applicationaccessclient.AccessManagerClient class.
 */
public class AccessManagerClientIntegrationTests {
    
    private String serverUrl = "http://127.0.0.1:5170/";
    private AccessManagerClient<String, String, String, String> testAccessManagerClient;

    @Before
    public void setUp() {

        URI serveUri = null;
        try {
            serveUri = new URI(serverUrl);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("Failed to create URI from string '%s'.", serverUrl), e);
        }

        testAccessManagerClient = new AccessManagerClient<String, String, String, String>(
            serveUri, 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier()
        );
    }

    @Test
    public void fullTest() {
        
        try {
            testAccessManagerClient.addUser("Alastair");
            boolean containsResult = testAccessManagerClient.containsUser("Alastair");
            List<String> getResult = testAccessManagerClient.getUsers();
            testAccessManagerClient.removeUser("Alastair");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed setup elements.", e);
        }
    }
}
