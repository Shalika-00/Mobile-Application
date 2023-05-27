package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBudget extends AppCompatActivity {
    EditText BName, updateAmtField;
    String budName, category, amount;
    Button datePickerBtn;
    DBHelper DB = new DBHelper(this);
    DatePicker dp = new DatePicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_budget);
        updateAmtField = findViewById(R.id.updateBudAmt);

        Intent i = getIntent();
        budName = i.getStringExtra("BName");
        category = i.getStringExtra("category");
        amount = i.getStringExtra("amount");
        updateAmtField.setText(amount);

        BName = findViewById(R.id.BName);
        BName.setText(budName);

        datePickerBtn = findViewById(R.id.datePickerBtn);
        datePickerBtn.setText(dp.getTodaysDate());
        dp.initDatePicker(datePickerBtn, this);
    }

    public void openDatePickerMethod(View view)
    {
        dp.openDatePicker();
    }

    public void onClickSave(View v){
        String budgetName = BName.getText().toString();
        float budgetAmt = Float.parseFloat(updateAmtField.getText().toString());
        String newCompDate = datePickerBtn.getText().toString();

        if( TextUtils.isEmpty(BName.getText()) || budgetAmt == 0){
            Toast.makeText(UpdateBudget.this, "Please Fill all the fields", Toast.LENGTH_LONG).show();

            BName.setError( "First name and amount is required!" );
        }

        //display confirmation pop-up before deletion
        new AlertDialog.Builder(this)
                .setTitle("Update Budget")
                .setMessage("Are you sure you want to Update this Budget?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean checkUpdatedData = DB.updateBudget(category, budgetAmt, newCompDate, budgetName);
                        if(checkUpdatedData){
                            Toast.makeText(UpdateBudget.this, "Budget updated", Toast.LENGTH_LONG).show();
                            switchToMainActivity();
                        }
                        else
                            Toast.makeText(UpdateBudget.this, "Entry not updated", Toast.LENGTH_LONG).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void switchToMainActivity(){
        Intent switchActivityIntent = new Intent(this, Budget.class);
        startActivity(switchActivityIntent);
    }

    public void backToDisplayBudget(View v){
        Intent i = new Intent(this, DisplayBudget.class);
        i.putExtra("BCategory", category);
        i.putExtra("BName", budName);
        startActivity(i);
    }


}