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