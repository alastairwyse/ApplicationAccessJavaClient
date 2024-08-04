package net.alastairwyse.applicationaccessclient;

import java.net.ConnectException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import net.alastairwyse.applicationaccessclient.models.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order; 
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests for the net.alastairwyse.applicationaccessclient.AccessManagerClient class.
 */
@TestMethodOrder(OrderAnnotation.class)
public class AccessManagerClientIntegrationTests {
    
    private final String URL_RESERVED_CHARACTERS = "! * ' ( ) ; : @ & = + $ , / ? % # [ ]";

    // Entity string constants
    private final String CLIENT_ACCOUNTS = "ClientAccount";
    private final String COMPANY_1 = "Company1";
    private final String COMPANY_2 = "Company2";
    private final String COMPANY_3 = "Company3";
    private final String COMPANY_4 = "Company4";
    private final String COMPANY_5 = "Company5";
    private final String COMPANY_6 = "Company6";
    private final String COMPANY_7 = "Company7";
    private final String COMPANY_8 = "Company8";
    private final String COMPANY_9 = "Company9";
    private final String COMPANY_10 = "Company10";
    private final String PRODUCT_LINES = "ProductLines";
    private final String LINE_1 = "Line1";
    private final String LINE_2 = "Line2";
    private final String LINE_3 = "Line3";
    private final String LINE_4 = "Line4";
    private final String LINE_5 = "Line5";
    private final String LINE_6 = "Line6";
    private final String LINE_7 = "Line7";
    private final String LINE_8 = "Line8";
    private final String LINE_9 = "Line9";
    private final String LINE_10 = "Line10";
    private final String UNMAPPED = "Unmapped";

    private String serverUrl = "http://127.0.0.1:5170/";
    private UniqueStringifier<ApplicationScreen> componentStringifier;
    private UniqueStringifier<AccessLevel> accessLevelStringifier;
    private AccessManagerClient<String, String, ApplicationScreen, AccessLevel> testAccessManagerClient;

    // TODO: Exercuse each one of the underlying SendRequest*() methods (e.g. sendGetRequestForContainsMethod()) for failure/exception case

    @Before
    public void setUp() {

        URI serverUri = null;
        try {
            serverUri = new URI(serverUrl);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("Failed to create URI from string '%s'.", serverUrl), e);
        }

        componentStringifier = new UniqueStringifier<ApplicationScreen>() {
            @Override
            public String toString(ApplicationScreen inputObject) {
                switch(inputObject) {
                    case ORDER:
                        return "Order";
                    case SUMMARY:
                        return "Summary";
                    case MANAGE_PRODUCTS:
                        return "ManageProducts";
                    case SETTINGS:
                        return "Settings";
                    case DELIVERY:
                        return "Delivery";
                    case REVIEW:
                        return "Review";
                    default:
                        throw new RuntimeException(String.format("Unhandled ApplicationScreen value '%s'.", inputObject));
                  }
            }

            @Override 
            public ApplicationScreen fromString(String inpuString) {
                switch(inpuString) {
                    case "Order":
                        return ApplicationScreen.ORDER;
                    case "Summary":
                        return ApplicationScreen.SUMMARY;
                    case "ManageProducts":
                        return ApplicationScreen.MANAGE_PRODUCTS;
                    case "Settings":
                        return ApplicationScreen.SETTINGS;
                    case "Delivery":
                        return ApplicationScreen.DELIVERY;
                    case "Review":
                        return ApplicationScreen.REVIEW;
                    default:
                        throw new RuntimeException(String.format("Unhandled value '%s'.", inpuString));
                  }
            }
        };

        accessLevelStringifier = new UniqueStringifier<AccessLevel>() {
            @Override
            public String toString(AccessLevel inputObject) {
                switch(inputObject) {
                    case VIEW:
                        return "View";
                    case CREATE:
                        return "Create";
                    case MODIFY:
                        return "Modify";
                    case DELETE:
                        return "Delete";
                    default:
                        throw new RuntimeException(String.format("Unhandled AccessLevel screen value '%s'.", inputObject));
                  }
            }

            @Override 
            public AccessLevel fromString(String inpuString) {
                switch(inpuString) {
                    case "View":
                        return AccessLevel.VIEW;
                    case "Create":
                        return AccessLevel.CREATE;
                    case "Modify":
                        return AccessLevel.MODIFY;
                    case "Delete":
                        return AccessLevel.DELETE;
                    default:
                        throw new RuntimeException(String.format("Unhandled value '%s'.", inpuString));
                  }
            }
        };

        testAccessManagerClient = new AccessManagerClient<String, String, ApplicationScreen, AccessLevel>(
            serverUri, 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier(), 
            componentStringifier, 
            accessLevelStringifier
        );
    }

    @After
    public void tearDown() {
        try {
            testAccessManagerClient.close();
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(1) 
    public void connectionExceptionTests() {

        URI closedPortUrl = null;
        try {
            closedPortUrl = new URI("http://127.0.0.1:100/");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create URI from string 'http://127.0.0.1:100/'.", e);
        }

        try (
            AccessManagerClient<String, String, ApplicationScreen, AccessLevel> exceptionAccessManagerClient = new AccessManagerClient<String, String, ApplicationScreen, AccessLevel>(
                closedPortUrl, 
                new StringUniqueStringifier(), 
                new StringUniqueStringifier(), 
                componentStringifier, 
                accessLevelStringifier
            );
        ) {
            ConnectException e = assertThrows(ConnectException.class, () -> {
                exceptionAccessManagerClient.getUsers();
            });

            
            e = assertThrows(ConnectException.class, () -> {
                exceptionAccessManagerClient.containsUser("user1");
            });

            
            e = assertThrows(ConnectException.class, () -> {
                exceptionAccessManagerClient.addUser("user1");
            });

            
            e = assertThrows(ConnectException.class, () -> {
                exceptionAccessManagerClient.removeUser("user1");
            });
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to test exceptions.", e);
        }
    }

    @Test
    @Order(2) 
    public void urlReservedCharacters() {

        try {
            testAccessManagerClient.addUser(URL_RESERVED_CHARACTERS);

            boolean containsResult = testAccessManagerClient.containsUser(URL_RESERVED_CHARACTERS);
            assertTrue(containsResult);

            testAccessManagerClient.addUser(URL_RESERVED_CHARACTERS);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to test url reserved characters.", e);
        }
    }

    @Test
    @Order(3) 
    public void gettersOnEmptyAccessManager() {
        
        try {
            List<String> allUsers = testAccessManagerClient.getUsers();
            assertEquals(0, allUsers.size());
            
            List<String> allGroups = testAccessManagerClient.getGroups();
            assertEquals(0, allGroups.size());
            
            List<String> allEntityTypes = testAccessManagerClient.getEntityTypes();
            assertEquals(0, allEntityTypes.size());
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to test empty element getters.", e);
        }
    }

    @Test
    @Order(4) 
    public void addElementsAndMappings() {

        // Add all elements and mappings
        try {
            testAccessManagerClient.addUser("user1");
            testAccessManagerClient.addUser("user2");
            testAccessManagerClient.addUser("user3");
            testAccessManagerClient.addUser("user4");
            testAccessManagerClient.addUser("user5");
            testAccessManagerClient.addUser("user6");
            testAccessManagerClient.addUser("user7");
            testAccessManagerClient.addUser("user8");
            testAccessManagerClient.addUser("user9");
            testAccessManagerClient.addUser("user10");
            testAccessManagerClient.addUser("user11");
            testAccessManagerClient.addUser("user12");
            testAccessManagerClient.addUser("unmappedUser1");
            testAccessManagerClient.addUser("orphanedUser");

            testAccessManagerClient.addGroup("group1");
            testAccessManagerClient.addGroup("group2");
            testAccessManagerClient.addGroup("group3");
            testAccessManagerClient.addGroup("group4");
            testAccessManagerClient.addGroup("group5");
            testAccessManagerClient.addGroup("group6");
            testAccessManagerClient.addGroup("unmappedGroup1");
            testAccessManagerClient.addGroup("unmappedGroup2");
            testAccessManagerClient.addGroup("orphanedGroup");

            testAccessManagerClient.addEntityType(CLIENT_ACCOUNTS);
            testAccessManagerClient.addEntityType(PRODUCT_LINES);
            testAccessManagerClient.addEntityType(UNMAPPED);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_1);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_2);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_3);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_4);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_5);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_6);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_7);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_8);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_9);
            testAccessManagerClient.addEntity(CLIENT_ACCOUNTS, COMPANY_10);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_1);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_2);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_3);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_4);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_5);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_6);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_7);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_8);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_9);
            testAccessManagerClient.addEntity(PRODUCT_LINES, LINE_10);

            testAccessManagerClient.addUserToGroupMapping("user1", "group1");
            testAccessManagerClient.addUserToGroupMapping("user2", "group1");
            testAccessManagerClient.addUserToGroupMapping("user3", "group2");
            testAccessManagerClient.addUserToGroupMapping("user4", "group2");
            testAccessManagerClient.addUserToGroupMapping("user5", "group3");
            testAccessManagerClient.addUserToGroupMapping("user6", "group3");
            testAccessManagerClient.addUserToGroupMapping("user7", "group4");
            testAccessManagerClient.addUserToGroupMapping("user8", "group4");
            testAccessManagerClient.addUserToGroupMapping("user9", "group5");
            testAccessManagerClient.addUserToGroupMapping("user10", "group5");
            testAccessManagerClient.addUserToGroupMapping("user11", "group6");
            testAccessManagerClient.addUserToGroupMapping("user12", "group6");
            testAccessManagerClient.addUserToGroupMapping("unmappedUser1", "unmappedGroup1");

            testAccessManagerClient.addGroupToGroupMapping("group1", "group3");
            testAccessManagerClient.addGroupToGroupMapping("group1", "group4");
            testAccessManagerClient.addGroupToGroupMapping("group2", "group4");
            testAccessManagerClient.addGroupToGroupMapping("group3", "group5");
            testAccessManagerClient.addGroupToGroupMapping("group4", "group5");
            testAccessManagerClient.addGroupToGroupMapping("group4", "group6");
            testAccessManagerClient.addGroupToGroupMapping("unmappedGroup1", "unmappedGroup2");

            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user1", ApplicationScreen.ORDER, AccessLevel.VIEW);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user1", ApplicationScreen.ORDER, AccessLevel.CREATE);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user2", ApplicationScreen.ORDER, AccessLevel.MODIFY);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user3", ApplicationScreen.ORDER, AccessLevel.DELETE);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user4", ApplicationScreen.SUMMARY, AccessLevel.VIEW);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user5", ApplicationScreen.SUMMARY, AccessLevel.CREATE);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user6", ApplicationScreen.SUMMARY, AccessLevel.MODIFY);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user7", ApplicationScreen.SUMMARY, AccessLevel.DELETE);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user8", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.VIEW);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user9", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.CREATE);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user10", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.MODIFY);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user11", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.DELETE);
            testAccessManagerClient.addUserToApplicationComponentAndAccessLevelMapping("user12", ApplicationScreen.SETTINGS, AccessLevel.VIEW);

            testAccessManagerClient.addGroupToApplicationComponentAndAccessLevelMapping("group1", ApplicationScreen.SETTINGS, AccessLevel.CREATE);
            testAccessManagerClient.addGroupToApplicationComponentAndAccessLevelMapping("group1", ApplicationScreen.SETTINGS, AccessLevel.MODIFY);
            testAccessManagerClient.addGroupToApplicationComponentAndAccessLevelMapping("group2", ApplicationScreen.SETTINGS, AccessLevel.DELETE);
            testAccessManagerClient.addGroupToApplicationComponentAndAccessLevelMapping("group3", ApplicationScreen.DELIVERY, AccessLevel.VIEW);
            testAccessManagerClient.addGroupToApplicationComponentAndAccessLevelMapping("group4", ApplicationScreen.DELIVERY, AccessLevel.CREATE);
            testAccessManagerClient.addGroupToApplicationComponentAndAccessLevelMapping("group5", ApplicationScreen.DELIVERY, AccessLevel.MODIFY);
            testAccessManagerClient.addGroupToApplicationComponentAndAccessLevelMapping("group6", ApplicationScreen.DELIVERY, AccessLevel.DELETE);

            testAccessManagerClient.addUserToEntityMapping("user1", CLIENT_ACCOUNTS, COMPANY_1);
            testAccessManagerClient.addUserToEntityMapping("user1", CLIENT_ACCOUNTS, COMPANY_2);
            testAccessManagerClient.addUserToEntityMapping("user2", CLIENT_ACCOUNTS, COMPANY_3);
            testAccessManagerClient.addUserToEntityMapping("user3", CLIENT_ACCOUNTS, COMPANY_4);
            testAccessManagerClient.addUserToEntityMapping("user4", CLIENT_ACCOUNTS, COMPANY_5);
            testAccessManagerClient.addUserToEntityMapping("user5", CLIENT_ACCOUNTS, COMPANY_6);
            testAccessManagerClient.addUserToEntityMapping("user6", CLIENT_ACCOUNTS, COMPANY_7);
            testAccessManagerClient.addUserToEntityMapping("user7", CLIENT_ACCOUNTS, COMPANY_8);
            testAccessManagerClient.addUserToEntityMapping("user8", CLIENT_ACCOUNTS, COMPANY_9);
            testAccessManagerClient.addUserToEntityMapping("user9", CLIENT_ACCOUNTS, COMPANY_10);
            testAccessManagerClient.addUserToEntityMapping("user10", PRODUCT_LINES, LINE_1);
            testAccessManagerClient.addUserToEntityMapping("user11", PRODUCT_LINES, LINE_2);
            testAccessManagerClient.addUserToEntityMapping("user12", PRODUCT_LINES, LINE_3);

            testAccessManagerClient.addGroupToEntityMapping("group1", PRODUCT_LINES, LINE_4);
            testAccessManagerClient.addGroupToEntityMapping("group1", PRODUCT_LINES, LINE_5);
            testAccessManagerClient.addGroupToEntityMapping("group2", PRODUCT_LINES, LINE_6);
            testAccessManagerClient.addGroupToEntityMapping("group3", PRODUCT_LINES, LINE_7);
            testAccessManagerClient.addGroupToEntityMapping("group4", PRODUCT_LINES, LINE_8);
            testAccessManagerClient.addGroupToEntityMapping("group5", PRODUCT_LINES, LINE_9);
            testAccessManagerClient.addGroupToEntityMapping("group6", PRODUCT_LINES, LINE_10);
            testAccessManagerClient.addGroupToEntityMapping("group6", CLIENT_ACCOUNTS, COMPANY_1);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create elements.", e);
        }
    }

    @Test
    @Order(5) 
    public void queries() {

        // Test get*() methods
        try {
            List<String> allUsers = testAccessManagerClient.getUsers();
            assertEquals(14, allUsers.size());
            assertTrue(allUsers.contains("user1"));
            assertTrue(allUsers.contains("user2"));
            assertTrue(allUsers.contains("user3"));
            assertTrue(allUsers.contains("user4"));
            assertTrue(allUsers.contains("user5"));
            assertTrue(allUsers.contains("user6"));
            assertTrue(allUsers.contains("user7"));
            assertTrue(allUsers.contains("user8"));
            assertTrue(allUsers.contains("user9"));
            assertTrue(allUsers.contains("user10"));
            assertTrue(allUsers.contains("user11"));
            assertTrue(allUsers.contains("user12"));
            assertTrue(allUsers.contains("unmappedUser1"));
            assertTrue(allUsers.contains("orphanedUser"));
            
            List<String> allGroups = testAccessManagerClient.getGroups();
            assertEquals(9, allGroups.size());
            assertTrue(allGroups.contains("group1"));
            assertTrue(allGroups.contains("group2"));
            assertTrue(allGroups.contains("group3"));
            assertTrue(allGroups.contains("group4"));
            assertTrue(allGroups.contains("group5"));
            assertTrue(allGroups.contains("group6"));
            assertTrue(allGroups.contains("unmappedGroup1"));
            assertTrue(allGroups.contains("unmappedGroup2"));
            assertTrue(allGroups.contains("orphanedGroup"));
            
            List<String> allEntityTypes = testAccessManagerClient.getEntityTypes();
            assertEquals(3, allEntityTypes.size());
            assertTrue(allEntityTypes.contains(CLIENT_ACCOUNTS));
            assertTrue(allEntityTypes.contains(PRODUCT_LINES));
            assertTrue(allEntityTypes.contains(UNMAPPED));

            boolean containsResult = testAccessManagerClient.containsUser("user1");
            assertTrue(containsResult);
            containsResult = testAccessManagerClient.containsUser("user99");
            assertFalse(containsResult);

            containsResult = testAccessManagerClient.containsGroup("group1");
            assertTrue(containsResult);
            containsResult = testAccessManagerClient.containsGroup("group99");
            assertFalse(containsResult);

            List<String> userToGroupMappings = testAccessManagerClient.getUserToGroupMappings("user5", false);
            assertEquals(1, userToGroupMappings.size());
            assertTrue(userToGroupMappings.contains("group3"));
            userToGroupMappings = testAccessManagerClient.getUserToGroupMappings("user5", true);
            assertEquals(2, userToGroupMappings.size());
            assertTrue(userToGroupMappings.contains("group3"));
            assertTrue(userToGroupMappings.contains("group5"));
            userToGroupMappings = testAccessManagerClient.getUserToGroupMappings("orphanedUser", false);
            assertEquals(0, userToGroupMappings.size());

            List<String> groupToUserMappings = testAccessManagerClient.getGroupToUserMappings("group3", false);
            assertEquals(2, groupToUserMappings.size());
            assertTrue(groupToUserMappings.contains("user5"));
            assertTrue(groupToUserMappings.contains("user6"));
            groupToUserMappings = testAccessManagerClient.getGroupToUserMappings("group3", true);
            assertEquals(4, groupToUserMappings.size());
            assertTrue(groupToUserMappings.contains("user1"));
            assertTrue(groupToUserMappings.contains("user2"));
            assertTrue(groupToUserMappings.contains("user5"));
            assertTrue(groupToUserMappings.contains("user6"));
            groupToUserMappings = testAccessManagerClient.getGroupToUserMappings("orphanedGroup", false);
            assertEquals(0, groupToUserMappings.size());

            List<String> groupToGroupMappings = testAccessManagerClient.getGroupToGroupMappings("group2", false);
            assertEquals(1, groupToGroupMappings.size());
            assertTrue(groupToGroupMappings.contains("group4"));
            groupToGroupMappings = testAccessManagerClient.getGroupToGroupMappings("group2", true);
            assertEquals(3, groupToGroupMappings.size());
            assertTrue(groupToGroupMappings.contains("group4"));
            assertTrue(groupToGroupMappings.contains("group5"));
            assertTrue(groupToGroupMappings.contains("group6"));
            groupToUserMappings = testAccessManagerClient.getGroupToGroupMappings("orphanedGroup", false);
            assertEquals(0, groupToUserMappings.size());

            List<String> groupToGroupReverseMappings = testAccessManagerClient.getGroupToGroupReverseMappings("group6", false);
            assertEquals(1, groupToGroupReverseMappings.size());
            assertTrue(groupToGroupReverseMappings.contains("group4"));
            groupToGroupReverseMappings = testAccessManagerClient.getGroupToGroupReverseMappings("group6", true);
            assertEquals(3, groupToGroupReverseMappings.size());
            assertTrue(groupToGroupReverseMappings.contains("group1"));
            assertTrue(groupToGroupReverseMappings.contains("group2"));
            assertTrue(groupToGroupReverseMappings.contains("group4"));
            groupToGroupReverseMappings = testAccessManagerClient.getGroupToGroupReverseMappings("orphanedGroup", false);
            assertEquals(0, groupToUserMappings.size());

            List<ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>> userComponentMappings = testAccessManagerClient.getUserToApplicationComponentAndAccessLevelMappings("user5");
            assertEquals(1, userComponentMappings.size());
            assertTrue(userComponentMappings.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.SUMMARY, AccessLevel.CREATE)));
            userComponentMappings = testAccessManagerClient.getUserToApplicationComponentAndAccessLevelMappings("orphanedUser");
            assertEquals(0, userComponentMappings.size());

            List<String> users = testAccessManagerClient.getApplicationComponentAndAccessLevelToUserMappings(ApplicationScreen.SETTINGS, AccessLevel.VIEW, false);
            assertEquals(1, users.size());
            assertTrue(users.contains("user12"));
            users = testAccessManagerClient.getApplicationComponentAndAccessLevelToUserMappings(ApplicationScreen.DELIVERY, AccessLevel.VIEW, true);
            assertEquals(4, users.size());
            assertTrue(users.contains("user1"));
            assertTrue(users.contains("user2"));
            assertTrue(users.contains("user5"));
            assertTrue(users.contains("user6"));
            users = testAccessManagerClient.getApplicationComponentAndAccessLevelToUserMappings(ApplicationScreen.DELIVERY, AccessLevel.VIEW, false);
            assertEquals(0, users.size());

            List<ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>> groupComponentMappings = testAccessManagerClient.getGroupToApplicationComponentAndAccessLevelMappings("group4");
            assertEquals(1, groupComponentMappings.size());
            assertTrue(groupComponentMappings.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.DELIVERY, AccessLevel.CREATE)));
            groupComponentMappings = testAccessManagerClient.getGroupToApplicationComponentAndAccessLevelMappings("orphanedGroup");
            assertEquals(0, groupComponentMappings.size());

            List<String> groups = testAccessManagerClient.getApplicationComponentAndAccessLevelToGroupMappings(ApplicationScreen.DELIVERY, AccessLevel.VIEW, false);
            assertEquals(1, groups.size());
            assertTrue(groups.contains("group3"));
            groups = testAccessManagerClient.getApplicationComponentAndAccessLevelToGroupMappings(ApplicationScreen.DELIVERY, AccessLevel.VIEW, true);
            assertEquals(2, groups.size());
            assertTrue(groups.contains("group1"));
            assertTrue(groups.contains("group3"));
            groups = testAccessManagerClient.getApplicationComponentAndAccessLevelToGroupMappings(ApplicationScreen.SUMMARY, AccessLevel.VIEW, false);
            assertEquals(0, groups.size());

            containsResult = testAccessManagerClient.containsEntityType(PRODUCT_LINES);
            assertTrue(containsResult);
            containsResult = testAccessManagerClient.containsEntityType("Invalid");
            assertFalse(containsResult);

            List<String> entities = testAccessManagerClient.getEntities(PRODUCT_LINES);
            assertEquals(10, entities.size());
            assertTrue(entities.contains(LINE_1));
            assertTrue(entities.contains(LINE_2));
            assertTrue(entities.contains(LINE_3));
            assertTrue(entities.contains(LINE_4));
            assertTrue(entities.contains(LINE_5));
            assertTrue(entities.contains(LINE_6));
            assertTrue(entities.contains(LINE_7));
            assertTrue(entities.contains(LINE_8));
            assertTrue(entities.contains(LINE_9));
            assertTrue(entities.contains(LINE_10));
            entities = testAccessManagerClient.getEntities(UNMAPPED);
            assertEquals(0, entities.size());

            containsResult = testAccessManagerClient.containsEntity(PRODUCT_LINES, LINE_1);
            assertTrue(containsResult);
            containsResult = testAccessManagerClient.containsEntity(PRODUCT_LINES, "Invalid");
            assertFalse(containsResult);

            List<EntityTypeAndEntity> userEntityMappings = testAccessManagerClient.getUserToEntityMappings("user6");
            assertEquals(1, userEntityMappings.size());
            assertTrue(userEntityMappings.contains(new EntityTypeAndEntity(CLIENT_ACCOUNTS, COMPANY_7)));
            userEntityMappings = testAccessManagerClient.getUserToEntityMappings("orphanedUser");
            assertEquals(0, userEntityMappings.size());

            entities = testAccessManagerClient.getUserToEntityMappings("user3", CLIENT_ACCOUNTS);
            assertEquals(1, entities.size());
            assertTrue(entities.contains("Company4"));
            entities = testAccessManagerClient.getUserToEntityMappings("orphanedUser", CLIENT_ACCOUNTS);
            assertEquals(0, entities.size());

            users = testAccessManagerClient.getEntityToUserMappings(CLIENT_ACCOUNTS, COMPANY_1, false);
            assertEquals(1, users.size());
            assertTrue(users.contains("user1"));
            users = testAccessManagerClient.getEntityToUserMappings(CLIENT_ACCOUNTS, COMPANY_1, true);
            assertEquals(8, users.size());
            assertTrue(users.contains("user1"));
            assertTrue(users.contains("user2"));
            assertTrue(users.contains("user3"));
            assertTrue(users.contains("user4"));
            assertTrue(users.contains("user7"));
            assertTrue(users.contains("user8"));
            assertTrue(users.contains("user11"));
            assertTrue(users.contains("user12"));
            users = testAccessManagerClient.getEntityToUserMappings(PRODUCT_LINES, LINE_10, false);
            assertEquals(0, users.size());

            List<EntityTypeAndEntity> groupEntityMappings = testAccessManagerClient.getGroupToEntityMappings("group4");
            assertEquals(1, groupEntityMappings.size());
            assertTrue(groupEntityMappings.contains(new EntityTypeAndEntity(PRODUCT_LINES, LINE_8)));
            groupEntityMappings = testAccessManagerClient.getGroupToEntityMappings("orphanedGroup");
            assertEquals(0, groupEntityMappings.size());

            entities = testAccessManagerClient.getGroupToEntityMappings("group2", PRODUCT_LINES);
            assertEquals(1, entities.size());
            assertTrue(entities.contains(LINE_6));
            entities = testAccessManagerClient.getGroupToEntityMappings("orphanedGroup", PRODUCT_LINES);
            assertEquals(0, entities.size());
            
            groups = testAccessManagerClient.getEntityToGroupMappings(CLIENT_ACCOUNTS, COMPANY_1, false);
            assertEquals(1, groups.size());
            assertTrue(groups.contains("group6"));
            groups = testAccessManagerClient.getEntityToGroupMappings(CLIENT_ACCOUNTS, COMPANY_1, true);
            assertEquals(4, groups.size());
            assertTrue(groups.contains("group1"));
            assertTrue(groups.contains("group2"));
            assertTrue(groups.contains("group4"));
            assertTrue(groups.contains("group6"));
            groups = testAccessManagerClient.getEntityToGroupMappings(CLIENT_ACCOUNTS, COMPANY_2, false);
            assertEquals(0, groups.size());

            boolean hasAccessResult = testAccessManagerClient.hasAccessToApplicationComponent("user1", ApplicationScreen.ORDER, AccessLevel.VIEW);
            assertTrue(hasAccessResult);
            hasAccessResult = testAccessManagerClient.hasAccessToApplicationComponent("user12", ApplicationScreen.ORDER, AccessLevel.VIEW);
            assertFalse(hasAccessResult);

            hasAccessResult = testAccessManagerClient.hasAccessToEntity("user1", CLIENT_ACCOUNTS, COMPANY_1);
            assertTrue(hasAccessResult);
            hasAccessResult = testAccessManagerClient.hasAccessToEntity("user12", CLIENT_ACCOUNTS, COMPANY_2);
            assertFalse(hasAccessResult);

            Set<ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>> userComponentMappingsSet = testAccessManagerClient.getApplicationComponentsAccessibleByUser("user8");
            assertEquals(4, userComponentMappingsSet.size());
            assertTrue(userComponentMappingsSet.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.VIEW)));
            assertTrue(userComponentMappingsSet.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.DELIVERY, AccessLevel.CREATE)));
            assertTrue(userComponentMappingsSet.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.DELIVERY, AccessLevel.DELETE)));
            assertTrue(userComponentMappingsSet.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.DELIVERY, AccessLevel.MODIFY)));
            userComponentMappingsSet = testAccessManagerClient.getApplicationComponentsAccessibleByUser("orphanedUser");
            assertEquals(0, userComponentMappingsSet.size());

            Set<ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>> groupComponentMappingsSet = testAccessManagerClient.getApplicationComponentsAccessibleByGroup("group4");
            assertEquals(3, groupComponentMappingsSet.size());
            assertTrue(groupComponentMappingsSet.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.DELIVERY, AccessLevel.CREATE)));
            assertTrue(groupComponentMappingsSet.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.DELIVERY, AccessLevel.DELETE)));
            assertTrue(groupComponentMappingsSet.contains(new ApplicationComponentAndAccessLevel<ApplicationScreen, AccessLevel>(ApplicationScreen.DELIVERY, AccessLevel.MODIFY)));
            groupComponentMappingsSet = testAccessManagerClient.getApplicationComponentsAccessibleByGroup("orphanedGroup");
            assertEquals(0, groupComponentMappingsSet.size());

            Set<EntityTypeAndEntity> userEntityMappingsSet = testAccessManagerClient.getEntitiesAccessibleByUser("user5");
            assertEquals(3, userEntityMappingsSet.size());
            assertTrue(userEntityMappingsSet.contains(new EntityTypeAndEntity(CLIENT_ACCOUNTS, COMPANY_6)));
            assertTrue(userEntityMappingsSet.contains(new EntityTypeAndEntity(PRODUCT_LINES, LINE_7)));
            assertTrue(userEntityMappingsSet.contains(new EntityTypeAndEntity(PRODUCT_LINES, LINE_9)));
            userEntityMappingsSet = testAccessManagerClient.getEntitiesAccessibleByUser("orphanedUser");
            assertEquals(0, userEntityMappingsSet.size());

            Set<String> entitiesSet = testAccessManagerClient.getEntitiesAccessibleByUser("user5", PRODUCT_LINES);
            assertEquals(2, entitiesSet.size());
            assertTrue(entitiesSet.contains(LINE_7));
            assertTrue(entitiesSet.contains(LINE_9));
            entitiesSet = testAccessManagerClient.getEntitiesAccessibleByUser("orphanedUser", PRODUCT_LINES);
            assertEquals(0, entitiesSet.size());

            Set<EntityTypeAndEntity> groupEntityMappingsSet = testAccessManagerClient.getEntitiesAccessibleByGroup("group4");
            assertEquals(4, groupEntityMappingsSet.size());
            assertTrue(groupEntityMappingsSet.contains(new EntityTypeAndEntity(CLIENT_ACCOUNTS, COMPANY_1)));
            assertTrue(groupEntityMappingsSet.contains(new EntityTypeAndEntity(PRODUCT_LINES, LINE_8)));
            assertTrue(groupEntityMappingsSet.contains(new EntityTypeAndEntity(PRODUCT_LINES, LINE_9)));
            assertTrue(groupEntityMappingsSet.contains(new EntityTypeAndEntity(PRODUCT_LINES, LINE_10)));
            groupEntityMappingsSet = testAccessManagerClient.getEntitiesAccessibleByGroup("orphanedGroup");
            assertEquals(0, groupEntityMappingsSet.size());

            entitiesSet = testAccessManagerClient.getEntitiesAccessibleByGroup("group4", PRODUCT_LINES);
            assertEquals(3, entitiesSet.size());
            assertTrue(entitiesSet.contains(LINE_8));
            assertTrue(entitiesSet.contains(LINE_9));
            assertTrue(entitiesSet.contains(LINE_10));
            entitiesSet = testAccessManagerClient.getEntitiesAccessibleByGroup("orphanedGroup", PRODUCT_LINES);
            assertEquals(0, entitiesSet.size());
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to check elements.", e);
        }
    }

    @Test
    @Order(99) 
    public void removeElementsAndMappings() {

        try {
            testAccessManagerClient.removeGroupToEntityMapping("group6", CLIENT_ACCOUNTS, COMPANY_1);
            testAccessManagerClient.removeGroupToEntityMapping("group6", PRODUCT_LINES, LINE_10);
            testAccessManagerClient.removeGroupToEntityMapping("group5", PRODUCT_LINES, LINE_9);
            testAccessManagerClient.removeGroupToEntityMapping("group4", PRODUCT_LINES, LINE_8);
            testAccessManagerClient.removeGroupToEntityMapping("group3", PRODUCT_LINES, LINE_7);
            testAccessManagerClient.removeGroupToEntityMapping("group2", PRODUCT_LINES, LINE_6);
            testAccessManagerClient.removeGroupToEntityMapping("group1", PRODUCT_LINES, LINE_5);
            testAccessManagerClient.removeGroupToEntityMapping("group1", PRODUCT_LINES, LINE_4);

            testAccessManagerClient.removeUserToEntityMapping("user12", PRODUCT_LINES, LINE_3);
            testAccessManagerClient.removeUserToEntityMapping("user11", PRODUCT_LINES, LINE_2);
            testAccessManagerClient.removeUserToEntityMapping("user10", PRODUCT_LINES, LINE_1);
            testAccessManagerClient.removeUserToEntityMapping("user9", CLIENT_ACCOUNTS, COMPANY_10);
            testAccessManagerClient.removeUserToEntityMapping("user8", CLIENT_ACCOUNTS, COMPANY_9);
            testAccessManagerClient.removeUserToEntityMapping("user7", CLIENT_ACCOUNTS, COMPANY_8);
            testAccessManagerClient.removeUserToEntityMapping("user6", CLIENT_ACCOUNTS, COMPANY_7);
            testAccessManagerClient.removeUserToEntityMapping("user5", CLIENT_ACCOUNTS, COMPANY_6);
            testAccessManagerClient.removeUserToEntityMapping("user4", CLIENT_ACCOUNTS, COMPANY_5);
            testAccessManagerClient.removeUserToEntityMapping("user3", CLIENT_ACCOUNTS, COMPANY_4);
            testAccessManagerClient.removeUserToEntityMapping("user2", CLIENT_ACCOUNTS, COMPANY_3);
            testAccessManagerClient.removeUserToEntityMapping("user1", CLIENT_ACCOUNTS, COMPANY_2);
            testAccessManagerClient.removeUserToEntityMapping("user1", CLIENT_ACCOUNTS, COMPANY_1);

            testAccessManagerClient.removeGroupToApplicationComponentAndAccessLevelMapping("group6", ApplicationScreen.DELIVERY, AccessLevel.DELETE);
            testAccessManagerClient.removeGroupToApplicationComponentAndAccessLevelMapping("group5", ApplicationScreen.DELIVERY, AccessLevel.MODIFY);
            testAccessManagerClient.removeGroupToApplicationComponentAndAccessLevelMapping("group4", ApplicationScreen.DELIVERY, AccessLevel.CREATE);
            testAccessManagerClient.removeGroupToApplicationComponentAndAccessLevelMapping("group3", ApplicationScreen.DELIVERY, AccessLevel.VIEW);
            testAccessManagerClient.removeGroupToApplicationComponentAndAccessLevelMapping("group2", ApplicationScreen.SETTINGS, AccessLevel.DELETE);
            testAccessManagerClient.removeGroupToApplicationComponentAndAccessLevelMapping("group1", ApplicationScreen.SETTINGS, AccessLevel.MODIFY);
            testAccessManagerClient.removeGroupToApplicationComponentAndAccessLevelMapping("group1", ApplicationScreen.SETTINGS, AccessLevel.CREATE);

            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user12", ApplicationScreen.SETTINGS, AccessLevel.VIEW);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user11", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.DELETE);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user10", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.MODIFY);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user9", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.CREATE);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user8", ApplicationScreen.MANAGE_PRODUCTS, AccessLevel.VIEW);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user7", ApplicationScreen.SUMMARY, AccessLevel.DELETE);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user6", ApplicationScreen.SUMMARY, AccessLevel.MODIFY);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user5", ApplicationScreen.SUMMARY, AccessLevel.CREATE);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user4", ApplicationScreen.SUMMARY, AccessLevel.VIEW);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user3", ApplicationScreen.ORDER, AccessLevel.DELETE);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user2", ApplicationScreen.ORDER, AccessLevel.MODIFY);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user1", ApplicationScreen.ORDER, AccessLevel.CREATE);
            testAccessManagerClient.removeUserToApplicationComponentAndAccessLevelMapping("user1", ApplicationScreen.ORDER, AccessLevel.VIEW);

            testAccessManagerClient.removeGroupToGroupMapping("unmappedGroup1", "unmappedGroup2");
            testAccessManagerClient.removeGroupToGroupMapping("group4", "group6");
            testAccessManagerClient.removeGroupToGroupMapping("group4", "group5");
            testAccessManagerClient.removeGroupToGroupMapping("group3", "group5");
            testAccessManagerClient.removeGroupToGroupMapping("group2", "group4");
            testAccessManagerClient.removeGroupToGroupMapping("group1", "group4");
            testAccessManagerClient.removeGroupToGroupMapping("group1", "group3");

            testAccessManagerClient.removeUserToGroupMapping("unmappedUser1", "unmappedGroup1");
            testAccessManagerClient.removeUserToGroupMapping("user12", "group6");
            testAccessManagerClient.removeUserToGroupMapping("user11", "group6");
            testAccessManagerClient.removeUserToGroupMapping("user10", "group5");
            testAccessManagerClient.removeUserToGroupMapping("user9", "group5");
            testAccessManagerClient.removeUserToGroupMapping("user8", "group4");
            testAccessManagerClient.removeUserToGroupMapping("user7", "group4");
            testAccessManagerClient.removeUserToGroupMapping("user6", "group3");
            testAccessManagerClient.removeUserToGroupMapping("user5", "group3");
            testAccessManagerClient.removeUserToGroupMapping("user4", "group2");
            testAccessManagerClient.removeUserToGroupMapping("user3", "group2");
            testAccessManagerClient.removeUserToGroupMapping("user2", "group1");
            testAccessManagerClient.removeUserToGroupMapping("user1", "group1");

            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_10);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_9);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_8);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_7);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_6);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_5);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_4);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_3);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_2);
            testAccessManagerClient.removeEntity(PRODUCT_LINES, LINE_1);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_10);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_9);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_8);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_7);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_6);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_5);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_4);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_3);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_2);
            testAccessManagerClient.removeEntity(CLIENT_ACCOUNTS, COMPANY_1);
            testAccessManagerClient.removeEntityType(UNMAPPED);
            testAccessManagerClient.removeEntityType(PRODUCT_LINES);
            testAccessManagerClient.removeEntityType(CLIENT_ACCOUNTS);

            testAccessManagerClient.removeGroup("orphanedGroup");
            testAccessManagerClient.removeGroup("unmappedGroup2");
            testAccessManagerClient.removeGroup("unmappedGroup1");
            testAccessManagerClient.removeGroup("group6");
            testAccessManagerClient.removeGroup("group5");
            testAccessManagerClient.removeGroup("group4");
            testAccessManagerClient.removeGroup("group3");
            testAccessManagerClient.removeGroup("group2");
            testAccessManagerClient.removeGroup("group1");

            testAccessManagerClient.removeUser("orphanedUser");
            testAccessManagerClient.removeUser("unmappedUser1");
            testAccessManagerClient.removeUser("user12");
            testAccessManagerClient.removeUser("user11");
            testAccessManagerClient.removeUser("user10");
            testAccessManagerClient.removeUser("user9");
            testAccessManagerClient.removeUser("user8");
            testAccessManagerClient.removeUser("user7");
            testAccessManagerClient.removeUser("user6");
            testAccessManagerClient.removeUser("user5");
            testAccessManagerClient.removeUser("user4");
            testAccessManagerClient.removeUser("user3");
            testAccessManagerClient.removeUser("user2");
            testAccessManagerClient.removeUser("user1");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to remove elements.", e);
        }
    }
}
