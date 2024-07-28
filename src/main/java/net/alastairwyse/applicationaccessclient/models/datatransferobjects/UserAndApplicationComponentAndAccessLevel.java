package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding a user, an application component, and level of access to that component.
 */
public class UserAndApplicationComponentAndAccessLevel extends ApplicationComponentAndAccessLevel{
    
    @JsonAlias({ "user" })
    public String User;
}
