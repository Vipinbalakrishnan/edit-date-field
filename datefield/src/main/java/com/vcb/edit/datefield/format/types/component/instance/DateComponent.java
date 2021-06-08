package com.vcb.edit.datefield.format.types.component.instance;

import com.vcb.edit.datefield.format.types.component.contract.Index;
import com.vcb.edit.datefield.format.types.component.contract.Validator;

public class DateComponent extends BaseComponent {
    /**
     * Constructor
     * @param field the date field of the component. eg:Calendar.DATE, Calendar.MONTH, Calendar.YEAR
     * @param index the index object of the component
     * @param separator the separator of this component
     * @param validator the validator object of the component
     */
    public DateComponent(int field, Index index, String separator, Validator validator) {
        super(field, index, separator, validator);
    }

    /**
     * Returns minimum value of the component
     *
     * @return int - minimum value of the component
     */
    @Override
    public int minValue() {
        return 1;
    }

    /**
     * Returns maximum value of the component
     *
     * @return int - maximum value of the component
     */
    @Override
    public int maxValue() {
        return 31;
    }

    /**
     * Returns the maximum possible value of the first digit of the component
     *
     * @return int - the maximum possible value of the first digit of the component
     */
    @Override
    public int maxStartDigit() {
        return 3;
    }
}
