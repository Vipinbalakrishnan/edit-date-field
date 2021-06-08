package com.vcb.edit.datefield.format.types.component.instance;

import com.vcb.edit.datefield.format.types.component.contract.Index;

/**
 * A class that provides a base implementation of the index of a component
 */
public class BaseIndex implements Index {
    /**
     * Start index
     */
    private final int start;
    /**
     * End index
     */
    private final int end;
    /**
     * Constructor
     * @param start the start index
     * @param end the end index
     */
    public BaseIndex(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start index
     *
     * @return int
     */
    @Override
    public int getStart() {
        return start;
    }

    /**
     * Returns the end index
     *
     * @return int
     */
    @Override
    public int getEnd() {
        return end;
    }
}
