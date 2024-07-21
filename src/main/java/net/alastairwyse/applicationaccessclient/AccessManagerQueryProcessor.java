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
    public Iterable<String> getUsers();
    
    /**
     * @return Returns a collection of all groups in the access manager.
     */
    public Iterable<String> getGroups();
    
    /**
     * @return Returns a collection of all entity types in the access manager.
     */
    public Iterable<String> getEntityTypes();

    /**
     * Returns true if the specified user exists.
     * 
     * @param user The user check for.
     * @return True if the user exists.  False otherwise.
     */
    public boolean containsUser(TUser user);

    /**
     * Returns true if the specified group exists.
     * 
     * @param group The group check for.
     * @return True if the group exists.  False otherwise.
     */
    public boolean containsGroup(TGroup group);

    /**
     * Gets the groups that the specified user is mapped to (i.e. is a member of).
     * 
     * @param user The user to retrieve the groups for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those that occur via group to group mappings).
     * @return A collection of groups the specified user is a member of.
     */
    public HashSet<TGroup> getUserToGroupMappings(TUser user, boolean includeIndirectMappings);

    /**
     * Gets the users that are mapped to the specified group.
     * 
     * @param group The group to retrieve the users for.
     * @param includeIndirectMappings >Whether to include indirect mappings (i.e. those where a user is mapped to the group via other groups).
     * @return A collection of users that are mapped to the specified group.
     */
    public HashSet<TUser> getGroupToUserMappings(TGroup group, Boolean includeIndirectMappings);

    /**
     * Gets the groups that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mapped groups for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where the 'mapped to' group is itself mapped to further groups).
     * @return A collection of groups the specified group is mapped to.
     */
    public HashSet<TGroup> getGroupToGroupMappings(TGroup group, boolean includeIndirectMappings);

    /**
     * Gets the groups that are mapped to the specified group.
     * 
     * @param group The group to retrieve the mapped groups for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where the 'mapped from' group is itself mapped from further groups).
     * @return A collection of groups that are mapped to the specified group.
     */
    public HashSet<TGroup> getGroupToGroupReverseMappings(TGroup group, Boolean includeIndirectMappings);

    /**
     * Gets the application component and access level pairs that the specified user is mapped to.
     * 
     * @param user The user to retrieve the mappings for.
     * @return A collection of {@link ApplicationComponentAndAccessLevel} pairs that the specified user is mapped to.
     */
    public Iterable<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getUserToApplicationComponentAndAccessLevelMappings(TUser user);

    /**
     * Gets the users that are mapped to the specified application component and access level pair.
     * 
     * @param applicationComponent The application component to retrieve the mappings for.
     * @param accessLevel The access level to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a user is mapped to an application component and access level via groups).
     * @return A collection of users that are mapped to the specified application component and access level.
     */
    public Iterable<TUser> getApplicationComponentAndAccessLevelToUserMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings);

    /**
     * Gets the application component and access level pairs that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mappings for.
     * @return A collection of {@link ApplicationComponentAndAccessLevel} pairs that the specified group is mapped to.
     */
    public Iterable<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getGroupToApplicationComponentAndAccessLevelMappings(TGroup group);

    /**
     * Gets the groups that are mapped to the specified application component and access level pair.
     * 
     * @param applicationComponent The application component to retrieve the mappings for.
     * @param accessLevel The access level to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a group is mapped to an application component and access level via other groups).
     * @return A collection of groups that are mapped to the specified application component and access level.
     */
    public Iterable<TGroup> getApplicationComponentAndAccessLevelToGroupMappings(TComponent applicationComponent, TAccess accessLevel, Boolean includeIndirectMappings);

    /**
     * Returns true if the specified entity type exists.
     * 
     * @param entityType The entity type to check for.
     * @return True if the entity type exists.  False otherwise.
     */
    public boolean containsEntityType(String entityType);

    /**
     * Returns all entities of the specified type.
     * 
     * @param entityType The type of the entity.
     * @return A collection of all entities of the specified type.
     */
    public Iterable<String> getEntities(String entityType);

    /**
     * Returns true if the specified entity exists.
     * 
     * @param entityType The type of the entity.
     * @param entity The entity to check for.
     * @return True if the entity exists.  False otherwise.
     */
    public boolean containsEntity(String entityType, String entity);

    /**
     * Gets the entities that the specified user is mapped to.
     * 
     * @param user The user to retrieve the mappings for.
     * @return A collection of {@link EntityTypeAndEntity} that the specified user is mapped to.
     */
    public Iterable<EntityTypeAndEntity> getUserToEntityMappings(TUser user);

    /**
     * Gets the entities of a given type that the specified user is mapped to.
     * 
     * @param user The user to retrieve the mappings for.
     * @param entityType The entity type to retrieve the mappings for.
     * @return A collection of entities that the specified user is mapped to.
     */
    public Iterable<String> getUserToEntityMappings(TUser user, String entityType);

    /**
     * Gets the users that are mapped to the specified entity.
     * 
     * @param entityType The entity type to retrieve the mappings for.
     * @param entity The entity to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a user is mapped to the entity via groups).
     * @return A collection of users that are mapped to the specified entity.
     */
    public Iterable<TUser> getEntityToUserMappings(String entityType, String entity, Boolean includeIndirectMappings);

    /**
     * Gets the entities that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mappings for.
     * @return A collection of {@link EntityTypeAndEntity} that the specified group is mapped to.
     */
    public Iterable<EntityTypeAndEntity> getGroupToEntityMappings(TGroup group);

    /**
     * Gets the entities of a given type that the specified group is mapped to.
     * 
     * @param group The group to retrieve the mappings for.
     * @param entityType The entity type to retrieve the mappings for.
     * @return A collection of entities that the specified group is mapped to.
     */
    public Iterable<String> getGroupToEntityMappings(TGroup group, String entityType);

    /**
     * Gets the groups that are mapped to the specified entity.
     * 
     * @param entityType The entity type to retrieve the mappings for.
     * @param entity The entity to retrieve the mappings for.
     * @param includeIndirectMappings Whether to include indirect mappings (i.e. those where a group is mapped to the entity via other groups).
     * @return A collection of groups that are mapped to the specified entity.
     */
    public Iterable<TGroup> getEntityToGroupMappings(String entityType, String entity, Boolean includeIndirectMappings);
    
    /**
     * Checks whether the specified user (or a group that the user is a member of) has access to an application component at the specified level of access.
     * 
     * @param user The user to check for.
     * @param applicationComponent The application component.
     * @param accessLevel The level of access to the component.
     * @return True if the user has access the component.  False otherwise.
     */
    public boolean hasAccessToApplicationComponent(TUser user, TComponent applicationComponent, TAccess accessLevel);

    /**
     * Checks whether the specified user (or a group that the user is a member of) has access to the specified entity.
     * 
     * @param user The user to check for.
     * @param entityType The type of the entity.
     * @param entity The entity.
     * @return True if the user has access the entity.  False otherwise.
     */
    public boolean hasAccessToEntity(TUser user, String entityType, String entity);

    /**
     * Gets all application components and levels of access that the specified user (or a group that the user is a member of) has access to.
     * 
     * @param user The user to retrieve the application components and levels of access for.
     * @return The application components and levels of access to those application components that the user has access to.
     */
    public HashSet<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByUser(TUser user);

    /**
     * Gets all application components and levels of access that the specified group (or group that the specified group is mapped to) has access to.
     * 
     * @param group The group to retrieve the application components and levels of access for.
     * @return The application components and levels of access to those application components that the group has access to.
     */
    public HashSet<ApplicationComponentAndAccessLevel<TComponent, TAccess>> getApplicationComponentsAccessibleByGroup(TGroup group);

    /**
     * Gets all entities that the specified user (or a group that the user is a member of) has access to.
     * 
     * @param user The user to retrieve the entities for.
     * @return A collection of Tuples containing the entity type and entity that the user has access to.
     */
    public HashSet<EntityTypeAndEntity> getEntitiesAccessibleByUser(TUser user);

    /**
     * Gets all entities of a given type that the specified user (or a group that the user is a member of) has access to.
     * 
     * @param user The user to retrieve the entities for.
     * @param entityType The type of entities to retrieve.
     * @return The entities the user has access to.
     */
    public HashSet<String> getEntitiesAccessibleByUser(TUser user, String entityType);

    /**
     * Gets all entities that the specified group (or a group that the specified group is a member of) has access to.
     * 
     * @param group The group to retrieve the entities for.
     * @return A collection of Tuples containing the entity type and entity that the group has access to.
     */
    public HashSet<EntityTypeAndEntity> getEntitiesAccessibleByGroup(TGroup group);

    /**
     * Gets all entities of a given type that the specified group (or a group that the specified group is a member of) has access to.
     * 
     * @param group The group to retrieve the entities for.
     * @param entityType The type of entities to retrieve.
     * @return The entities the group has access to.
     */
    public HashSet<String> getEntitiesAccessibleByGroup(TGroup group, String entityType);
}