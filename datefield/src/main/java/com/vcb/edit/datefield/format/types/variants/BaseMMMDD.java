package com.vcb.edit.datefield.format.types.variants;

import android.text.InputType;

import com.vcb.edit.datefield.format.types.base.BaseDateFormat;
import com.vcb.edit.datefield.format.types.component.contract.Component;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseMMMDD extends BaseDateFormat {
    /** A map for keeping the integer values of months. */
    private Map<String, Integer> monthIndexMap;

    /**
     * Constructor
     *
     * @param format the format
     */
    public BaseMMMDD(String format) {
        super(format);
        initialize();
        fillMonthIndexMap();
    }

    /**
     * Initializes the variables
     */
    private void initialize() {
        monthIndexMap = new HashMap<>(12);
    }

    /**
     * Returns the month index map
     * @return the month index map
     */
    public Map<String, Integer> getMonthMap() {
        return monthIndexMap;
    }

    /**
     * Sets the month index map
     * @param monthIndexMap - the month index map
     */
    public void setMonthMap(Map<String, Integer> monthIndexMap) {
        this.monthIndexMap = monthIndexMap;
    }

    /**
     * Fills the month index map with month short names and its numeric values
     */
    private void fillMonthIndexMap() {
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        String [] monthsShort = symbols.getShortMonths();
        int index = 0;
        for(String monthShort : monthsShort) {
            getMonthMap().put(monthShort, ++index);
        }
    }

    /**
     * Returns the input type of the field to be used for this date format
     *
     * @return int - the input type of the field to be used for this date format
     */
    @Override
    public int inputType() {
        return InputType.TYPE_DATETIME_VARIATION_DATE;
    }

    private String getFormattedMonth(String month) {
        if(month == null || month.isEmpty()) return month;
        return month.substring(0, 1).toUpperCase() + month.substring(1);
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
        try {
            if(Calendar.MONTH != component.field()) {
                //"-?\\d+" - for including negative
                /** If we are entering a string value for day or year we will trim that. */
                if(!value.substring(component.index().getStart()).matches("\\d+")) {
                    value = value.substring(0, component.index().getStart());
                    return value;
                }
            }
            if(Calendar.DATE == component.field()) {
                Integer month = monthIndexMap.get(value.substring(components().get(Calendar.MONTH).index().getStart(), components().get(Calendar.MONTH).index().getEnd() + 1));
                if(value.length() == component.index().getStart() + 1) {
                    /** Check if the single letter typed is greater than 2. Because it should limit from 3 to enter. */
                    if(parseInt(value.substring(component.index().getStart(), component.index().getStart() + 1)) > 2
                            && null != month && month == 2) {
                        value = value.substring(0, component.index().getStart());
                        return value;
                    }
                }
                /** Check if the two digits typed for date is greater than 29 and month is February. */
                if(value.length() == component.index().getEnd() + 1) {
                    if(parseInt(value.substring(component.index().getStart(), component.index().getStart() + 1)) > 29
                            && null != month && month == 2) {
                        value = value.substring(0, component.index().getStart() + 1);
                        return value;
                    }
                }
            }
            /** Added to handle the scenarios where a user clears the text by pressing back button till
             *  the separator and trying to add again.
             *  Eg:- Current text is 12/31/2011. Delete till 12 or 12/31 including the separator.
             *  Now add the text to the end should add a separator before adding the current input.
             *  Note:- Needed to change HashMap of components to LinkedHashMap with adding the components
             *  in the occurring order in the format. Also changed the breaking from for loop after
             *  a component is finished. */
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
            if(Calendar.MONTH != component.field() && value.length() == component.index().getStart() + 1
                    && parseInt(value.substring(component.index().getStart(), component.index().getStart() + 1)) > component.maxStartDigit()) {
                value = value.substring(0, component.index().getStart()) + "0" + value.charAt(component.index().getStart()) + component.separator();
                return value;
            }

            if(value.length() == component.index().getEnd() + 1) {
                /** For handling inputs like
                 * 1. 00 :- If enters, then removes the end character and
                 * 2. 33 -> date and 19 -> month :- max than the component max.
                 *          If enters, then removes the end character. */
                String componentValue = value.substring(component.index().getStart(), component.index().getEnd() + 1);
                Integer intValue;
                /** If month is not valid as in the month map, we will clear the text field. */
                if(Calendar.MONTH == component.field()) {
//                    componentValue = getFormattedMonth(componentValue);
                    if(!getMonthMap().containsKey(componentValue)) {
                        value = "";
                        return value;
                    } else {
                        intValue = getMonthMap().get(componentValue);
                    }
                } else {
                    intValue = parseInt(value.substring(component.index().getStart(), component.index().getEnd() + 1));
                }
                if(null != intValue && (intValue < component.minValue()
                        || intValue > component.maxValue())) {
                    value = value.substring(0, component.index().getStart() + 1);
                } else {
                    /** For adding separator after each component end. */
                    value += component.separator();
                }
                return value;
            }
        } catch(Exception ex) {
        }
        return null;
    }
}
