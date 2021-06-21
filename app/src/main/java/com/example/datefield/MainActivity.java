package com.example.datefield;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.vcb.edit.datefield.DateField;
import com.vcb.edit.datefield.constants.StatusType;
import com.vcb.edit.datefield.format.FormatTypes;
import com.vcb.edit.datefield.listener.DateInputListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String APP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private DateField dateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        register();
    }

    private void initialize() {
        dateField = findViewById(R.id.dateField);
        dateField.setDateFormat(FormatTypes.DD_MM_YYYY_SLASH);
        dateField.setMinDate("11/05/2021");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 2);
        dateField.setMaxDate(calendar.getTime());
//        dateField.setDate("10/05/2021");
        dateField.setDate(new Date());
        dateField.setLenient(false);
        dateField.setDateFormatAsHint(true);
    }

    private void register() {
        dateField.addInputListener(new DateInputListener() {
            @Override
            public void onDateSet(String date, StatusType statusType) {
                if(StatusType.VALID == statusType) {
                    try {
                        dateField.setError(null);
                        Date dateObject = dateField.parse(date);
                        String dateStringInAppFormat = dateField.formatFieldValue(APP_DATE_FORMAT);
                        String dateStringInAppFormat1 = dateField.format(date, dateField.getDateFormat().format(), APP_DATE_FORMAT);
                        String dateStringInSpecifiedFormat = dateField.format(date, dateField.getDateFormat().format(), "dd MMMM yyyy");
                        Log.d("Date Field", "Date Field onDateSet: date = " + date);
                        Log.d("Date Field", "Date Field onDateSet: dateStringInAppFormat = " + dateStringInAppFormat);
                        Log.d("Date Field", "Date Field onDateSet: dateStringInAppFormat1 = " + dateStringInAppFormat1);
                        Log.d("Date Field", "Date Field onDateSet: dateStringInSpecifiedFormat = " + dateStringInSpecifiedFormat);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.d("Date Field", "Date Field ParseException: " + e.getMessage());
                    }
                } else {
                    dateField.setError("Invalid input");
                    Log.d("Date Field", "Date Field onDateSet: date = " + date + " statusType = " + statusType);
                }
            }
        });
        /** Also can use below method. If not valid, then returns null. */
        if(dateField.isValidInput()) {
            Date dateObject = dateField.getDate();
            String dateString = dateField.getDateString();
            Log.d("Date Field", "Date Field date dateString " + dateString);
        }
    }
}