package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding a user and a group.
 */
public class UserAndGroup {
    
    @JsonAlias({ "user" })
    public String User;

    @JsonAlias({ "group" })
    public String Group;
}
