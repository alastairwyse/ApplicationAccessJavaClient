package net.alastairwyse.applicationaccessclient;

/**
 * Defines methods to process events which change the structure of an AccessManager implementation.
 *
 * @param <TUser> The type of users in the application.
 * @param <TGroup> The type of groups in the application.
 * @param <TComponent> The type of components in the application to manage access to.
 * @param <TAccess> The type of levels of access which can be assigned to an application component.
 */
public interface AccessManagerEventProcessor<TUser, TGroup, TComponent, TAccess> {
    
    /**
     * Adds a user.
     * 
     * @param user The user to add.
     */
    public void addUser(TUser user) throws Exception;

    /**
     * Removes a user.
     * 
     * @param user The user to remove.
     */
    public void removeUser(TUser user) throws Exception;

    /**
     * Adds a group.
     * 
     * @param group The group to add.
     */
    public void addGroup(TGroup group) throws Exception;

    /**
     * Removes a group.
     * 
     * @param group The group to remove.
     */
    public void removeGroup(TGroup group) throws Exception;

    /**
     * Adds a mapping between the specified user and group.
     * 
     * @param user The user in the mapping.
     * @param group The group in the mapping.
     */
    public void addUserToGroupMapping(TUser user, TGroup group) throws Exception;

    /**
     * Removes the mapping between the specified user and group.
     * 
     * @param user The user in the mapping.
     * @param group The group in the mapping.
     */
    public void removeUserToGroupMapping(TUser user, TGroup group) throws Exception;

    /**
     * Adds a mapping between the specified groups.
     * 
     * @param fromGroup The 'from' group in the mapping.
     * @param toGroup The 'to' group in the mapping.
     */
    public void addGroupToGroupMapping(TGroup fromGroup, TGroup toGroup) throws Exception;

    /**
     * Removes the mapping between the specified groups.
     * 
     * @param fromGroup The 'from' group in the mapping.
     * @param toGroup The 'to' group in the mapping.
     */
    public void removeGroupToGroupMapping(TGroup fromGroup, TGroup toGroup) throws Exception;

    /**
     * Adds a mapping between the specified user, application component, and level of access to that component.
     * 
     * @param user The user in the mapping.
     * @param applicationComponent The application component in the mapping.
     * @param accessLevel The level of access to the component.
     */
    public void addUserToApplicationComponentAndAccessLevelMapping(TUser user, TComponent applicationComponent, TAccess accessLevel) throws Exception;

    /**
     * Removes a mapping between the specified user, application component, and level of access to that component.
     * 
     * @param user The user in the mapping.
     * @param applicationComponent The application component in the mapping.
     * @param accessLevel The level of access to the component.
     */
    public void removeUserToApplicationComponentAndAccessLevelMapping(TUser user, TComponent applicationComponent, TAccess accessLevel) throws Exception;

    /**
     * Adds a mapping between the specified group, application component, and level of access to that component.
     * 
     * @param group The group in the mapping.
     * @param applicationComponent The application component in the mapping.
     * @param accessLevel The level of access to the component.
     */
    public void addGroupToApplicationComponentAndAccessLevelMapping(TGroup group, TComponent applicationComponent, TAccess accessLevel) throws Exception;

    /**
     * Removes a mapping between the specified group, application component, and level of access to that component.
     * 
     * @param group The group in the mapping.
     * @param applicationComponent The application component in the mapping.
     * @param accessLevel The level of access to the component.
     */
    public void removeGroupToApplicationComponentAndAccessLevelMapping(TGroup group, TComponent applicationComponent, TAccess accessLevel) throws Exception;

    /**
     * Adds an entity type.
     * 
     * @param entityType The entity type to add.
     */
    public void addEntityType(String entityType) throws Exception;

    /**
     * Removes an entity type.
     * 
     * @param entityType The entity type to remove.
     */
    public void removeEntityType(String entityType) throws Exception;

    /**
     * Adds an entity.
     * 
     * @param entityType The type of the entity.
     * @param entity The entity to add.
     */
    public void addEntity(String entityType, String entity) throws Exception;

    /**
     * Removes an entity.
     * 
     * @param entityType The type of the entity.
     * @param entity The entity to remove.
     */
    public void removeEntity(String entityType, String entity) throws Exception;

    /**
     * Removes a mapping between the specified user, and entity.
     * 
     * @param user The user in the mapping.
     * @param entityType The type of the entity.
     * @param entity The entity in the mapping.
     */
    public void addUserToEntityMapping(TUser user, String entityType, String entity) throws Exception;

    /**
     * Removes a mapping between the specified user, and entity.
     * 
     * @param user The user in the mapping.
     * @param entityType The type of the entity.
     * @param entity The entity in the mapping.
     */
    public void removeUserToEntityMapping(TUser user, String entityType, String entity) throws Exception;

    /**
     * Removes a mapping between the specified group, and entity.
     * 
     * @param group The group in the mapping.
     * @param entityType The type of the entity.
     * @param entity The entity in the mapping.
     */
    public void addGroupToEntityMapping(TGroup group, String entityType, String entity) throws Exception;

    /**
     * Removes a mapping between the specified group, and entity.
     * 
     * @param group The group in the mapping.
     * @param entityType The type of the entity.
     * @param entity The entity in the mapping.
     */
    public void removeGroupToEntityMapping(TGroup group, String entityType, String entity) throws Exception;
}
