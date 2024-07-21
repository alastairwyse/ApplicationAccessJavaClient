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
