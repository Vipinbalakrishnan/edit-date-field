package com.vcb.edit.datefield.format.types.variants;

import com.vcb.edit.datefield.format.types.component.instance.BaseIndex;
import com.vcb.edit.datefield.format.types.component.instance.DateComponent;
import com.vcb.edit.datefield.format.types.component.instance.DateValidator;
import com.vcb.edit.datefield.format.types.component.instance.MonthComponent;
import com.vcb.edit.datefield.format.types.component.instance.MonthValidator;
import com.vcb.edit.datefield.format.types.component.instance.YearComponentYY;
import com.vcb.edit.datefield.format.types.component.instance.YearValidator;

import java.util.Calendar;

/**
 * Class for dat format of type "YY/MM/dd"
 */
public class YYMMDD_Slash extends BaseMMDD {
    /**
     * The separator of the date format components
     */
    protected static final String separator = "/";

    /**
     * Constructor
     */
    public YYMMDD_Slash() {
        super("yy" + separator + "MM" + separator + "dd");
        int firstIndex = format.indexOf(separator);
        int secondIndex = format.lastIndexOf(separator);
        components.put(Calendar.YEAR, new YearComponentYY(Calendar.YEAR, new BaseIndex(0, firstIndex - 1), separator, new YearValidator()));
        components.put(Calendar.MONTH, new MonthComponent(Calendar.MONTH, new BaseIndex(firstIndex + 1, secondIndex - 1), separator, new MonthValidator()));
        components.put(Calendar.DATE, new DateComponent(Calendar.DATE, new BaseIndex(secondIndex + 1, format().length() - 1), "", new DateValidator()));
    }
}
