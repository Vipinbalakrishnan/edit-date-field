package com.vcb.edit.datefield.exceptions;

/**
 * Exception thrown for validation from {@code DateField} class
 */
public class DateFieldInvalidArgumentException extends RuntimeException {

    /**
     * Default constructor
     */
    public DateFieldInvalidArgumentException() {
        super();
    }

    /**
     * Constructor
     * @param message the message to be thrown
     */
    public DateFieldInvalidArgumentException(String message) {
        super(message);
    }
}
