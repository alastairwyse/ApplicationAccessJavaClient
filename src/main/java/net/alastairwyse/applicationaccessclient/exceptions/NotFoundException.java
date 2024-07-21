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
