package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class Update_details extends AppCompatActivity {

   /* TextInputLayout cat_lay;
    AutoCompleteTextView auto_cat;

    ArrayList<String> arrL_cat;
    ArrayAdapter<String> arrA_cat;*/
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
        setContentView(R.layout.activity_update_details);



        /*cat_lay=(TextInputLayout)findViewById(R.id.text_inputLayout);
        auto_cat=(AutoCompleteTextView)findViewById(R.id.autoCompleteTxt);



        arrL_cat = new ArrayList<>();

        arrL_cat.add("Checks, Coupons");
        arrL_cat.add("Child Support");
        arrL_cat.add("Dues & Grants");
        arrL_cat.add("Gifts");
        arrL_cat.add("Interests, Dividends");
        arrL_cat.add("Lending, Renting");
        arrL_cat.add("Lottery, Gambling");
        arrL_cat.add("Refunds (Tax,Purchase)");
        arrL_cat.add("Rental Income");
        arrL_cat.add("Sale");
        arrL_cat.add("Wage, Invoices");
        arrL_cat.add("Other");

        arrA_cat = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrL_cat);
        auto_cat.setAdapter(arrA_cat);

        auto_cat.setThreshold(1);
*/
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

    /*public void switchToCategory(View view){
        Intent switchActivityIntent = new Intent(this, Category.class);
        startActivity(switchActivityIntent);
    }*/


    /*private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString( day, month, year);
    }*/

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
                .setTitle("Update Budget")
                .setMessage("Are you sure you want to Update this Budget?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean checkUpdatedData = Database.updateIncomeData(idU,categoryU, newDate, newAmount);
                        if(checkUpdatedData){
                            Toast.makeText(Update_details.this, "Income updated", Toast.LENGTH_LONG).show();
                            switchToMainActivity();
                        }
                        else
                            Toast.makeText(Update_details.this, "Entry not updated", Toast.LENGTH_LONG).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void switchToMainActivity(){
        Intent switchActivityIntent = new Intent(this, Income.class);
        startActivity(switchActivityIntent);
    }

    public void backToDisplayBudget(View v){
        Intent intent = new Intent(this, DisplayIncomeDetails.class);
        intent.putExtra("incID", idU);
        intent.putExtra("BName", categoryU);

        startActivity(intent);
    }

}