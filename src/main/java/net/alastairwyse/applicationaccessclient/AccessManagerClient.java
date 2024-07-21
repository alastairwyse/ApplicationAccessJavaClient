package net.alastairwyse.applicationaccessclient;

import java.util.HashSet;

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
public class AccessManagerClient<TUser, TGroup, TComponent, TAccess> implements
    AccessManagerEventProcessor<TUser, TGroup, TComponent, TAccess>, 
    AccessManagerQueryProcessor<TUser, TGroup, TComponent, TAccess> {

    @Override
    public Iterable<String> getUsers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<String> getGroups() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<String> getEntityTypes() {
        throw new UnsupportedOperationException();
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
    public HashSet<TGroup> getUserToGroupMappings(TUser user, boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<TUser> getGroupToUserMappings(TGroup group, Boolean includeIndirectMappings) {
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
    public HashSet<TGroup> getGroupToGroupMappings(TGroup group, boolean includeIndirectMappings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<TGroup> getGroupToGroupReverseMappings(TGroup group, Boolean includeIndirectMappings) {
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
    public Iterable<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getUserToApplicationComponentAndAccessLevelMappings(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<TUser> getApplicationComponentAndAccessLevelToUserMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) {
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
    public Iterable<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getGroupToApplicationComponentAndAccessLevelMappings(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<TGroup> getApplicationComponentAndAccessLevelToGroupMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) {
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
    public Iterable<String> getEntities(String entityType) {
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
    public Iterable<EntityTypeAndEntity> getUserToEntityMappings(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<String> getUserToEntityMappings(TUser user, String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<TUser> getEntityToUserMappings(String entityType, String entity, Boolean includeIndirectMappings) {
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
    public Iterable<EntityTypeAndEntity> getGroupToEntityMappings(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<String> getGroupToEntityMappings(TGroup group, String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<TGroup> getEntityToGroupMappings(String entityType, String entity, Boolean includeIndirectMappings) {
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
    public HashSet<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByUser(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByGroup(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<EntityTypeAndEntity> getEntitiesAccessibleByUser(TUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<String> getEntitiesAccessibleByUser(TUser user, String entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<EntityTypeAndEntity> getEntitiesAccessibleByGroup(TGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<String> getEntitiesAccessibleByGroup(TGroup group, String entityType) {
        throw new UnsupportedOperationException();
    }
}
