package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayExpenseDetails extends AppCompatActivity {
    DBHelper DB;
    int idInt;
    String category, date;
    float amount;
    TextView cat, dat, amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_expense_details);
        Intent i = getIntent();
        String textVal = i.getStringExtra("BName");
        String id = i.getStringExtra("incID");
        idInt = Integer.parseInt(id);

        TextView t = findViewById(R.id.head);
//        String disp = textVal+": " + id;
        String disp = textVal;

//        String categoryU = i.getStringExtra("categoryU");
        t.setText(disp);


        cat = findViewById(R.id.catTxtV);
        dat = findViewById(R.id.dateTxtV);
        amt = findViewById(R.id.amtTxtV);

        DB = new DBHelper(this);

        getSingleIncomeData(idInt);
    }

    public void getSingleIncomeData(int id){
        Cursor res = DB.getSingleExpenseDataUsingIncomeID(id);

        while(res.moveToNext()){
            category = res.getString(1);
            date = res.getString(2);
            amount = res.getFloat(3);

            cat.setText(category);
            dat.setText(date);
            amt.setText(String.valueOf(amount));
        }
    }

    public void deleteFromDB(View view){
        //display confirmation pop-up before deletion
        new AlertDialog.Builder(this)
                .setTitle("Delete Budget")
                .setMessage("Are you sure you want to delete this Budget?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean deleteData = DB.deleteExpenseData(idInt);
                        if(deleteData){
                            Toast.makeText(DisplayExpenseDetails.this, "Income deleted", Toast.LENGTH_LONG).show();
                            switchToMainAfterDeletion();
                        }

                        else
                            Toast.makeText(DisplayExpenseDetails.this, "Entry not deleted", Toast.LENGTH_LONG).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void switchToMainAfterDeletion(){
        //I think this is redundant. You can normally switch to main activity without using intents.

        Cursor res = DB.getExpenseData();
        ArrayList<String> myArrList = new ArrayList<>();
        Intent i = new Intent(this, Expense.class);
        if(res.getCount() == 0){
            Toast.makeText(DisplayExpenseDetails.this, "No entry exists", Toast.LENGTH_SHORT).show();
            i.putExtra("EmptyMsg", "Your budget is empty");
            startActivity(i);
            return;
        }

        while(res.moveToNext()){
            myArrList.add(res.getString(0));
        }

        i.putStringArrayListExtra("COOL", myArrList);
        startActivity(i);
    }

    public void switchToUpdateExpense(View v){
        Intent switchActivityIntent = new Intent(this, UpdateExpenseDetails.class);
        switchActivityIntent.putExtra("itemID", String.valueOf(idInt));
        switchActivityIntent.putExtra("category", category);
        switchActivityIntent.putExtra("date", date);
        switchActivityIntent.putExtra("amount", String.valueOf(amount));
        startActivity(switchActivityIntent);
    }

    public void switchToModifyExpense(View v){
        Intent i = new Intent(this, Modify_Expense.class);
        startActivity(i);
    }
}