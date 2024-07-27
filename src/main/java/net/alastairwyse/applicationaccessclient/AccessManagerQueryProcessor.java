package net.alastairwyse.applicationaccessclient;

import java.util.*;

import net.alastairwyse.applicationaccessclient.models.*;

/**
 * Defines methods which query the state/structure of an AccessManager implementation.
 * 
 * @param <TUser> The type of users in the application.
 * @param <TGroup> The type of groups in the application.
 * @param <TComponent> The type of components in the application to manage access to.
 * @param <TAccess> The type of levels of access which can be assigned to an application component.
 */
public interface AccessManagerQueryProcessor<TUser, TGroup, TComponent, TAccess> {

    /**
     * @return Returns a collection of all users in the access manager.
     */
    public List<TUser> getUsers() throws Exception;
    
    /**
     * @return Returns a collection of all groups in the access manager.
     */
    public List<TGroup> getGroups() throws Exception;
    
    /**
     * @return Returns a collection of all entity types in the access manager.
     */
    public List<String> getEntityTypes() throws Exception;

    /**
     * Returns true if the specified user exists.
     * 
     * @param user The user check for.
     * @return True if the user exists.  False otherwise.
     */
    public boolean containsUser(TUser user) throws Exception;

    /**
     * Returns true if the specified group exists.
     * 
     * @param group The group check for.
     * @return True if the group exists.  False otherwise.
     */
    public boolean containsGroup(TGroup group) throws Exception;

    /**
     * Gets the groups that the specified user is mapped to (i.e. is a member of).
     * 
     * @param user The user to retrieve the groups for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those that occur via group to group mappings).
     * @return A collection of groups the specified user is a member of.
     */
    public List<TGroup> getUserToGroupMappings(TUser user, boolean includeIndirectMappings) throws Exception;

    /**
     * Gets the users that are mapped to the specified group.
     * 
     * @param group The group to retrieve the users for.
     * @param includeIndirectMappings >Whether to include indirect mappings (i.e. those where a user is mapped to the group via other groups).
     * @return A collection of users that are mapped to the specified group.
     */
    public List<TUser> getGroupToUserMappings(TGroup group, Boolean includeIndirectMappings) throws Exception;

    /**
     * Gets the groups that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mapped groups for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where the 'mapped to' group is itself mapped to further groups).
     * @return A collection of groups the specified group is mapped to.
     */
    public List<TGroup> getGroupToGroupMappings(TGroup group, boolean includeIndirectMappings) throws Exception;

    /**
     * Gets the groups that are mapped to the specified group.
     * 
     * @param group The group to retrieve the mapped groups for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where the 'mapped from' group is itself mapped from further groups).
     * @return A collection of groups that are mapped to the specified group.
     */
    public List<TGroup> getGroupToGroupReverseMappings(TGroup group, Boolean includeIndirectMappings) throws Exception;

    /**
     * Gets the application component and access level pairs that the specified user is mapped to.
     * 
     * @param user The user to retrieve the mappings for.
     * @return A collection of {@link ApplicationComponentAndAccessLevel} pairs that the specified user is mapped to.
     */
    public List<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getUserToApplicationComponentAndAccessLevelMappings(TUser user) throws Exception;

    /**
     * Gets the users that are mapped to the specified application component and access level pair.
     * 
     * @param applicationComponent The application component to retrieve the mappings for.
     * @param accessLevel The access level to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a user is mapped to an application component and access level via groups).
     * @return A collection of users that are mapped to the specified application component and access level.
     */
    public List<TUser> getApplicationComponentAndAccessLevelToUserMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) throws Exception;

    /**
     * Gets the application component and access level pairs that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mappings for.
     * @return A collection of {@link ApplicationComponentAndAccessLevel} pairs that the specified group is mapped to.
     */
    public List<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getGroupToApplicationComponentAndAccessLevelMappings(TGroup group) throws Exception;

    /**
     * Gets the groups that are mapped to the specified application component and access level pair.
     * 
     * @param applicationComponent The application component to retrieve the mappings for.
     * @param accessLevel The access level to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a group is mapped to an application component and access level via other groups).
     * @return A collection of groups that are mapped to the specified application component and access level.
     */
    public List<TGroup> getApplicationComponentAndAccessLevelToGroupMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings) throws Exception;

    /**
     * Returns true if the specified entity type exists.
     * 
     * @param entityType The entity type to check for.
     * @return True if the entity type exists.  False otherwise.
     */
    public boolean containsEntityType(String entityType) throws Exception;

    /**
     * Returns all entities of the specified type.
     * 
     * @param entityType The type of the entity.
     * @return A collection of all entities of the specified type.
     */
    public List<String> getEntities(String entityType) throws Exception;

    /**
     * Returns true if the specified entity exists.
     * 
     * @param entityType The type of the entity.
     * @param entity The entity to check for.
     * @return True if the entity exists.  False otherwise.
     */
    public boolean containsEntity(String entityType, String entity) throws Exception;

    /**
     * Gets the entities that the specified user is mapped to.
     * 
     * @param user The user to retrieve the mappings for.
     * @return A collection of {@link EntityTypeAndEntity} that the specified user is mapped to.
     */
    public List<EntityTypeAndEntity> getUserToEntityMappings(TUser user) throws Exception;

    /**
     * Gets the entities of a given type that the specified user is mapped to.
     * 
     * @param user The user to retrieve the mappings for.
     * @param entityType The entity type to retrieve the mappings for.
     * @return A collection of entities that the specified user is mapped to.
     */
    public List<String> getUserToEntityMappings(TUser user, String entityType) throws Exception;

    /**
     * Gets the users that are mapped to the specified entity.
     * 
     * @param entityType The entity type to retrieve the mappings for.
     * @param entity The entity to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a user is mapped to the entity via groups).
     * @return A collection of users that are mapped to the specified entity.
     */
    public List<TUser> getEntityToUserMappings(String entityType, String entity, Boolean includeIndirectMappings) throws Exception;

    /**
     * Gets the entities that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mappings for.
     * @return A collection of {@link EntityTypeAndEntity} that the specified group is mapped to.
     */
    public List<EntityTypeAndEntity> getGroupToEntityMappings(TGroup group) throws Exception;

    /**
     * Gets the entities of a given type that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mappings for.
     * @param entityType The entity type to retrieve the mappings for.
     * @return A collection of entities that the specified group is mapped to.
     */
    public List<String> getGroupToEntityMappings(TGroup group, String entityType) throws Exception;

    /**
     * Gets the groups that are mapped to the specified entity.
     * 
     * @param entityType The entity type to retrieve the mappings for.
     * @param entity The entity to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a group is mapped to the entity via other groups).
     * @return A collection of groups that are mapped to the specified entity.
     */
    public List<TGroup> getEntityToGroupMappings(String entityType, String entity, Boolean includeIndirectMappings) throws Exception;
    
    /**
     * Checks whether the specified user (or a group that the user is a member of) has access to an application component at the specified level of access.
     * 
     * @param user The user to check for.
     * @param applicationComponent The application component.
     * @param accessLevel The level of access to the component.
     * @return True if the user has access the component.  False otherwise.
     */
    public boolean hasAccessToApplicationComponent(TUser user, TComponent applicationComponent, TAccess accessLevel) throws Exception;

    /**
     * Checks whether the specified user (or a group that the user is a member of) has access to the specified entity.
     * 
     * @param user The user to check for.
     * @param entityType The type of the entity.
     * @param entity The entity.
     * @return True if the user has access the entity.  False otherwise.
     */
    public boolean hasAccessToEntity(TUser user, String entityType, String entity) throws Exception;

    /**
     * Gets all application components and levels of access that the specified user (or a group that the user is a member of) has access to.
     * 
     * @param user The user to retrieve the application components and levels of access for.
     * @return The application components and levels of access to those application components that the user has access to.
     */
    public Set<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByUser(TUser user) throws Exception;

    /**
     * Gets all application components and levels of access that the specified group (or group that the specified group is mapped to) has access to.
     * 
     * @param group The group to retrieve the application components and levels of access for.
     * @return The application components and levels of access to those application components that the group has access to.
     */
    public Set<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByGroup(TGroup group) throws Exception;

    /**
     * Gets all entities that the specified user (or a group that the user is a member of) has access to.
     * 
     * @param user The user to retrieve the entities for.
     * @return A collection of Tuples containing the entity type and entity that the user has access to.
     */
    public Set<EntityTypeAndEntity> getEntitiesAccessibleByUser(TUser user) throws Exception;

    /**
     * Gets all entities of a given type that the specified user (or a group that the user is a member of) has access to.
     * 
     * @param user The user to retrieve the entities for.
     * @param entityType The type of entities to retrieve.
     * @return The entities the user has access to.
     */
    public Set<String> getEntitiesAccessibleByUser(TUser user, String entityType) throws Exception;

    /**
     * Gets all entities that the specified group (or a group that the specified group is a member of) has access to.
     * 
     * @param group The group to retrieve the entities for.
     * @return A collection of Tuples containing the entity type and entity that the group has access to.
     */
    public Set<EntityTypeAndEntity> getEntitiesAccessibleByGroup(TGroup group) throws Exception;

    /**
     * Gets all entities of a given type that the specified group (or a group that the specified group is a member of) has access to.
     * 
     * @param group The group to retrieve the entities for.
     * @param entityType The type of entities to retrieve.
     * @return The entities the group has access to.
     */
    public Set<String> getEntitiesAccessibleByGroup(TGroup group, String entityType) throws Exception;
}