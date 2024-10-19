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
 * The exception that is thrown when a element was not found in an AccessManager instance.
 */
public class ElementNotFoundException extends NotFoundException {

    /** The type of the element  */
    protected String elementType;

    /**
     * @return The type of the element.
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * @return The value of the element.
     */
    public String getElementValue() {
        return resourceId;
    }

    /**
     * Constructs an ElementNotFoundException.
     * 
     * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     * @param elementType The type of the element.
     * @param elementValue The value of the element.
     */
    public ElementNotFoundException(String message, String elementType, String elementValue) {
        super(message, elementValue);
        this.elementType = elementType;
    }
}