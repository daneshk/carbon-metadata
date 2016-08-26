package org.wso2.carbon.metadata.base.store.exceptions;

public class MetadataStoreException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public MetadataStoreException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of this exception.
     */
    public MetadataStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
