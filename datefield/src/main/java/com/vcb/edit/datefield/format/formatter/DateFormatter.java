package com.vcb.edit.datefield.format.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Methods to parse or format the date.
 * Set lenient as false to not round off date components.
 * For example there is no date as 31/06/2018. If lenient set to true, this date will be parsed as
 * 01 Jul 2018 without error.
 * Default is lenient true.
 * To change the lenient value use constructor DateFormatter(boolean lenient)
 */
public class DateFormatter {
    /**
     * <code>True</code> if this formatter allows out-of-range field values during computation
     * of <code>time</code> from <code>fields[]</code>.
     */
    private boolean lenient = true;
    /**
     * Constructor
     */
    public DateFormatter() {
    }
    /**
     * Constructor
     * @param lenient tells whether the date parsing need to be lenient.
     */
    public DateFormatter(boolean lenient) {
        this.lenient = lenient;
    }
    /**
     * Returns a date format object with the format specified.
     * @param format the format on which the formatter need to create.
     * @return DateFormat - the date format.
     */
    private DateFormat getDateFormatter(String format) {
        DateFormat formatter = new SimpleDateFormat(format, Locale.US);
        formatter.setLenient(lenient);
        return formatter;
    }
    /**
     * Parse a string representation of date to the date object with the specified format.
     * @param date the string representation of the date to be parsed.
     * @param format the format with which the date is represented by 'date' parameter.
     * @return java.util.Date - the parsed date.
     * @throws ParseException
     */
    public Date parse(String date, String format) throws ParseException {
        DateFormat formatter = getDateFormatter(format);
        return formatter.parse(date);
    }
    /**
     * Format a string representation of date to the format specified with the parameter formatToConvert.
     * @param date the java.util.Date object that need to convert to string.
     * @param formatToConvert the format to which the date string to parse.
     * @return String - the formatted date
     */
    public String format(Date date, String formatToConvert) {
        DateFormat formatter = getDateFormatter(formatToConvert);
        return formatter.format(date);
    }
    /**
     * Format a string representation of date to the format specified with the parameter formatToConvert.
     * @param date the string representation of the date to be formatted
     * @param currentDateFormat the format in which the date string currently is.
     * @param formatToConvert the format to which the date string to parse.
     * @return String - the formatted date
     * @throws ParseException
     */
    public String format(String date, String currentDateFormat, String formatToConvert) throws ParseException {
        return format(parse(date, currentDateFormat), formatToConvert);
    }
}
