package net.alastairwyse.applicationaccessclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.alastairwyse.applicationaccessclient.models.ApplicationComponentAndAccessLevel;
import net.alastairwyse.applicationaccessclient.models.EntityTypeAndEntity;
import net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndGroup;
import net.alastairwyse.applicationaccessclient.models.datatransferobjects.FromGroupAndToGroup;

/**
 * Client class which interfaces to an AccessManager instance hosted as a REST web API.
 * 
 * @param <TUser> The type of users in the AccessManager.
 * @param <TGroup> The type of groups in the AccessManager.
 * @param <TComponent> The type of components in the AccessManager.
 * @param <TAccess> The type of levels of access which can be assigned to an application component.
 */
public class AccessManagerClient<TUser, TGroup, TComponent, TAccess> 
    extends AccessManagerClientBase<TUser, TGroup, TComponent, TAccess> 
    implements AccessManagerEventProcessor<TUser, TGroup, TComponent, TAccess>, 
    AccessManagerQueryProcessor<TUser, TGroup, TComponent, TAccess> {

    /**
     * Constructs an AccessManagerClient.
     * 
     * @param baseUrl The base URL for the hosted Web API.
     * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
     * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
     * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     */
    public AccessManagerClient(
        URI baseUrl, 
        UniqueStringifier<TUser> userStringifier, 
        UniqueStringifier<TGroup> groupStringifier, 
        UniqueStringifier<TComponent> applicationComponentStringifier, 
        UniqueStringifier<TAccess> accessLevelStringifier
    ) {
        super(baseUrl, userStringifier, groupStringifier, applicationComponentStringifier, accessLevelStringifier);
    }

    /**
     * Constructs an AccessManagerClient.
     * 
     * @param httpClient The client to use to connect.
     * @param baseUrl The base URL for the hosted Web API.
     * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
     * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
     * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     */
    public AccessManagerClient(
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
     * Constructs an AccessManagerClient.
     * 
     * @param httpClient The client to use to connect.
     * @param baseUrl The base URL for the hosted Web API.
     * @param userStringifier A string converter for users.  Used to convert strings sent to and received from the web API from/to TUser instances.
     * @param groupStringifier A string converter for groups.  Used to convert strings sent to and received from the web API from/to TGroup instances.
     * @param applicationComponentStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     * @param accessLevelStringifier A string converter for access levels.  Used to convert strings sent to and received from the web API from/to TAccess instances.
     * @param requestHeaders HTTP headers to send with each request.
     */
    public AccessManagerClient(
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
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TUser> getUsers() throws IOException, InterruptedException {

        var url = appendPathToBaseUrl("users");
        ArrayList<String> rawResults = sendGetRequest(url, new TypeReference<ArrayList<String>>(){});
        var results = new ArrayList<TUser>();
        for (String currentRawResult : rawResults) {
            results.add(userStringifier.fromString(currentRawResult));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TGroup> getGroups() throws IOException, InterruptedException {

        var url = appendPathToBaseUrl("groups");
        ArrayList<String> rawResults = sendGetRequest(url, new TypeReference<ArrayList<String>>(){});
        var results = new ArrayList<TGroup>();
        for (String currentRawResult : rawResults) {
            results.add(groupStringifier.fromString(currentRawResult));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<String> getEntityTypes() throws IOException, InterruptedException {

        var url = appendPathToBaseUrl("entityTypes");

        return sendGetRequest(url, new TypeReference<ArrayList<String>>(){});
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addUser(TUser user) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("users/%s", 
                userStringifier.toString(user)
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public boolean containsUser(TUser user) throws IOException, InterruptedException {
        
        var url = appendPathToBaseUrl(String.format("users/%s",
                userStringifier.toString(user)
            )
        );

        return sendGetRequestForContainsMethod(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeUser(TUser user) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("users/%s", 
                userStringifier.toString(user)
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addGroup(TGroup group) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groups/%s", 
                groupStringifier.toString(group)
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public boolean containsGroup(TGroup group) throws IOException, InterruptedException {
        
        var url = appendPathToBaseUrl(String.format("groups/%s",
                groupStringifier.toString(group)
            )
        );

        return sendGetRequestForContainsMethod(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeGroup(TGroup group) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groups/%s", 
                groupStringifier.toString(group)
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addUserToGroupMapping(TUser user, TGroup group) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToGroupMappings/user/%s/group/%s", 
                userStringifier.toString(user), 
                groupStringifier.toString(group)
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TGroup> getUserToGroupMappings(TUser user, boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToGroupMappings/user/%s?includeIndirectMappings=%s",
                userStringifier.toString(user), 
                includeIndirectMappings
            )
        );
        ArrayList<UserAndGroup> rawResults = sendGetRequest(url, new TypeReference<ArrayList<UserAndGroup>>(){});
        var results = new ArrayList<TGroup>();
        for (UserAndGroup currentRawResult : rawResults) {
            results.add(groupStringifier.fromString(currentRawResult.Group));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TUser> getGroupToUserMappings(TGroup group, Boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToGroupMappings/group/%s?includeIndirectMappings=%s",
                groupStringifier.toString(group), 
                includeIndirectMappings
            )
        );
        ArrayList<UserAndGroup> rawResults = sendGetRequest(url, new TypeReference<ArrayList<UserAndGroup>>(){});
        var results = new ArrayList<TUser>();
        for (UserAndGroup currentRawResult : rawResults) {
            results.add(userStringifier.fromString(currentRawResult.User));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeUserToGroupMapping(TUser user, TGroup group) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToGroupMappings/user/%s/group/%s", 
                userStringifier.toString(user), 
                groupStringifier.toString(group)
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addGroupToGroupMapping(TGroup fromGroup, TGroup toGroup) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToGroupMappings/fromGroup/%s/toGroup/%s", 
                groupStringifier.toString(fromGroup), 
                groupStringifier.toString(toGroup)
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TGroup> getGroupToGroupMappings(TGroup group, boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToGroupMappings/group/%s?includeIndirectMappings=%s",
                groupStringifier.toString(group), 
                includeIndirectMappings
            )
        );
        ArrayList<FromGroupAndToGroup> rawResults = sendGetRequest(url, new TypeReference<ArrayList<FromGroupAndToGroup>>(){});
        var results = new ArrayList<TGroup>();
        for (FromGroupAndToGroup currentRawResult : rawResults) {
            results.add(groupStringifier.fromString(currentRawResult.ToGroup));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TGroup> getGroupToGroupReverseMappings(TGroup group, Boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToGroupReverseMappings/group/%s?includeIndirectMappings=%s",
                groupStringifier.toString(group), 
                includeIndirectMappings
            )
        );
        ArrayList<FromGroupAndToGroup> rawResults = sendGetRequest(url, new TypeReference<ArrayList<FromGroupAndToGroup>>(){});
        var results = new ArrayList<TGroup>();
        for (FromGroupAndToGroup currentRawResult : rawResults) {
            results.add(groupStringifier.fromString(currentRawResult.FromGroup));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeGroupToGroupMapping(TGroup fromGroup, TGroup toGroup) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToGroupMappings/fromGroup/%s/toGroup/%s", 
                groupStringifier.toString(fromGroup), 
                groupStringifier.toString(toGroup)
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addUserToApplicationComponentAndAccessLevelMapping(TUser user, TComponent applicationComponent, TAccess accessLevel) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToApplicationComponentAndAccessLevelMappings/user/%s/applicationComponent/%s/accessLevel/%s", 
                userStringifier.toString(user), 
                applicationComponentStringifier.toString(applicationComponent), 
                accessLevelStringifier.toString(accessLevel)
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getUserToApplicationComponentAndAccessLevelMappings(TUser user) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToApplicationComponentAndAccessLevelMappings/user/%s?includeIndirectMappings=false",
                userStringifier.toString(user)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndApplicationComponentAndAccessLevel> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndApplicationComponentAndAccessLevel>>(){});
        var results = new ArrayList<ApplicationComponentAndAccessLevel<TComponent, TAccess>>();
        for (var currentRawResult : rawResults) {
            results.add(new ApplicationComponentAndAccessLevel<TComponent,TAccess>(
                applicationComponentStringifier.fromString(currentRawResult.ApplicationComponent), 
                accessLevelStringifier.fromString(currentRawResult.AccessLevel)
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TUser> getApplicationComponentAndAccessLevelToUserMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToApplicationComponentAndAccessLevelMappings/applicationComponent/%s/accessLevel/%s?includeIndirectMappings=%s",
                applicationComponentStringifier.toString(applicationComponent), 
                accessLevelStringifier.toString(accessLevel), 
                includeIndirectMappings
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndApplicationComponentAndAccessLevel> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndApplicationComponentAndAccessLevel>>(){});
        var results = new ArrayList<TUser>();
        for (var currentRawResult : rawResults) {
            results.add(userStringifier.fromString(currentRawResult.User));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeUserToApplicationComponentAndAccessLevelMapping(TUser user, TComponent applicationComponent, TAccess accessLevel) throws IOException, InterruptedException {
        
        var url = appendPathToBaseUrl(String.format("userToApplicationComponentAndAccessLevelMappings/user/%s/applicationComponent/%s/accessLevel/%s", 
                userStringifier.toString(user), 
                applicationComponentStringifier.toString(applicationComponent), 
                accessLevelStringifier.toString(accessLevel)
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addGroupToApplicationComponentAndAccessLevelMapping(TGroup group, TComponent applicationComponent, TAccess accessLevel) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToApplicationComponentAndAccessLevelMappings/group/%s/applicationComponent/%s/accessLevel/%s", 
                groupStringifier.toString(group), 
                applicationComponentStringifier.toString(applicationComponent), 
                accessLevelStringifier.toString(accessLevel)
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getGroupToApplicationComponentAndAccessLevelMappings(TGroup group) throws IOException, InterruptedException {
        
        var url = appendPathToBaseUrl(String.format("groupToApplicationComponentAndAccessLevelMappings/group/%s?includeIndirectMappings=false",
                groupStringifier.toString(group)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndApplicationComponentAndAccessLevel> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndApplicationComponentAndAccessLevel>>(){});
        var results = new ArrayList<ApplicationComponentAndAccessLevel<TComponent, TAccess>>();
        for (var currentRawResult : rawResults) {
            results.add(new ApplicationComponentAndAccessLevel<TComponent,TAccess>(
                applicationComponentStringifier.fromString(currentRawResult.ApplicationComponent), 
                accessLevelStringifier.fromString(currentRawResult.AccessLevel)
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TGroup> getApplicationComponentAndAccessLevelToGroupMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToApplicationComponentAndAccessLevelMappings/applicationComponent/%s/accessLevel/%s?includeIndirectMappings=%s",
                applicationComponentStringifier.toString(applicationComponent), 
                accessLevelStringifier.toString(accessLevel), 
                includeIndirectMappings
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndApplicationComponentAndAccessLevel> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndApplicationComponentAndAccessLevel>>(){});
        var results = new ArrayList<TGroup>();
        for (var currentRawResult : rawResults) {
            results.add(groupStringifier.fromString(currentRawResult.Group));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeGroupToApplicationComponentAndAccessLevelMapping(TGroup group, TComponent applicationComponent, TAccess accessLevel) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToApplicationComponentAndAccessLevelMappings/group/%s/applicationComponent/%s/accessLevel/%s", 
                groupStringifier.toString(group), 
                applicationComponentStringifier.toString(applicationComponent), 
                accessLevelStringifier.toString(accessLevel)
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addEntityType(String entityType) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("entityTypes/%s", entityType));
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public boolean containsEntityType(String entityType) throws IOException, InterruptedException {
        
        var url = appendPathToBaseUrl(String.format("entityTypes/%s",
                entityType
            )
        );

        return sendGetRequestForContainsMethod(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeEntityType(String entityType) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("entityTypes/%s", entityType));
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addEntity(String entityType, String entity) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("entityTypes/%s/entities/%s", entityType, entity));
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<String> getEntities(String entityType) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("entityTypes/%s/entities", entityType));
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.EntityTypeAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.EntityTypeAndEntity>>(){});
        var results = new ArrayList<String>();
        for (var currentRawResult : rawResults) {
            results.add(currentRawResult.Entity);
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public boolean containsEntity(String entityType, String entity) throws IOException, InterruptedException {
        
        var url = appendPathToBaseUrl(String.format("entityTypes/%s/entities/%s",
                entityType, 
                entity
            )
        );

        return sendGetRequestForContainsMethod(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeEntity(String entityType, String entity) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("entityTypes/%s/entities/%s", entityType, entity));
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addUserToEntityMapping(TUser user, String entityType, String entity) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToEntityMappings/user/%s/entityType/%s/entity/%s", 
                userStringifier.toString(user), 
                entityType, 
                entity
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<EntityTypeAndEntity> getUserToEntityMappings(TUser user) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToEntityMappings/user/%s?includeIndirectMappings=false",
                userStringifier.toString(user)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity>>(){});
        var results = new ArrayList<EntityTypeAndEntity>();
        for (var currentRawResult : rawResults) {
            results.add(new EntityTypeAndEntity(
                currentRawResult.EntityType, 
                currentRawResult.Entity
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<String> getUserToEntityMappings(TUser user, String entityType) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToEntityMappings/user/%s/entityType/%s?includeIndirectMappings=false",
                userStringifier.toString(user), 
                entityType
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity>>(){});
        var results = new ArrayList<String>();
        for (var currentRawResult : rawResults) {
            results.add(currentRawResult.Entity);
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TUser> getEntityToUserMappings(String entityType, String entity, Boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToEntityMappings/entityType/%s/entity/%s?includeIndirectMappings=%s",
                entityType, 
                entity, 
                includeIndirectMappings
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity>>(){});
        var results = new ArrayList<TUser>();
        for (var currentRawResult : rawResults) {
            results.add(userStringifier.fromString(currentRawResult.User));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeUserToEntityMapping(TUser user, String entityType, String entity) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToEntityMappings/user/%s/entityType/%s/entity/%s", 
                userStringifier.toString(user), 
                entityType, 
                entity
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void addGroupToEntityMapping(TGroup group, String entityType, String entity) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToEntityMappings/group/%s/entityType/%s/entity/%s", 
                groupStringifier.toString(group), 
                entityType, 
                entity
            )
        );
        sendPostRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<EntityTypeAndEntity> getGroupToEntityMappings(TGroup group) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToEntityMappings/group/%s?includeIndirectMappings=false",
                groupStringifier.toString(group)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity>>(){});
        var results = new ArrayList<EntityTypeAndEntity>();
        for (var currentRawResult : rawResults) {
            results.add(new EntityTypeAndEntity(
                currentRawResult.EntityType, 
                currentRawResult.Entity
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<String> getGroupToEntityMappings(TGroup group, String entityType) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToEntityMappings/group/%s/entityType/%s?includeIndirectMappings=false",
                groupStringifier.toString(group), 
                entityType
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity>>(){});
        var results = new ArrayList<String>();
        for (var currentRawResult : rawResults) {
            results.add(currentRawResult.Entity);
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TGroup> getEntityToGroupMappings(String entityType, String entity, Boolean includeIndirectMappings) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToEntityMappings/entityType/%s/entity/%s?includeIndirectMappings=%s",
                entityType, 
                entity, 
                includeIndirectMappings
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity>>(){});
        var results = new ArrayList<TGroup>();
        for (var currentRawResult : rawResults) {
            results.add(groupStringifier.fromString(currentRawResult.Group));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public void removeGroupToEntityMapping(TGroup group, String entityType, String entity) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToEntityMappings/group/%s/entityType/%s/entity/%s", 
                groupStringifier.toString(group), 
                entityType, 
                entity
            )
        );
        sendDeleteRequest(url);
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public boolean hasAccessToApplicationComponent(TUser user, TComponent applicationComponent, TAccess accessLevel) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("dataElementAccess/applicationComponent/user/%s/applicationComponent/%s/accessLevel/%s",
                userStringifier.toString(user), 
                applicationComponentStringifier.toString(applicationComponent), 
                accessLevelStringifier.toString(accessLevel)
            )
        );
        Boolean result = sendGetRequest(url, new TypeReference<Boolean>(){});

        return result;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public boolean hasAccessToEntity(TUser user, String entityType, String entity) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("dataElementAccess/entity/user/%s/entityType/%s/entity/%s",
                userStringifier.toString(user), 
                entityType, 
                entity
            )
        );
        Boolean result = sendGetRequest(url, new TypeReference<Boolean>(){});

        return result;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public Set<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByUser(TUser user) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToApplicationComponentAndAccessLevelMappings/user/%s?includeIndirectMappings=true",
                userStringifier.toString(user)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndApplicationComponentAndAccessLevel> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndApplicationComponentAndAccessLevel>>(){});
        var results = new HashSet<ApplicationComponentAndAccessLevel<TComponent, TAccess>>();
        for (var currentRawResult : rawResults) {
            results.add(new ApplicationComponentAndAccessLevel<TComponent,TAccess>(
                applicationComponentStringifier.fromString(currentRawResult.ApplicationComponent), 
                accessLevelStringifier.fromString(currentRawResult.AccessLevel)
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public Set<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByGroup(TGroup group) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToApplicationComponentAndAccessLevelMappings/group/%s?includeIndirectMappings=true",
                groupStringifier.toString(group)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndApplicationComponentAndAccessLevel> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndApplicationComponentAndAccessLevel>>(){});
        var results = new HashSet<ApplicationComponentAndAccessLevel<TComponent, TAccess>>();
        for (var currentRawResult : rawResults) {
            results.add(new ApplicationComponentAndAccessLevel<TComponent,TAccess>(
                applicationComponentStringifier.fromString(currentRawResult.ApplicationComponent), 
                accessLevelStringifier.fromString(currentRawResult.AccessLevel)
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public Set<EntityTypeAndEntity> getEntitiesAccessibleByUser(TUser user) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToEntityMappings/user/%s?includeIndirectMappings=true",
                userStringifier.toString(user)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity>>(){});
        var results = new HashSet<EntityTypeAndEntity>();
        for (var currentRawResult : rawResults) {
            results.add(new EntityTypeAndEntity(
                currentRawResult.EntityType, 
                currentRawResult.Entity
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public Set<String> getEntitiesAccessibleByUser(TUser user, String entityType) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("userToEntityMappings/user/%s/entityType/%s?includeIndirectMappings=true",
                userStringifier.toString(user), 
                entityType
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.UserAndEntity>>(){});
        var results = new HashSet<String>();
        for (var currentRawResult : rawResults) {
            results.add(currentRawResult.Entity);
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public Set<EntityTypeAndEntity> getEntitiesAccessibleByGroup(TGroup group) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToEntityMappings/group/%s?includeIndirectMappings=true",
                groupStringifier.toString(group)
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity>>(){});
        var results = new HashSet<EntityTypeAndEntity>();
        for (var currentRawResult : rawResults) {
            results.add(new EntityTypeAndEntity(
                currentRawResult.EntityType, 
                currentRawResult.Entity
            ));
        }

        return results;
    }

    /**
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public Set<String> getEntitiesAccessibleByGroup(TGroup group, String entityType) throws IOException, InterruptedException {

        var url = appendPathToBaseUrl(String.format("groupToEntityMappings/group/%s/entityType/%s?includeIndirectMappings=true",
                groupStringifier.toString(group), 
                entityType
            )
        );
        ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity> rawResults = sendGetRequest(url, new TypeReference<ArrayList<net.alastairwyse.applicationaccessclient.models.datatransferobjects.GroupAndEntity>>(){});
        var results = new HashSet<String>();
        for (var currentRawResult : rawResults) {
            results.add(currentRawResult.Entity);
        }

        return results;
    }
}