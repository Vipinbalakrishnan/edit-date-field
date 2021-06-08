package com.vcb.edit.datefield.format.types.variants;

import com.vcb.edit.datefield.format.types.component.instance.BaseIndex;
import com.vcb.edit.datefield.format.types.component.instance.DateComponent;
import com.vcb.edit.datefield.format.types.component.instance.DateValidator;
import com.vcb.edit.datefield.format.types.component.instance.MonthComponent;
import com.vcb.edit.datefield.format.types.component.instance.MonthValidator;
import com.vcb.edit.datefield.format.types.component.instance.YearComponentYYYY;
import com.vcb.edit.datefield.format.types.component.instance.YearValidator;

import java.util.Calendar;

/**
 * Class for dat format of type "January 17, 2022"
 */
public class MMMDDYYYY extends BaseMMMDD {
    /**
     * The month separator for the date format of type 'January 17, 2022'
     */
    protected static final String monthSeparator = " ";
    /**
     * The month separator for the date format of type 'January 17, 2022'
     */
    protected static final String daySeparator = ", ";

    /**
     * Constructor
     */
    public MMMDDYYYY() {
        super("MMM dd, yyyy");
        int firstIndex = format.indexOf(monthSeparator);
        int secondStartIndex = format.indexOf(",");
        int thirdStartIndex = format.lastIndexOf(" ");
        components.put(Calendar.MONTH, new MonthComponent(Calendar.MONTH, new BaseIndex(0, firstIndex - 1), monthSeparator, new MonthValidator()));
        components.put(Calendar.DATE, new DateComponent(Calendar.DATE, new BaseIndex(firstIndex + 1, secondStartIndex - 1), daySeparator, new DateValidator()));
        components.put(Calendar.YEAR, new YearComponentYYYY(Calendar.YEAR, new BaseIndex(thirdStartIndex + 1, format().length() - 1), "", new YearValidator()));
    }
}
