package com.example.sqliteapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class DeleteGoal extends AppCompatActivity {

    EditText name, goalAmount, goalDescription, addSavings;
    DatePickerDialog datePickerDialog;
    Button delete, estimatedDate, category;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_goal);

        goalAmount = findViewById(R.id.goalAmount);
        goalDescription = findViewById(R.id.goalDescription);
        initDatePicker();
        estimatedDate = findViewById(R.id.estimatedDate);
        estimatedDate.setText(getTodaysDate());
        addSavings = findViewById(R.id.addSavings);

        delete = findViewById(R.id.delete);
        DB = new DBHelper(this);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameSrc = name.getText().toString();

                Boolean checkdeletedata = DB.deleteGoalData(nameSrc);
                if(checkdeletedata==true){
                    Toast.makeText(DeleteGoal.this, "Entry deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DeleteGoal.this, "Entry Not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                estimatedDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListner, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }


    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";
    }

    public void addDatePop(View v) {

        datePickerDialog.show();

    }

}
