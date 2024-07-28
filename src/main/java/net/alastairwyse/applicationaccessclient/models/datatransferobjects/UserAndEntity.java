package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding a user, an entity type, and an entity of that type.
 */
public class UserAndEntity extends EntityTypeAndEntity {
    
    @JsonAlias({ "user" })
    public String User;
}
