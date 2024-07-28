package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding a mapping between two groups.
 */
public class FromGroupAndToGroup {
    
    @JsonAlias({ "fromGroup" })
    public String FromGroup;

    @JsonAlias({ "toGroup" })
    public String ToGroup;
}
