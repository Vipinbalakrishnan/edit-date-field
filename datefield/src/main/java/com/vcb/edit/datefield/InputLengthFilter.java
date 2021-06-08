package com.vcb.edit.datefield;

import android.text.InputFilter;

public class InputLengthFilter extends InputFilter.LengthFilter {
    /**
     * The max length to apply
     */
    private int maxLength;

    /**
     * Constructor
     * @param max the max length to apply
     */
    public InputLengthFilter(int max) {
        super(max);
        this.maxLength = max;
    }

    /**
     * Returns the max length
     * @return the max length
     */
    public int getMaxLength() {
        return maxLength;
    }
}
