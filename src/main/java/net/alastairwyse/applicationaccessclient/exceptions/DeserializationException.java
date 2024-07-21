package net.alastairwyse.applicationaccessclient.exceptions;

/**
 * The exception that is thrown when deserialization fails.
 */
public class DeserializationException extends Exception {
    
    /**
     * Constructs a DeserializationException.
     * 
     * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public DeserializationException(String message) {
        super(message);
    }
    
    /**
     * Constructs a DeserializationException.
     * 
     * @param message The detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

