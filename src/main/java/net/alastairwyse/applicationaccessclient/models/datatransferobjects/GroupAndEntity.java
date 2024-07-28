package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding a group, an entity type, and an entity of that type.
 */
public class GroupAndEntity extends EntityTypeAndEntity {
    
    @JsonAlias({ "group" })
    public String Group;
}
