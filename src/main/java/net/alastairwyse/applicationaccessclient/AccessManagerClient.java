package net.alastairwyse.applicationaccessclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer;

import java.net.URI;
import java.net.http.HttpClient;

import net.alastairwyse.applicationaccessclient.models.ApplicationComponentAndAccessLevel;
import net.alastairwyse.applicationaccessclient.models.EntityTypeAndEntity;

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
     * Constructs an AccessManagerClientBase.
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
     * @inheritDoc
     * @exception RuntimeException If a non-success response status was received.
     * @exception RuntimeException If the response could not be deserialized to an object.
     * @exception IOException If an I/O error occurs when sending or receiving, or the client has ##closing shut down.
     * @exception InterruptedException If the operation is interrupted.
     */
    @Override
    public List<TUser> getUsers() throws IOException, InterruptedException {

        var url = new URI(baseUrl.toString() + "users");
        ArrayList<String> rawResults = sendGetRequest(url, new TypeReference<ArrayList<String>>(){});
        var results = new ArrayList<TUser>();
        for (String currentRawResult : rawResults) {
            results.add(userStringifier.fromString(currentRawResult))
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

        var url = new URI(baseUrl.toString() + "groups");
        ArrayList<String> rawResults = sendGetRequest(url, new TypeReference<ArrayList<String>>(){});
        var results = new ArrayList<TGroup>();
        for (String currentRawResult : rawResults) {
            results.add(groupStringifier.fromString(currentRawResult))
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

        URI url = appendPathToBaseUrl("entityTypes");

        return sendGetRequest(url, new TypeReference<ArrayList<String>>(){});
    }

    @Override
    public void addUser(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsUser(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeUser(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addGroup(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsGroup(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeGroup(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addUserToGroupMapping(TUser user, TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<TGroup> getUserToGroupMappings(TUser user, boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<TUser> getGroupToUserMappings(TGroup group, Boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeUserToGroupMapping(TUser user, TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addGroupToGroupMapping(TGroup fromGroup, TGroup toGroup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<TGroup> getGroupToGroupMappings(TGroup group, boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<TGroup> getGroupToGroupReverseMappings(TGroup group, Boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeGroupToGroupMapping(TGroup fromGroup, TGroup toGroup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addUserToApplicationComponentAndAccessLevelMapping(TUser user, TComponent applicationComponent, TAccess accessLevel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getUserToApplicationComponentAndAccessLevelMappings(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TUser> getApplicationComponentAndAccessLevelToUserMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeUserToApplicationComponentAndAccessLevelMapping(TUser user, TComponent applicationComponent, TAccess accessLevel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addGroupToApplicationComponentAndAccessLevelMapping(TGroup group, TComponent applicationComponent, TAccess accessLevel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getGroupToApplicationComponentAndAccessLevelMappings(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TGroup> getApplicationComponentAndAccessLevelToGroupMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeGroupToApplicationComponentAndAccessLevelMapping(TGroup group, TComponent applicationComponent, TAccess accessLevel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEntityType(String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsEntityType(String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEntityType(String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEntity(String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getEntities(String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsEntity(String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEntity(String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addUserToEntityMapping(TUser user, String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<EntityTypeAndEntity> getUserToEntityMappings(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getUserToEntityMappings(TUser user, String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TUser> getEntityToUserMappings(String entityType, String entity, Boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeUserToEntityMapping(TUser user, String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addGroupToEntityMapping(TGroup group, String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<EntityTypeAndEntity> getGroupToEntityMappings(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getGroupToEntityMappings(TGroup group, String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TGroup> getEntityToGroupMappings(String entityType, String entity, Boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeGroupToEntityMapping(TGroup group, String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasAccessToApplicationComponent(TUser user, TComponent applicationComponent, TAccess accessLevel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasAccessToEntity(TUser user, String entityType, String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByUser(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByGroup(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<EntityTypeAndEntity> getEntitiesAccessibleByUser(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getEntitiesAccessibleByUser(TUser user, String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<EntityTypeAndEntity> getEntitiesAccessibleByGroup(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getEntitiesAccessibleByGroup(TGroup group, String entityType) {
        throw new UnsupportedOperationException();
    }
}
