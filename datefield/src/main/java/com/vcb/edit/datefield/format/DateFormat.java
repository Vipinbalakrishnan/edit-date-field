package com.vcb.edit.datefield.format;

import com.vcb.edit.datefield.format.types.component.contract.Component;

import java.util.Map;

public interface DateFormat {
    /**
     * Returns the date format in use
     * @return String - the date format
     */
    String format();
    /**
     * Returns the input type of the field to be used for this date format
     * @return int - the input type of the field to be used for this date format
     */
    int inputType();
    /**
     * Returns length of the date format in use
     * @return int - length of the date format
     */
    int length();
    /**
     * Returns number of the date format components
     * @return int - number of the date format components
     */
    int numberOfComponents();
    /**
     * Returns components of the date format.
     * Which contains the components like
     * java.util.Calendar.DATE,
     * java.util.Calendar.MONTH,
     * java.util.Calendar.YEAR etc.
     * @return an instance of Map of type Map<Integer, ? super Index>
     */
    Map<Integer, Component> components();
    /**
     * Format the input value if any text changes are needed.
     * If formatting done, returns the formatted text. null will be returned otherwise.
     * For example
     *  If date entered is 4, it formats to 04.
     *  If date entered is 3, waits for the second letter to enter.
     *  If the month entered is 2, it formats to 02.
     *  If month entered is 1, waits for the second letter to enter.
     *  So it will check for maximum possible digit for each component to start with.
     * @param component the component
     * @param value the input value
     * @return the formatted input value or returns null.
     *          So if null is returned, assume that it is not formatted
     */
    String formatInput(Component component, String value);
}
