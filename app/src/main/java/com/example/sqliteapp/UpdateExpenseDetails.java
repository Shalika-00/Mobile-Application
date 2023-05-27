package com.example.sqliteapp;

//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateExpenseDetails extends AppCompatActivity {

    Button btndate;
    DatePickerDialog datePickerDialog;
    String dateU, amountU,categoryU, idU;
    TextView tablecat;
    EditText amount_update;
    DBHelper Database = new DBHelper(this);
    int intId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense_details);

        Intent i = getIntent();
        dateU = i.getStringExtra("date");
        amountU = i.getStringExtra("amount");
        categoryU = i.getStringExtra("category");
        idU =  i.getStringExtra("itemID");
        intId = Integer.parseInt(idU);


        btndate = findViewById(R.id.btndate);
        tablecat =findViewById(R.id.tablecat);
        amount_update = findViewById(R.id.amount_update);

        initDatePicker();
        btndate.setText(dateU);
        tablecat.setText(categoryU);
        amount_update.setText(amountU);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String sDate = makeDateString(day,month,year);
                btndate.setText(sDate);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year, month, day);

    }

    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " +day+ " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }


    public void onClickSave(View v){
        float newAmount = Float.parseFloat(amount_update.getText().toString());
        String newDate = btndate.getText().toString();

        /*if( TextUtils.isEmpty(BName.getText()) || budgetAmt == 0){
            Toast.makeText(UpdateBudget.this, "Please Fill all the fields", Toast.LENGTH_LONG).show();
            BName.setError( "First name and amount is required!" );
        }
*/

        new AlertDialog.Builder(this)
                .setTitle("Update Expense")
                .setMessage("Are you sure you want to Update this Expense?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean checkUpdatedData = Database.updateExpense(idU,categoryU, newDate, newAmount);
                        if(checkUpdatedData){
                            Toast.makeText(UpdateExpenseDetails.this, "Expense updated", Toast.LENGTH_LONG).show();
                            switchToMainActivity();
                        }
                        else
                            Toast.makeText(UpdateExpenseDetails.this, "Entry not updated", Toast.LENGTH_LONG).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void switchToMainActivity(){
        Intent switchActivityIntent = new Intent(this, Expense.class);
        startActivity(switchActivityIntent);
    }

    public void backToDisplayExpense(View v){
        Intent intent = new Intent(this, DisplayExpenseDetails.class);
        intent.putExtra("BName", categoryU);
        intent.putExtra("incID", idU);
        startActivity(intent);
    }
}