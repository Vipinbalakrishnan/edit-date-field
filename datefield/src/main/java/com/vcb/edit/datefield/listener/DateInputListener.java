package com.vcb.edit.datefield.listener;

import com.vcb.edit.datefield.constants.StatusType;

/**
 * A listener class to notify the date input changes.
 */
public interface DateInputListener {
    /**
     * Triggers when date is set
     * @param date - the input date string which is entered by the user
     * @param statusType - the status type of the input.
     */
    void onDateSet(String date, StatusType statusType);
}
