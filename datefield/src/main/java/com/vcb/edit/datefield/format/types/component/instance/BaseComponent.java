package com.vcb.edit.datefield.format.types.component.instance;

import com.vcb.edit.datefield.format.types.component.contract.Component;
import com.vcb.edit.datefield.format.types.component.contract.Index;
import com.vcb.edit.datefield.format.types.component.contract.Validator;

public class BaseComponent implements Component {
    /**
     * The date field of the component. eg:- Calendar.DATE, Calendar.MONTH, Calendar.YEAR etc
     */
    protected int field;
    /**
     * Index object of the component of the date format
     */
    protected Index index;
    /**
     * The length of the component
     */
    protected int length;
    /**
     * Validator object of the component of the date format
     */
    protected Validator validator;
    /**
     * The separator of this component
     */
    protected String separator;

    /**
     * Constructor
     * @param field the date field of the component. eg:Calendar.DATE, Calendar.MONTH, Calendar.YEAR
     * @param index the index object of the component
     * @param separator the separator of this component
     * @param validator the validator object of the component
     */
    public BaseComponent(int field, Index index, String separator, Validator validator) {
        this.field = field;
        this.index = index;
        this.validator = validator;
        this.separator = separator;
        length = index().getEnd() - index().getStart() + 1;
    }

    /**
     * Returns date field of the component. eg:- Calendar.DATE, Calendar.MONTH, Calendar.YEAR etc
     * @return int - date field of the component
     */
    @Override
    public int field() {
        return field;
    }

    /**
     * Returns the index of the component
     *
     * @return the index
     */
    @Override
    public Index index() {
        return index;
    }

    /**
     * Returns length of the component
     * @return int - length of the component
     */
    @Override
    public int length() {
        return length;
    }

    /**
     * Returns the validator
     *
     * @return the validator of the component
     */
    @Override
    public Validator validator() {
        return validator;
    }

    /**
     * Returns minimum value of the component
     *
     * @return int - minimum value of the component
     */
    @Override
    public int minValue() {
        return 0;
    }

    /**
     * Returns maximum value of the component
     *
     * @return int - maximum value of the component
     */
    @Override
    public int maxValue() {
        return 0;
    }

    /**
     * Returns the maximum possible value of the first digit of the component
     *
     * @return int - the maximum possible value of the first digit of the component
     */
    @Override
    public int maxStartDigit() {
        return 0;
    }

    /**
     * Returns the separator.
     * It will return "/" for dd/MM/yy and "-" for dd-MM-yyyy
     *
     * @return string - separators
     */
    @Override
    public String separator() {
        return separator;
    }
}
