/*
 * Copyright 2024 Alastair Wyse (https://github.com/alastairwyse/ApplicationAccessJavaClient/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.alastairwyse.applicationaccessclient;

import java.net.URI;
import java.util.List;

import net.alastairwyse.applicationaccessclient.models.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests for the net.alastairwyse.applicationaccessclient.AccessManagerStringElementClient class.
 */
public class AccessManagerStringElementClientIntegrationTests {

    private String serverUrl = "http://127.0.0.1:5170/";
    private AccessManagerStringElementClient testAccessManagerStringElementClient;

    @Before
    public void setUp() {

        URI serverUri = null;
        try {
            serverUri = new URI(serverUrl);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("Failed to create URI from string '%s'.", serverUrl), e);
        }

        testAccessManagerStringElementClient = new AccessManagerStringElementClient(serverUri);
    }

    @After
    public void tearDown() {
        try {
            testAccessManagerStringElementClient.close();
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddQueryRemoveElementsAndMappings() {

        try {
            testAccessManagerStringElementClient.addUser("user1");
            testAccessManagerStringElementClient.addGroup("group1");
            testAccessManagerStringElementClient.addUserToApplicationComponentAndAccessLevelMapping("user1", "order", "view");

            List<String> allUsers = testAccessManagerStringElementClient.getUsers();
            assertEquals(1, allUsers.size());
            assertTrue(allUsers.contains("user1"));

            List<String> allGroups = testAccessManagerStringElementClient.getGroups();
            assertEquals(1, allGroups.size());
            assertTrue(allGroups.contains("group1"));

            List<ApplicationComponentAndAccessLevel<String, String>> userComponentMappings = testAccessManagerStringElementClient.getUserToApplicationComponentAndAccessLevelMappings("user1");
            assertEquals(1, userComponentMappings.size());
            assertTrue(userComponentMappings.contains(new ApplicationComponentAndAccessLevel<String, String>("order", "view")));
            
            testAccessManagerStringElementClient.removeUserToApplicationComponentAndAccessLevelMapping("user1", "order", "view");
            testAccessManagerStringElementClient.removeGroup("group1");
            testAccessManagerStringElementClient.removeUser("user1");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to test adding, querying, and removing.", e);
        }
    }
}
