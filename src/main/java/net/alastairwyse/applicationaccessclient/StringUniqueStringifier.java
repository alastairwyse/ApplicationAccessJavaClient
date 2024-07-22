package net.alastairwyse.applicationaccessclient;

import net.alastairwyse.applicationaccessclient.UniqueStringifier;

/**
 * An implementation of {@link UniqueStringifier} for strings.
 */
public class StringUniqueStringifier implements UniqueStringifier<String>{

    @Override
    public String toString(String inputObject) {
        
        return inputObject;
    }

    @Override
    public String fromString(String stringifiedObject) {
        
        return stringifiedObject;
    }
    
}
