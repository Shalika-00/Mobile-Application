package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BudExpenses extends AppCompatActivity {
    DBHelper DB;
    TextView categoryTxtv;
    String cat;
    EditText amtIn;
    float newAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budexpenses);
        categoryTxtv = findViewById(R.id.categoryTxtV2);
        amtIn = findViewById(R.id.amountInputField);
//        newAmount = Double.parseDouble(amtIn.getText().toString());
//        newAmount = Float.parseFloat(amtIn.getText().toString());

        Intent i = getIntent();
        cat = i.getStringExtra("category");
        if(cat != null){
            categoryTxtv.setText(cat);
        }
        DB = new DBHelper(this);
    }

    public void switchToCategoryPage(View v){
        //Move to shali's page
        Intent switchActivityIntent = new Intent(this, BudCategory.class);
        switchActivityIntent.putExtra("fromPage", this.getClass().getSimpleName());
        startActivity(switchActivityIntent);
    }

    public void switchToMainActivity(View v){
        Intent switchActivityIntent = new Intent(this, Budget.class);
        startActivity(switchActivityIntent);
    }

    public void updateCurrBudgetBalance(View v){
        Cursor res = DB.getSingleBudgetDataUsingCategory(cat);
        while(res.moveToNext()){
            float currAmt = res.getFloat(8);
            newAmount = Float.parseFloat(amtIn.getText().toString());
            currAmt += newAmount;
            boolean checkUpdatedData = DB.updateBudgetCurrBalance(currAmt, cat);

            if(checkUpdatedData){
                Toast.makeText(BudExpenses.this, "Entry updated", Toast.LENGTH_LONG).show();
                Intent switchActivityIntent = new Intent(this, Budget.class);
                startActivity(switchActivityIntent);
            }
            else
                Toast.makeText(BudExpenses.this, "Entry not updated", Toast.LENGTH_LONG).show();
        }
    }
}