package net.alastairwyse.applicationaccessclient.models;

/**
 * Container class holding an entity type and entity of that type.
 */
public class EntityTypeAndEntity {

    protected int prime1 = 7;
    protected int prime2 = 11;
    
    /** The type of the entity. */
    protected String entityType;
    /** The entity. */
    protected String entity;

    /** 
     * @return The type of the entity.
     */
    public String getEntityType() {
        return entityType;
    }

    /** 
     * @return The entity.
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Constructs an EntityTypeAndEntity.
     * 
     * @param entityType The type of the entity.
     * @param entity The entity.
     */
    public EntityTypeAndEntity(String entityType, String entity) {
        this.entityType = entityType;
        this.entity = entity;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        EntityTypeAndEntity typedOther = (EntityTypeAndEntity)other;

        return (this.entityType.equals(typedOther.entityType) && this.entity.equals(typedOther.entity));
    }

    @Override
    public int hashCode() {
        return (this.entityType.hashCode() * prime1 + this.entity.hashCode() * prime2);
    }
}
