package net.alastairwyse.applicationaccessclient.models.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * DTO container class holding an entity type, and an entity of that type.
 */
public class EntityTypeAndEntity {
    
    @JsonAlias({ "entityType" })
    public String EntityType;

    @JsonAlias({ "entity" })
    public String Entity;
}
