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

package net.alastairwyse.applicationaccessclient.exceptions;

/**
 * The exception that is thrown when a resource doesn't exist or could not be found.
 */
public class NotFoundException extends RuntimeException {
    
    /** A unique identifier for the resource. */
    protected String resourceId;

    /**
     * @return A unique identifier for the resource.
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Constructs a NotFoundException.
     * 
     * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     * @param resourceId A unique identifier for the resource.
     */
    public NotFoundException(String message, String resourceId) {
        super(message);
        this.resourceId = resourceId;
    }
}
