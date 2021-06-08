package com.vcb.edit.datefield.format.types.base;

import android.text.InputType;

import com.vcb.edit.datefield.format.DateFormat;
import com.vcb.edit.datefield.format.types.component.contract.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base class for the date format with base implementations
 */
public abstract class BaseDateFormat implements DateFormat {
    /**
     * The date format
     */
    protected String format;
//    /**
//     * Number of date components
//     */
//    protected int numberOfComponents;
    /**
     * Map that holds the date components
     * Note:-  Avoiding
     * 1. breaking out of loop once the component is finished and
     * 2. changing HashMap to LinkedHashMap
     * to handle conditions such as deleting the current text till the
     * separator of a component and adding the text.
     * Eg:- Current text is 12/31/2011. Delete till 12 or 12/31 including the separator.
     * Now add the text to the end should add a separator before adding the current input. */
    protected Map<Integer, Component> components = new LinkedHashMap<>();
//    protected Map<Integer, Component> components = new HashMap<>();

    /**
     * Constructor
     */
    public BaseDateFormat(String format) {
        this.format = format;
//        numberOfComponents = format().split(separator()).length;
    }

    /**
     * Returns the date format in use
     *
     * @return String - the date format
     */
    @Override
    public String format() {
        return format;
    }

    /**
     * Returns length of the date format in use
     *
     * @return int - length of the date format
     */
    @Override
    public int length() {
        return format().length();
    }

    /**
     * Returns number of the date format components
     *
     * @return int - number of the date format components
     */
    @Override
    public int numberOfComponents() {
        return components.size();
    }

    /**
     * Returns components of the date format.
     * Which contains the components like
     * java.util.Calendar.DATE,
     * java.util.Calendar.MONTH,
     * java.util.Calendar.YEAR etc.
     *
     * @return an instance of Map of type Map<Integer, ? super Index>
     */
    @Override
    public Map<Integer, Component> components() {
        return components;
    }

    /**
     * Returns the input type of the field to be used for this date format
     *
     * @return int - the input type of the field to be used for this date format
     */
    @Override
    public int inputType() {
        return InputType.TYPE_CLASS_NUMBER;
    }

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
    @Override
    public String formatInput(Component component, String value) {
        /** Added to handle the scenarios where a user clears the text by pressing back button till
         *  the separator and trying to add again.
         *  Eg:- Current text is 12/31/2011. Delete till 12 or 12/31 including the separator.
         *  Now add the text to the end should add a separator before adding the current input.
         *  Note:- Needed to change HashMap of components to LinkedHashMap with adding the components
         *  in the occurring order in the format. Also changed the breaking from for loop after
         *  a component is finished. */
//        if(value.length() == component.index().getEnd() + 2
//                && component.separator().charAt(0) != value.charAt(component.index().getEnd() + 1)) {
//            value = new StringBuilder(value).insert(component.index().getEnd() + 1, component.separator()).toString();
//            return value;
//        }
        /** Same for the above code but to support for separators that have more than
         * one characters like in May 12, 1999 where date has comma and space. */
        if((value.length() > component.index().getEnd() + 1 && value.length() <= component.index().getEnd() + 1 + component.separator().length())
                && !component.separator().equals(value.substring(component.index().getEnd()))) {
            value = new StringBuilder(value.substring(0, component.index().getEnd() + 1))
                    .insert(component.index().getEnd() + 1, component.separator())
                    .append(value.substring(value.length() - 1))
                    .toString();
            return value;
        }

        /** For adding 0 to the beginning if current added character is greater
         * than the max digit possible for the component.eg for date, 4 -> 04 */
        if(value.length() == component.index().getStart() + 1
                && parseInt(value.substring(component.index().getStart(), component.index().getStart() + 1)) > component.maxStartDigit()) {
            value = value.substring(0, component.index().getStart()) + "0" + value.charAt(component.index().getStart()) + component.separator();
            return value;
        }

        if(value.length() == component.index().getEnd() + 1) {
            /** For handling inputs like
             * 1. 00 :- If enters, then removes the end character and
             * 2. 33 -> date and 19 -> month :- max than the component max.
             *          If enters, then removes the end character. */
            int intValue = parseInt(value.substring(component.index().getStart(), component.index().getEnd() + 1));
            if(intValue < component.minValue()
                    || intValue > component.maxValue()) {
                value = value.substring(0, component.index().getStart() + 1);
            } else {
                /** For adding separator after each component end. */
                value += component.separator();
            }
            return value;
        }
        return null;
    }

    /**
     * Parse a string input. Returns Integer.MIN_VALUE if cannot be parsed to int
     * @param value the value to be converted to int
     * @return the int value of the input. Returns Integer.MIN_VALUE if cannot be parsed
     */
    protected int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
        }
        return  Integer.MIN_VALUE;
    }
}
