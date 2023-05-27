package com.example.sqliteapp;
//My name is Yohan
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Calendar;

public class AddNewGoal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, goalAmount, goalDescription, addSavings;
    DatePickerDialog datePickerDialog;
    Button create, estimatedDate, category;
    DBHelper DB;
    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_goal);


        name = findViewById(R.id.name);
        goalAmount = findViewById(R.id.goalAmount);
        goalDescription = findViewById(R.id.goalDescription);
        initDatePicker();
        estimatedDate = findViewById(R.id.estimatedDate);
        estimatedDate.setText(getTodaysDate());
        addSavings = findViewById(R.id.addSavings);
        category = findViewById(R.id.category);

        Intent i = getIntent();
        String cat = i.getStringExtra("BtnTxt");
        if(cat != null){
            category.setText(cat);
        }

        create = findViewById(R.id.update);
        DB = new DBHelper(this);
    }
            public void onClick(View view) {
                String nameSrc = name.getText().toString();
                String goalAmountSrc = goalAmount.getText().toString();
                float goalAmounts = Float.parseFloat(goalAmountSrc);
                String goalDescriptionSrc = goalDescription.getText().toString();
                String estimatedDateSrc = estimatedDate.getText().toString();
                String categorySrc = category.getText().toString();
                String addSavingsSrc = addSavings.getText().toString();
                float addSavingsAmount = Float.parseFloat(addSavingsSrc);
                String todayDate = getTodaysDate();


                if( TextUtils.isEmpty(name.getText())){
//                    Toast.makeText(AddNewGoal.this, "Please Insert goal name", Toast.LENGTH_LONG).show();

                    name.setError( "Goal name is required!" );

                }else if(goalAmounts == 0){
                    goalAmount.setError( "Goal amount is required!" );
                }else{

                Boolean cheackinsertdata = DB.insertGoalData(nameSrc, estimatedDateSrc, goalAmounts, categorySrc, goalDescriptionSrc, addSavingsAmount, todayDate);
                if(cheackinsertdata){
                    Toast.makeText(AddNewGoal.this, "New Goal Added", Toast.LENGTH_SHORT).show();
                    name.setText(null);
                    goalAmount.setText(null);
                    goalDescription.setText(null);
                    estimatedDate.setText(null);
                    category.setText("Category");
                    addSavings.setText(null);
                    Intent switchActivityIntent = new Intent(this, GoalHome.class);
                    startActivity(switchActivityIntent);
                }else{
                    Toast.makeText(AddNewGoal.this, "Goal Not Added", Toast.LENGTH_SHORT).show();
                }
                }
            }


    public String getTodaysDate() {
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
        return day + "-" + getMonthFormat(month) + "-" + year;
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

    public void switchToMainActivity(View v){
        Intent switchActivityIntent = new Intent(this, GoalHome.class);
        startActivity(switchActivityIntent);
    }

    public void switchToCategory(View view){
        Intent switchActivityIntent = new Intent(this, GoalCategory.class);
        startActivity(switchActivityIntent);
    }

    // dropdown menu methods

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
