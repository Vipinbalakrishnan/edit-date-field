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
 * Class for dat format of type "dd/MM/YY"
 */
public class DDMMYY_Slash extends BaseDDMM {
    /**
     * The separator of the date format components
     */
    protected static final String separator = "/";

    /**
     * Constructor
     */
    public DDMMYY_Slash() {
        super("dd" + separator + "MM" + separator + "yy");
        int firstIndex = format.indexOf(separator);
        int secondIndex = format.lastIndexOf(separator);
        components.put(Calendar.DATE, new DateComponent(Calendar.DATE, new BaseIndex(0, firstIndex - 1), separator, new DateValidator()));
        components.put(Calendar.MONTH, new MonthComponent(Calendar.MONTH, new BaseIndex(firstIndex + 1, secondIndex - 1), separator, new MonthValidator()));
        components.put(Calendar.YEAR, new YearComponentYY(Calendar.YEAR, new BaseIndex(secondIndex + 1, format().length() - 1), "", new YearValidator()));
    }
}
