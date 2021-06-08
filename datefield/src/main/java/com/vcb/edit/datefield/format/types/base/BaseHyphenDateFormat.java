package com.vcb.edit.datefield.format.types.base;

/**
 * Base class for the date format with hyphen as delimiter
 */
public class BaseHyphenDateFormat extends BaseDateFormat {
    /**
     * The separator of the date format components
     */
    protected static final String separator = "-";

    /**
     * Constructor
     */
    public BaseHyphenDateFormat(String format) {
        super(format);
    }
}
