package com.vcb.edit.datefield.format.types.component.contract;

/**
 * Contract for getting the components of the format
 */
public interface Component {
    /**
     * Returns date field of the component. eg:- Calendar.DATE, Calendar.MONTH, Calendar.YEAR etc
     * @return int - date field of the component
     */
    int field();
    /**
     * Returns the index of the component
     * @return the index
     */
    Index index();
    /**
     * Returns length of the component
     * @return int - length of the component
     */
    int length();
    /**
     * Returns the validator
     * @return the validator of the component
     */
    Validator validator();
    /**
     * Returns minimum value of the component
     * @return int - minimum value of the component
     */
    int minValue();
    /**
     * Returns maximum value of the component
     * @return int - maximum value of the component
     */
    int maxValue();
    /**
     * Returns the maximum possible value of the first digit of the component
     * @return int - the maximum possible value of the first digit of the component
     */
    int maxStartDigit();
    /**
     * Returns the separator.
     * It will return "/" for dd/MM/yy and "-" for dd-MM-yyyy
     * @return string - separators
     */
    String separator();
}
