package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateBudgetUI extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    EditText BName, BAmount;
    TextView Bcategory;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    DBHelper DB;

    DatePicker dp = new DatePicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_budget_ui);

        dateButton = findViewById(R.id.datePickerBtn);
        dateButton.setText(dp.getTodaysDate());
        dp.initDatePicker(dateButton, this);

        BName = findViewById(R.id.budget_name);
        BAmount = findViewById(R.id.budget_amount);
        Bcategory = findViewById(R.id.categoryTxtV);

        //create a DBHelper object
        DB = new DBHelper(this);

        Intent i = getIntent();
        String cat = i.getStringExtra("category");
        if(cat != null){
            Bcategory.setText(cat);
        }
    }


    public void openDatePickerMethod(View view)
    {
        dp.openDatePicker();
    }

    public void switchToMainActivity(View v){
        Intent switchActivityIntent = new Intent(this, Budget.class);
        startActivity(switchActivityIntent);
    }

    public void switchToCategoryPage(View v){
        //Move to shali's page
        Intent switchActivityIntent = new Intent(this, BudCategory.class);
        switchActivityIntent.putExtra("fromPage", this.getClass().getSimpleName());
        startActivity(switchActivityIntent);
    }

    public void insertToDB(View v){
        String name = BName.getText().toString();
        String date = dateButton.getText().toString();
        String amountStr = BAmount.getText().toString();
        float amount = Float.parseFloat(amountStr);
        String category = Bcategory.getText().toString();
        String todayDate = dp.getTodaysDate();
        float currentAmt = 0;

        if(TextUtils.isEmpty(BName.getText())){
            Toast.makeText(CreateBudgetUI.this, "Please Insert budget details", Toast.LENGTH_LONG).show();
            BName.setError( "Budget name is required!" );
        }
        else if(category.equals("None")){
            Toast.makeText(CreateBudgetUI.this, "Please Insert budget details", Toast.LENGTH_LONG).show();
            Bcategory.setError( "Budget category is required!" );
        }
        else if(amountStr.equals("0")){
            Toast.makeText(CreateBudgetUI.this, "Please Insert budget details", Toast.LENGTH_LONG).show();
            BAmount.setError( "Budget name is required!" );
        }

        else{
            boolean checkInsertData = DB.insertBudgetData(name, date, amount, "LKR", category, 1, 1, todayDate, currentAmt);
            if(checkInsertData){
                Toast.makeText(CreateBudgetUI.this, "New Budget inserted", Toast.LENGTH_LONG).show();
                BName.setText(null);
                BAmount.setText(null);
                Bcategory.setText("None");
                Intent switchActivityIntent = new Intent(this, Budget.class);
                startActivity(switchActivityIntent);
            }
            else
                Toast.makeText(CreateBudgetUI.this, "New entry not inserted", Toast.LENGTH_LONG).show();
        }
    }

}