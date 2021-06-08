package com.vcb.edit.datefield.exceptions;

/**
 * Exception thrown for general causes from {@code DateField} class
 */
public class DateFieldException extends RuntimeException {

    /**
     * Default constructor
     */
    public DateFieldException() {
        super();
    }

    /**
     * Constructor
     * @param message the message to be thrown
     */
    public DateFieldException(String message) {
        super(message);
    }
}
