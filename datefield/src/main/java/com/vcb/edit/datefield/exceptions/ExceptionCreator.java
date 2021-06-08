package com.vcb.edit.datefield.exceptions;

/**
 * Creates and returns the exceptions for {@code DateField} class
 */
public class ExceptionCreator {

    /**
     * Returns the general exception for {@code DateField} class
     * @param message the message to be shown
     * @return the general DateFieldException
     */
    public static DateFieldException getGeneralException(String message) {
        return new DateFieldException(message);
    }

    /**
     * Returns the invalid argument exception for {@code DateField} class
     * @param message the message to be shown
     * @return the general DateFieldInvalidArgumentException
     */
    public static DateFieldInvalidArgumentException getInvalidArgumentException(String message) {
        return new DateFieldInvalidArgumentException(message);
    }
}
