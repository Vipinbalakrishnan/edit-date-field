package com.vcb.edit.datefield.format.types.component.instance;

import com.vcb.edit.datefield.format.types.component.contract.Validator;

/**
 * A class with validation for year component
 */
public class YearValidator implements Validator {
    /**
     * Validation contract
     *
     * @param value the value to validate
     * @return the value is valid or not
     */
    @Override
    public boolean isValid(String value) {
        return true;
    }
}
