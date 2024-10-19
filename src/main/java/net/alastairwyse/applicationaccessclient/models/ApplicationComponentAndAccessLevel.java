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
 * Container class holding an application component and level of access.
 * 
 * @param <TComponent> The type of components in the application to manage access to.
 * @param <TAccess> The type of levels of access which can be assigned to an application component.
 */
public class ApplicationComponentAndAccessLevel<TComponent, TAccess> {
    
    protected int prime1 = 7;
    protected int prime2 = 11;

    /** The application component. */
    protected TComponent applicationComponent;
    /** The level of access to the application component. */
    protected TAccess accessLevel;

    /** 
     * @return The application component. 
     */
    public TComponent getApplicationComponent() {
        return applicationComponent;
    }

    /** 
     * @return The level of access to the application component.
     */
    public TAccess getAccessLevel() {
        return accessLevel;
    }

    /**
     * Constructs an ApplicationComponentAndAccessLevel.
     * 
     * @param applicationComponent The application component. 
     * @param accessLevel The level of access to the application component.
     */
    public ApplicationComponentAndAccessLevel(TComponent applicationComponent, TAccess accessLevel) {
        this.applicationComponent = applicationComponent;
        this.accessLevel = accessLevel;
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
        ApplicationComponentAndAccessLevel<TComponent, TAccess> typedOther = (ApplicationComponentAndAccessLevel<TComponent, TAccess>)other;

        return (this.applicationComponent.equals(typedOther.applicationComponent) && this.accessLevel.equals(typedOther.accessLevel));
    }

    @Override
    public int hashCode() {
        return (this.applicationComponent.hashCode() * prime1 + this.accessLevel.hashCode() * prime2);
    }
}
