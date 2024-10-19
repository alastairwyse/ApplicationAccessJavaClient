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

package net.alastairwyse.applicationaccessclient;

/**
 * Defines methods for converting objects of a specified type to and from strings which uniquely identify the object.
 * 
 * @param <T> The type of objects to convert.
 */
public interface UniqueStringifier<T> {

    /**
     * Converts an object into a string which uniquely identifies that object.
     * 
     * @param inputObject The object to convert.
     * @return A string which uniquely identifies that object.
     */
    String toString(T inputObject);

    /**
     * Converts a string which uniquely identifies an object into the object.
     * 
     * @param stringifiedObject The string representing the object.
     * @return The object.
     */
    T fromString(String stringifiedObject);
}
