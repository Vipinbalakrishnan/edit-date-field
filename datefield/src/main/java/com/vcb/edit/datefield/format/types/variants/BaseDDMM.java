package com.vcb.edit.datefield.format.types.variants;

import com.vcb.edit.datefield.format.types.base.BaseDateFormat;
import com.vcb.edit.datefield.format.types.component.contract.Component;

import java.util.Calendar;

public abstract class BaseDDMM extends BaseDateFormat {

    /**
     * Constructor
     *
     * @param format the format
     */
    public BaseDDMM(String format) {
        super(format);
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
        /** For handling input like 29 or above for date and adding month as 2 or 02. */
        try {
            /** Check if single digit typed for month is 2 and date is greater than 29. */
            if(Calendar.MONTH == component.field()) {
                if(value.length() == component.index().getStart() + 1) {
                    if(2 == parseInt(value.substring(component.index().getStart(), component.index().getStart() + 1))
                            && parseInt(value.substring(components().get(Calendar.DATE).index().getStart(), components().get(Calendar.DATE).index().getEnd() + 1)) > 29) {
                        value = value.substring(0, component.index().getStart());
                        return value;
                    }
                }
                /** Check if the two digits typed for month is 2 and date is greater than 29. */
                if(value.length() == component.index().getEnd() + 1) {
                    if(2 == parseInt(value.substring(component.index().getStart(), component.index().getEnd() + 1))
                            && parseInt(value.substring(components().get(Calendar.DATE).index().getStart(), components().get(Calendar.DATE).index().getEnd() + 1)) > 29) {
                        value = value.substring(0, component.index().getStart() + 1);
                        return value;
                    }
                }
            }
        } catch(Exception ex) {
        }
        return super.formatInput(component, value);
    }
}
