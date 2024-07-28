package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding a group, an application component, and level of access to that component.
 */
public class GroupAndApplicationComponentAndAccessLevel extends ApplicationComponentAndAccessLevel {
    
    @JsonAlias({ "group" })
    public String Group;
}
