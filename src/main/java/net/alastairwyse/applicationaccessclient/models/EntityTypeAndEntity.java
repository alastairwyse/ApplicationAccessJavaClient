/*
 * Copyright 2024 Alastair Wyse (https://github.com/alastairwyse/ApplicationAccessJavaClient/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
