package com.example.sqliteapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class DatePicker {
    String date;
    private DatePickerDialog datePickerDialog;
    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1; //since month starts from 0
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void initDatePicker(Button dateBtn, Context ctx)
    {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                date = makeDateString(day, month, year);
                if(dateBtn.getText() != null){
                    dateBtn.setText(date);
                }

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(ctx, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()); //set the Min date to current date

    }

    public String makeDateString(int day, int month, int year) {
        return day + "-" + getMonthFormat(month)  + "-" + year;
    }

    public String getMonthFormat(int month) {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //default should never happen
        return "Jan";
    }

    public void openDatePicker()
    {
        datePickerDialog.show();
    }
}
