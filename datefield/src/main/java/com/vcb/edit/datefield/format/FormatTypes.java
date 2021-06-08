package com.vcb.edit.datefield.format;

import com.vcb.edit.datefield.format.types.variants.DDMMYYYY_Hyphen;
import com.vcb.edit.datefield.format.types.variants.DDMMYYYY_Slash;
import com.vcb.edit.datefield.format.types.variants.DDMMYY_Hyphen;
import com.vcb.edit.datefield.format.types.variants.DDMMYY_Slash;
import com.vcb.edit.datefield.format.types.variants.MMMDDYYYY;
import com.vcb.edit.datefield.format.types.variants.YYMMDD_Hyphen;
import com.vcb.edit.datefield.format.types.variants.YYMMDD_Slash;
import com.vcb.edit.datefield.format.types.variants.YYYYMMDD_Hyphen;
import com.vcb.edit.datefield.format.types.variants.YYYYMMDD_Slash;

/**
 * Class that holds the currently supported date formats by the view out of the box
 */
public class FormatTypes {
    public static DateFormat DD_MM_YY_SLASH = new DDMMYY_Slash();
    public static DateFormat DD_MM_YYYY_SLASH = new DDMMYYYY_Slash();
    public static DateFormat YY_MM_DD_SLASH = new YYMMDD_Slash();
    public static DateFormat YYYY_MM_DD_SLASH = new YYYYMMDD_Slash();
    public static DateFormat DD_MM_YY_HYPHEN = new DDMMYY_Hyphen();
    public static DateFormat DD_MM_YYYY_HYPHEN = new DDMMYYYY_Hyphen();
    public static DateFormat YY_MM_DD_HYPHEN = new YYMMDD_Hyphen();
    public static DateFormat YYYY_MM_DD_HYPHEN = new YYYYMMDD_Hyphen();
    public static DateFormat MMMM_DD_YYYY = new MMMDDYYYY();
}
