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
 * Class for dat format of type "YYYY/MM/dd"
 */
public class YYYYMMDD_Slash extends BaseMMDD {
    /**
     * The separator of the date format components
     */
    protected static final String separator = "/";
    /**
     * Constructor
     */
    public YYYYMMDD_Slash() {
        super("yyyy" + separator + "MM" + separator + "dd");
        int firstIndex = format.indexOf(separator);
        int secondIndex = format.lastIndexOf(separator);
        components.put(Calendar.YEAR, new YearComponentYYYY(Calendar.YEAR, new BaseIndex(0, firstIndex - 1), separator, new YearValidator()));
        components.put(Calendar.MONTH, new MonthComponent(Calendar.MONTH, new BaseIndex(firstIndex + 1, secondIndex - 1), separator, new MonthValidator()));
        components.put(Calendar.DATE, new DateComponent(Calendar.DATE, new BaseIndex(secondIndex + 1, format().length() - 1), "", new DateValidator()));
    }
}
