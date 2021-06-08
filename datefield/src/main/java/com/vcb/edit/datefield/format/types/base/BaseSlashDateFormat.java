package com.vcb.edit.datefield.format.types.base;

/**
 * Base class for the date format with slash as delimiter
 */
public class BaseSlashDateFormat extends BaseDateFormat {
    /**
     * The separator of the date format components
     */
    protected static final String separator = "/";

    /**
     * Constructor
     */
    public BaseSlashDateFormat(String format) {
        super(format);
    }
}
