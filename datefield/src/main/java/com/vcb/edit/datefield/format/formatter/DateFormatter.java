package com.vcb.edit.datefield.format.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Methods to parse or format the date.
 */
public class DateFormatter {
    /**
     * Parse a string representation of date to the date object with the specified format.
     * @param date the string representation of the date to be parsed.
     * @param format the format with which the date is represented by 'date' parameter.
     * @return java.util.Date - the parsed date.
     * @throws ParseException
     */
    public Date parse(String date, String format) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(format, Locale.US);
        return formatter.parse(date);
    }
    /**
     * Format a string representation of date to the format specified with the parameter formatToConvert.
     * @param date the java.util.Date object that need to convert to string.
     * @param formatToConvert the format to which the date string to parse.
     * @return String - the formatted date
     */
    public String format(Date date, String formatToConvert) {
        DateFormat formatter = new SimpleDateFormat(formatToConvert, Locale.US);
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
