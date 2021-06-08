package com.vcb.edit.datefield.format.types.component.contract;

/**
 * Contract for validating the components
 */
public interface Validator {
    /**
     * Validation contract
     * @param value the value to validate
     * @return the value is valid or not
     */
    boolean isValid(String value);
}
