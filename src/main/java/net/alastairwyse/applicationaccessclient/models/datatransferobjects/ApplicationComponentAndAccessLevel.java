package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding an application component, and level of access to that component.
 */
public class ApplicationComponentAndAccessLevel {
    
    @JsonAlias({ "applicationComponent" })
    public String ApplicationComponent;

    @JsonAlias({ "accessLevel" })
    public String AccessLevel;
}
