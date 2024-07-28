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


    private String serverUrl = "http://127.0.0.1:5170/";
    private UniqueStringifier<ApplicationScreen> componentStringifier;
    private UniqueStringifier<AccessLevel> accessLevelStringifier;
    private AccessManagerClient<String, String, ApplicationScreen, AccessLevel> testAccessManagerClient;

    @Before
    public void setUp() {

        URI serveUri = null;
        try {
            serveUri = new URI(serverUrl);
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
                        throw new RuntimeException(String.format("Unhandled ApplicationScreen screen value '%s'.", inputObject));
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
            serveUri, 
            new StringUniqueStringifier(), 
            new StringUniqueStringifier(), 
            componentStringifier, 
            accessLevelStringifier
        );
    }

    @Test
    public void fullTest() {
        
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



            //boolean containsResult = testAccessManagerClient.containsUser("Alastair");
            //List<String> getResult = testAccessManagerClient.getUsers();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed setup elements.", e);
        }
    }
}
