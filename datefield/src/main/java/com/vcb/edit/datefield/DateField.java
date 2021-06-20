package com.vcb.edit.datefield;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.Log;

import com.vcb.edit.datefield.base.MenuDisabledEditText;
import com.vcb.edit.datefield.constants.StatusType;
import com.vcb.edit.datefield.exceptions.DateFieldException;
import com.vcb.edit.datefield.exceptions.DateFieldInvalidArgumentException;
import com.vcb.edit.datefield.format.DateFormat;
import com.vcb.edit.datefield.format.FormatTypes;
import com.vcb.edit.datefield.format.formatter.DateFormatter;
import com.vcb.edit.datefield.format.types.component.contract.Component;
import com.vcb.edit.datefield.format.types.component.contract.Index;
import com.vcb.edit.datefield.listener.DateFieldKeyListener;
import com.vcb.edit.datefield.listener.DateInputListener;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Custom class on EditText.java for date input.
 * This is an abstract implementation and can handle different date formats with the given
 * interfaces like DateFormat.java, Component.java, Index.java, Validator.java.
 * Please find the different implementations for the interfaces mentioned in the packages.
 * Currently this will support the following date formats
 * dd/MM/yy
 * dd/MM/yyyy
 * yy/MM/dd
 * yyyy/MM/dd
 * dd-MM-yy
 * dd-MM-yyyy
 * yy-MM-dd
 * yyyy-MM-dd
 * MMM dd, yyyy - only basic implementation
 * If you want DateField.java to work with a different date format, then implement your own classes
 * for DateFormat.java, Component.java, Index.java, Validator.java and provide to DateFormat.java
 */
public class DateField extends MenuDisabledEditText {
    /**
     * Class that holds the properties of the edit mode of the view.
     * This is used to set the text from code without triggering text watcher methods.
     */
    private enum TextEditMode {
        DEFAULT,
        CODE,
        USER
    }
    /**
     * Class that holds the validation mode of the view.
     * This is used to notify the listeners about the input validity.
     * Modes are
     * ON_CALL              - When we call validate method on the view
     * ON_INPUT             - While making the input - Like on the text change
     * ON_FINISHED_INPUT    - Will notify listeners after the input is finished
     */
    private enum ValidationMode {
        ON_CALL,
        ON_INPUT,
        ON_FINISHED_INPUT
    }
    /** The invalid int value. */
    private static final int INVALID_INT = -1;
    /** Default style attribute. */
    private static final int VOID_DEF_STYLE_ATTR = -11;
    /** Number of maximum lines supported for the view. */
    private static final int MAX_LINE = 1;
    /** The current date format of the view. */
    private DateFormat dateFormat;
    /** The text watcher for handling the input. */
    private TextWatcher textWatcher;
    /** Boolean that handles visibility of hint. */
    private boolean isHintEnabled;
    /** The current text edit mode of the view.
     * This is used to set the text from code without triggering text watcher methods. */
    private TextEditMode textEditMode = TextEditMode.DEFAULT;
    /** The current validation mode of the view.
     * This is used to validate and notify the date set listeners about the validity of the date. */
    private ValidationMode validationMode = ValidationMode.ON_FINISHED_INPUT;
    /** The minimum date of the date range. */
    private Date minDate;
    /** The maximum date of the date range. */
    private Date maxDate;
    /** List for listeners. */
    private List<DateInputListener> listeners;

    /**
     * Constructor
     * @param context the context
     */
    public DateField(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    /**
     * Constructor
     * @param context the context
     * @param attrs the attribute set
     */
    public DateField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    /**
     * Constructor
     * @param context the context
     * @param attrs the attribute set
     * @param defStyleAttr the styling attributes
     */
    public DateField(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    /**
     * Initializes the properties of the view
     * @param context the context
     */
    private void initialize(Context context) {
        initialize(context, null, VOID_DEF_STYLE_ATTR);
    }

    /**
     * Initializes the properties of the view
     * @param context the context
     * @param attrs the attribute set
     */
    private void initialize(Context context, AttributeSet attrs) {
        initialize(context, attrs, VOID_DEF_STYLE_ATTR);
    }

    /**
     * Initializes the properties of the view
     * @param context the context
     * @param attrs the attribute set
     * @param defStyleAttr the styling attributes
     */
    private void initialize(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        setDefaultHintEnabled(false);
        setDateFormat(getDefaultDateFormat());
        if(null != attrs) {
            readViewAttributes(attrs);
        }
        if(VOID_DEF_STYLE_ATTR != defStyleAttr) {
            getStyle(defStyleAttr);
        }
        addTextChangedListener(getTextWatcher());
        setMaxLines(MAX_LINE);
        setMaxLength();
        setViewHint();
        setViewInputType();
        setViewKeyListener();
    }

    /**
     * Reads the attributes set from the given attributes
     * @param attrs the attributes set to the view
     */
    private void readViewAttributes(@NonNull AttributeSet attrs) {
        try {
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads and sets the styling attribute
     * @param defStyleAttr the styling attributes
     */
    private void getStyle(int defStyleAttr) {
        try {
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        super.removeTextChangedListener(watcher);
    }

    @Override
    public void setMaxLines(int maxLines) {
        if(maxLines > MAX_LINE) {
            throwInvalidArgumentExceptionWithMessage("Max lines cannot be greater than " + MAX_LINE);
            return;
        }
        super.setMaxLines(maxLines);
    }

    /**
     * Boolean that decides whether to show hint
     * @return the boolean value
     */
    public boolean isDefaultHintEnabled() {
        return isHintEnabled;
    }

    /**
     * Sets the boolean that decides whether to show hint
     * @param hintEnabled - the boolean value
     */
    public void setDefaultHintEnabled(boolean hintEnabled) {
        isHintEnabled = hintEnabled;
    }

    /**
     * Sets the hint of the view
     */
    private void setViewHint() {
        if(isDefaultHintEnabled()) {
            setHint(getDateFormat().format().toLowerCase(Locale.US));
        }
    }

    /**
     * Returns the length of the date format that is currently in use
     * @return the length of current selected date format
     */
    private int getDateFormatLength() {
        return getDateFormat().length();
    }

    /**
     * Sets teh max length of the view. This should be in accordance with the current date format in use.
     * So call this after setting the attributes from initialization to override the max length setting from XML.
     */
    private void setMaxLength() {
        setMaxLength(getDateFormatLength());
    }

    /**
     * Sets teh max length of the view. This should be in accordance with the current date format in use.
     * So call this after setting the attributes from initialization to override the max length setting from XML.
     * @param maxLength the max length to apply to the field
     */
    private void setMaxLength(int maxLength) {
        if(getDateFormatLength() != maxLength) {
            throwInvalidArgumentExceptionWithMessage(
                    "The maxLength should be same as the length of the current " +
                            "dateFormat of the DateField.java. Which is " + getDateFormatLength());
            return;
        }
        setFilters(new InputFilter[] { new InputLengthFilter(maxLength) });
    }

//    @Override
//    public void setFilters(InputFilter[] filters) {
//        if(null != filters) {
//            for(InputFilter filter : filters) {
//                /** Checks whether the length filter is applied from the DateField.java code itself
//                 * by checking if it is an instance of InputLengthFilter class. Throws if not. */
//                if(filter instanceof InputFilter.LengthFilter && !(filter instanceof InputLengthFilter)) {
//                    throwInvalidArgumentExceptionWithMessage(
//                            "The maxLength property/ InputFilter.LengthFilter " +
//                                    "should not be applied to DateField.java");
//                    return;
//
//                }
//            }
//        }
//        super.setFilters(filters);
//    }

    @Override
    public void setFilters(InputFilter[] filters) {
        try {
            List<InputFilter> filteredList = new ArrayList<>();
            if(null != filters) {
                for(InputFilter filter : filters) {
                    /** Checks whether the length filter applied is not equal to length of current date format.
                     * If the date format is null or empty, the filter will permitted to set. */
                    if(filter instanceof InputFilter.LengthFilter
                            && !(filter instanceof InputLengthFilter)
                            && null != getDateFormat()
                            && null != getDateFormat().format()) {
                        if(getDateFormatLength() != getMaxLengthOfFilter((InputFilter.LengthFilter) filter)) {
                            /** Not setting length filters other than the valid case. */
                            System.out.println("Omitting filter -> " + filter.toString() + "setting the max length or min length other than the permitted value.");
                            continue;
                        }
                    }
                    filteredList.add(filter);
//                    if(filter instanceof InputFilter.LengthFilter && !(filter instanceof InputLengthFilter)) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            if(null != getDateFormat() && null != getDateFormat().format() && getDateFormatLength() != ((InputFilter.LengthFilter) filter).getMax()) {
//                                /** Not setting length filters other than the valid class. */
//                                System.out.println("Omitting filter -> " + filter.toString() + "setting the max length or min length as it is prohibited from setting out side the library.");
//                                continue;
//                            }
//                        }
//                    }
//                    filteredList.add(filter);
                }
            }
            if(!filteredList.isEmpty()) {
                filters = filteredList.toArray(new InputFilter[0]);
            }
            super.setFilters(filters);
        } catch (Exception ex) {
            super.setFilters(filters);
        }
    }

    /**
     * Returns the max length of the filter of type InputFilter.LengthFilter
     * @param filter the instance of InputFilter.LengthFilter
     * @return the max length or INVALID_INT
     */
    private int getMaxLengthOfFilter(InputFilter.LengthFilter filter) {
        int maxLength = INVALID_INT;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                maxLength = filter.getMax();
            } else {
                /** Using reflection to access the value of private variable mMax */
                Field field = filter.getClass().getDeclaredField("mMax");
                field.setAccessible(true);
                maxLength = (int) field.get(filter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxLength;
    }

    /**
     * Sets the input type of the view
     */
    private void setViewInputType() {
        setInputType(getDateFormat().inputType());
    }

    @Override
    public void setInputType(int type) {
        /** Disabling setting the input type other than that of currently selected date format. */
        if(type == getDateFormat().inputType()) {
            super.setInputType(type);
        }
    }

    /**
     * Sets the key listener of the view
     */
    private void setViewKeyListener() {
        /** Need to set based on different format. If we provide formats like Jun, 13 2021. */
        setKeyListener(DateFieldKeyListener.getInstance("0123456789"));
    }

    @Override
    public void setKeyListener(KeyListener input) {
        /** Avoiding other key listeners try to set from code. Only allows this package class. */
        if(input instanceof DateFieldKeyListener) {
            super.setKeyListener(input);
        }
    }

//    @Override
//    public void onEditorAction(int actionCode) {
//        super.onEditorAction(actionCode);
//        textEditMode = TextEditMode.USER;
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        textEditMode = TextEditMode.USER;
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * Return the listeners
     * @return the list of listeners
     */
    private List<DateInputListener> getListeners() {
        if(null == listeners) {
            listeners = new ArrayList<>();
        }
        return listeners;
    }

    /**
     * Add listener for the input
     * @return the listener to get changes
     */
    public void addInputListener(DateInputListener listener) {
        if(null != listener && !getListeners().contains(listener)) {
            getListeners().add(listener);
        }
    }

    /**
     * Remove listener from the current list of listeners
     * @return the listener to remove from the listeners
     */
    public void removeInputListener(DateInputListener listener) {
        if(null != listener && getListeners().contains(listener)) {
            getListeners().remove(listener);
        }
    }

    /**
     * Returns the min date of the view
     * @return  instance of java.util.Date - the min date of the view
     */
    public Date getMinDate() {
        return minDate;
    }

    /**
     * Returns the max date of the view
     * @return instance of java.util.Date - the max date of the view
     */
    public Date getMaxDate() {
        return maxDate;
    }

    /**
     * Sets the minimum date for the selectable date range
     * View state will be invalidated due to change in the range.
     * @param date the minimum date of the view
     */
    public void setMinDate(Date date) {
        this.minDate = date;
        invalidateState();
    }

    /**
     * Sets the maximum date for the selectable date range
     * View state will be invalidated due to change in the range.
     * @param date the maximum date of the view
     */
    public void setMaxDate(Date date) {
        this.maxDate = date;
        invalidateState();
    }

    /**
     * Sets the minimum date for the selectable date range
     * View state will be invalidated due to change in the range.
     * @param calendar the minimum date of the view
     */
    public void setMinDate(Calendar calendar) {
        if(null == calendar) {
            setMinDate((Date) null);
            return;
        }
        setMinDate(calendar.getTime());
    }

    /**
     * Sets the maximum date for the selectable date range
     * View state will be invalidated due to change in the range.
     * @param calendar the maximum date of the view
     */
    public void setMaxDate(Calendar calendar) {
        if(null == calendar) {
            setMaxDate((Date) null);
            return;
        }
        setMaxDate(calendar.getTime());
    }

    /**
     * Sets the minimum date for the selectable date range
     * View state will be invalidated due to change in the range.
     * @param milliseconds the minimum date of the view
     */
    public void setMinDate(long milliseconds) {
        if(milliseconds <= 0) {
            setMinDate((Date) null);
            return;
        }
        setMinDate(new Date(milliseconds));
    }

    /**
     * Sets the maximum date for the selectable date range
     * View state will be invalidated due to change in the range.
     * @param milliseconds the maximum date of the view
     */
    public void setMaxDate(long milliseconds) {
        if(milliseconds <= 0) {
            setMaxDate((Date) null);
            return;
        }
        setMaxDate(new Date(milliseconds));
    }

    /**
     * Sets the minimum date for the selectable date range.
     * View state will be invalidated due to change in the range.
     * @param date the minimum date of the view
     */
    public void setMinDate(String date) {
        try {
            if(null == date || date.isEmpty()) {
                setMinDate((Date) null);
                return;
            }
            setMinDate(parse(date));
        } catch (ParseException e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Please provide a valid string representation of the format "
                            + getDateFormat().format() + ". Current provided value is " + date);
        }
    }

    /**
     * Sets the maximum date for the selectable date range.
     * View state will be invalidated due to change in the range.
     * @param date the maximum date of the view
     */
    public void setMaxDate(String date) {
        try {
            if(null == date || date.isEmpty()) {
                setMaxDate((Date) null);
                return;
            }
            setMaxDate(parse(date));
        } catch (ParseException e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Please provide a valid string representation of the format "
                            + getDateFormat().format() + ". Current provided value is " + date);
        }
    }

    /**
     * Sets the minimum date for the selectable date range.
     * View state will be invalidated due to change in the range.
     * @param date the minimum date of the view
     * @param format the format in which the date string is given
     */
    public void setMinDate(String date, @NonNull String format) {
        try {
            setMinDate(parse(date, format));
        } catch (ParseException e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Please provide a valid string representation of the format "
                            + format + ". Current provided value is " + date);
        } catch (Exception e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Please provide a valid string representation of the format "
                            + format + ". Current provided value is " + date);
        }
    }

    /**
     * Sets the maximum date for the selectable date range.
     * View state will be invalidated due to change in the range.
     * @param date the maximum date of the view
     * @param format the format in which the date string is given
     */
    public void setMaxDate(String date, @NonNull String format) {
        try {
            setMaxDate(parse(date, format));
        } catch (ParseException e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Please provide a valid string representation of the format "
                            + format + ". Current provided value is " + date);
        } catch (Exception e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Please provide a valid string representation of the format "
                            + format + ". Current provided value is " + date);
        }
    }

    /**
     * Updates the minimum date without invalidating the view state
     * @param date the minimum date of the view
     */
    public void updateMinDate(Date date) {
        this.minDate = date;
    }

    /**
     * Updates the maximum date without invalidating the view state
     * @param date the maximum date of the view
     */
    public void updateMaxDate(Date date) {
        this.maxDate = date;
    }

    /**
     * Updates the minimum date for the selectable date range without invalidating the view state
     * @param calendar the minimum date of the view
     */
    public void updateMinDate(Calendar calendar) {
        if(null == calendar) {
            updateMinDate((Date) null);
            return;
        }
        updateMinDate(calendar.getTime());
    }

    /**
     * Updates the maximum date for the selectable date range without invalidating the view state
     * @param calendar the maximum date of the view
     */
    public void updateMaxDate(Calendar calendar) {
        if(null == calendar) {
            updateMaxDate((Date) null);
            return;
        }
        updateMaxDate(calendar.getTime());
    }

    /**
     * Parses a given date string to date based on the current date format of the view.
     * @param date the string representation of the date to be parsed
     * @return java.util.Date - the parsed date
     * @throws ParseException
     */
    public Date parse(String date) throws ParseException {
        return parse(date, getDateFormat().format());
    }

    /**
     * Parses a given date string to date based on the current date format of the view.
     * @param date the string representation of the date to be parsed
     * @param format the format in which the date string is given.
     * @return java.util.Date - the parsed date
     * @throws ParseException
     */
    public Date parse(String date, @NonNull String format) throws ParseException {
        return new DateFormatter().parse(date, format);
    }

    /**
     * Formats a given java.util.Date object to the format specified.
     * @param date java.util.Date object to format.
     * @return dateFormat - the format in which the returned string needs
     * @return String - the formatted date
     */
    public String format(Date date, String dateFormat) {
        return new DateFormatter().format(date, dateFormat);
    }

    /**
     * Formats a given string representation of the date to the format of this field.
     * @param date the date to format in string representation.
     * @param currentDateFormat the format in which the date string currently is.
     * @return String - the formatted date string with this field's date format
     */
    public String format(String date, @NonNull String currentDateFormat) throws ParseException {
        return new DateFormatter().format(date, currentDateFormat, getDateFormat().format());
    }

    /**
     * Formats a given string representation of the date to the format specified.
     * @param date the date to format in string representation.
     * @param currentDateFormat the format in which the date string currently is.
     * @param formatToConvert the format in which the returned formatted string needs.
     * @return String - the formatted date string
     */
    public String format(String date, @NonNull String currentDateFormat, @NonNull String formatToConvert) throws ParseException {
        return new DateFormatter().format(date, currentDateFormat, formatToConvert);
    }

    /**
     * Formats the current value in the field to the format given.
     * @return dateFormat - the format in which the returned formatted string needs.
     * @return String - the formatted date string.
     */
    public String formatFieldValue(String dateFormat) throws ParseException {
        return null == getText() || getText().toString().isEmpty()
                ? "" : format(getText().toString().trim(), getDateFormat().format(), dateFormat);
    }

    /**
     * Returns the default date format - DD/MM/YYYY
     * @return the date format
     */
    private DateFormat getDefaultDateFormat() {
//        return FormatTypes.DD_MM_YY_SLASH;
        return FormatTypes.DD_MM_YYYY_SLASH;
//        return FormatTypes.YY_MM_DD_SLASH;
//        return FormatTypes.YYYY_MM_DD_SLASH;
//        return FormatTypes.DD_MM_YY_HYPHEN;
//        return FormatTypes.DD_MM_YYYY_HYPHEN;
//        return FormatTypes.YY_MM_DD_HYPHEN;
//        return FormatTypes.YYYY_MM_DD_HYPHEN;
//        return FormatTypes.MMMM_DD_YYYY;
    }

    /**
     * Returns the current selected date format
     * @return the date format which is now in use
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * Sets the date format
     * @param dateFormat the date format to use
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        invalidateState();
    }

//    /**
//     * Returns the separator for current selected date format
//     * @return the separator for format which is now in use
//     */
//    public String getDateFormatSeparator() {
//        return getDateFormat().separator();
//    }

    /**
     * Returns the separator for given component
     * @param field - the component field id
     * @return the separator for given component
     */
    public String getDateComponentSeparator(int field) {
        return getComponent(field).separator();
    }

    /**
     * Returns the components for current selected date format
     * @return the components for format which is now in use
     */
    public Map<Integer, Component> getDateFormatComponents() {
        return getDateFormat().components();
    }

    /**
     * Returns the component of the specified field
     * @param field the field of type Calendar.DATE/ Calendar.MONTH/ Calendar.YEAR
     * @return Component - the component object for the field type
     */
    private Component getComponent(int field) {
        return Objects.requireNonNull(getDateFormatComponents().get(field));
    }

    /**
     * Returns the index of the specified component
     * @param field the field of type Calendar.DATE/ Calendar.MONTH/ Calendar.YEAR
     * @return Index - the index object for the field type
     */
    private Index getComponentIndex(int field) {
        return getComponent(field).index();
    }

    /**
     * Returns the start index of the specified component
     * @param field the field of type Calendar.DATE/ Calendar.MONTH/ Calendar.YEAR
     * @return int - the start index of the field type
     */
    private int getComponentStartIndex(int field) {
        return getComponentIndex(field).getStart();
    }

    /**
     * Returns the end index of the specified component
     * @param field the field of type Calendar.DATE/ Calendar.MONTH/ Calendar.YEAR
     * @return int - the end index of the field type
     */
    private int getComponentEndIndex(int field) {
        return getComponentIndex(field).getEnd();
    }

    /**
     * Returns the text edit mode of the view
     * @return the current text edit mode
     */
    private TextEditMode getTextEditMode() {
        return textEditMode;
    }

    /**
     * Sets the text edit mode of the view
     * @param textEditMode - the text edit mode to set
     */
    private void setTextEditMode(TextEditMode textEditMode) {
        this.textEditMode = textEditMode;
    }

    /**
     * Sets the text to the view.
     * The text will not be validate against the current date format of the DateField class.
     * So validation can be done on the text change.
     * @param text the text to set.
     */
    public void setText(String text) {
        setTextMutingWatcher(text);
//        super.setText(text);
//        setDate(text);
    }

    /**
     * Sets the date to the view.
     * The provided String will be validate against the current date format of the DateField class.
     * If the String is empty or null .It will clear the text in the view.
     * @param date the date to set in the view
     */
    public void setDate(String date) {
        try {
            if(null == date || date.isEmpty()) {
                invalidateText();
                return;
            }
            if(getDateFormatLength() != date.trim().length()) {
                throwInvalidArgumentExceptionWithMessage(
                        "Cannot set date. Please provide a valid string representation of the format "
                                + getDateFormat().format() + ". Current provided value is " + date);
                return;
            }
            setDate(parse(date));
        } catch (ParseException e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Cannot set date. Please provide a valid string representation of the format "
                            + getDateFormat().format() + ". Current provided value is " + date);
        } catch (Exception e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Cannot set date. Please provide a valid string representation of the format "
                            + getDateFormat().format() + ". Current provided value is " + date);
        }
    }

    /**
     * Sets the date to the view.
     * @param date the date to set in the view
     */
    public void setDate(Date date) {
        try {
            if(null == date) {
                invalidateText();
                return;
            }
            String dateValue = format(date, getDateFormat().format());
            setTextMutingWatcher(dateValue);
        } catch (Exception e) {
            throwInvalidArgumentExceptionWithMessage(
                    "Cannot set date. Please provide a valid string representation of the format "
                            + getDateFormat().format() + ". Current provided value is " + date);
        }
    }

    /**
     * Sets the date to the view.
     * @param date the date to set in the view
     */
    public void setDate(Calendar date) {
        if(null == date) {
            invalidateText();
            return;
        }
        setDate(date.getTime());
    }

    /**
     * Sets the date to the view.
     * @param year - year of the date to set.
     * @param month - month of the date to set.
     * @param day - day of the date to set.
     */
    public void setDate(int year, int month, int day) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            setDate(calendar.getTime());
        } catch(Exception ex) {
            throwInvalidArgumentExceptionWithMessage(
                    "Cannot set date. Please provide valid year, month, day. Current provided " +
                            "value is year->" + year + "month->" + month + "day->" + day);
            invalidateText();
            return;
        }
    }

    /**
     * Returns the date as a string in the date format of the view.
     * String is not null if and only if the date entered is valid with respect to the format of the field.
     * @return String - current date entered in the view.
     */
    public String getDateString() {
        String date = null;
        try {
            if(StatusType.VALID == getValidationStatus()) {
                date = getText().toString().trim();
            }
        } catch(Exception ex) {
        }
        return date;
    }

    /**
     * Returns the date in this view if and only if the date entered is valid with respect to the format of the field.
     * @return Date - current date entered in the view.
     */
    public Date getDate() {
        Date date = null;
        try {
            if(StatusType.VALID == getValidationStatus()) {
                date = parse(getText().toString().trim());
            }
        } catch(Exception ex) {
        }
        return date;
    }

    /**
     * Invalidates the state of the text view due to change in configuration
     */
    private void invalidateState() {
        invalidateText();
        setMaxLines(MAX_LINE);
        setMaxLength();
        setViewHint();
        setViewInputType();
        setViewKeyListener();
    }

    /**
     * Clears the current text and sets to empty.
     * This will call to clear the current state of the text
     * due to change in configuration or due to validations
     */
    private void invalidateText() {
        setTextMutingWatcher("");
    }

    /**
     * Sets the text in the field after setting the text watcher to null.
     * So that validations of the watcher will not apply.
     * @param text the text to set
     */
    private void setTextMutingWatcher(String text) {
        setTextEditMode(TextEditMode.CODE);
        super.setText(text);
        setTextEditMode(TextEditMode.USER);
    }

    /**
     * Sets the text selection to the end of the text
     */
    private void setTextSelectionToEnd() {
        setSelection(null == getText() ? 0 : getText().length());
    }

    /**
     * Returns the current text watcher instance
     * @return instance of TextWatcher in use
     */
    private TextWatcher getTextWatcher() {
        if(null == textWatcher) {
            setTextWatcher(getTextWatcherInstance());
        }
        return textWatcher;
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
        }
        return  Integer.MIN_VALUE;
    }

    /**
     * Sets the text watcher instance with the specified text watcher
     * @param textWatcher the text watcher to set
     */
    private void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    private TextWatcher getTextWatcherInstance() {
        String currentText;
        final String[] formattedText = new String[1];
//        Component component;
        /** For cache the component value calculated. */
        final int[] componentValue = new int[1];
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(TextEditMode.CODE == getTextEditMode()) {
                    setTextSelectionToEnd();
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextEditMode.CODE == getTextEditMode()) {
                    setTextSelectionToEnd();
                    return;
                }
                try {
                    String currentText = s.toString();
                    /** For clearing the text if, the user edit from in between the text. */
                    if(start >= 0 && start < currentText.length() && count == 0) {
                        invalidateText();
                        return;
                    }
                    Log.d("Date Field", " Date Field onTextChanged: s -> " + s + " start -> " + start + " before -> " + before + " count -> " + count + " currentText.length() " + currentText.length());

                    if(count > 0) {
                        for(Map.Entry<Integer, Component> entry : getDateFormatComponents().entrySet()) {
                            formattedText[0] = getDateFormat().formatInput(getComponent(entry.getKey()), currentText);
                            if(null != formattedText[0]) {
                                currentText = formattedText[0];
                                setTextMutingWatcher(currentText);
                                setTextSelectionToEnd();
                                /** Avoiding
                                 * 1. breaking out of loop once the component is finished and
                                 * 2. changing HashMap to LinkedHashMap
                                 * to handle conditions such as deleting the current text till the
                                 * separator of a component and adding the text.
                                 * Eg:- Current text is 12/31/2011. Delete till 12 or 12/31 including the separator.
                                 * Now add the text to the end should add a separator before adding the current input.*/
//                                break;
                            }
                        }
                    }
                    if(ValidationMode.ON_INPUT == validationMode) {
                        notifyListeners();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextEditMode.CODE == getTextEditMode()) {
                    setTextSelectionToEnd();
                    return;
                }
                if(null != s && getDateFormatLength() == s.length()) {
                    // notify listeners about validity
                    if(ValidationMode.ON_FINISHED_INPUT == validationMode) {
                        notifyListeners();
                    }
                }
            }
        };
    }

    /**
     * Returns the text of the field or empty if null.
     * @return String - the text in the field
     */
    private @NonNull String getNonNullText() {
        return null == getText() ? "" : getText().toString();
    }

    /**
     * Checks whether the input text is invalid in terms of the date format of the view
     * @return is the input text is having invalid length or not
     */
    private boolean isInvalidInputLength() {
        return (null == getText() || getText().toString().trim().isEmpty()
                || getDateFormatLength() != getText().toString().trim().length());
    }

    /**
     * Returns is the entered date is a valid format based on the current view date format
     * and the date is equal to or greater than minDate and is not after maxDate.
     * @return boolean - the input date is valid or not
     */
    public boolean isValidInput() {
        return StatusType.VALID == getValidationStatus();
    }

    /**
     * Returns the validation status type of the current input.
     * @return StatusType - the input date is valid or not with type in class StatusType
     */
    public StatusType getValidationStatus() {
        try {
            if(isInvalidInputLength()) {
                return StatusType.INVALID;
            }
            Date date = parse(getText().toString().trim());
            if(null != getMinDate() && date.before(getMinDate())) {
                return StatusType.OUT_OF_MIN_RANGE;
            }
            if(null != getMaxDate() && date.after(getMaxDate())) {
                return StatusType.OUT_OF_MAX_RANGE;
            }
            return StatusType.VALID;
        } catch (ParseException e) {
        }
        return StatusType.INVALID;
    }

    /**
     * Returns if the listeners are empty
     */
    private boolean isListenersEmpty() {
        return null == listeners || listeners.isEmpty();
    }

    /**
     * Notifies the listeners about the date set
     */
    private void notifyListeners() {
        try {
            if(isListenersEmpty()) {
                return;
            }
            StatusType statusType = getValidationStatus();
            notifyListenersWithDate(getNonNullText(), statusType);
        } catch (Exception ex) {
        }
    }

    /**
     * Notifies the listeners about the date set
     * @param date the date string
     * @param statusType the status type of the operation
     */
    private void notifyListenersWithDate(String date, StatusType statusType) {
        if(isListenersEmpty()) {
            return;
        }
        for(DateInputListener listener : listeners) {
            if(null != listener) {
                listener.onDateSet(date, statusType);
            }
        }
    }

    /**
     * Throws general DateField exception.
     * @param message the message to throw.
     */
    private void throwGeneralExceptionWithMessage(String message) {
        throw new DateFieldException(message);
    }

    /**
     * Throws invalid DateField exception.
     * @param message the message to throw.
     */
    private void throwInvalidArgumentExceptionWithMessage(String message) {
        throw new DateFieldInvalidArgumentException(message);
    }
}
