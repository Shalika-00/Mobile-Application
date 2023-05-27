package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class DisplayBudget extends AppCompatActivity {
    Button deleteBudget;
    TextView Bheading, amt, dt, curr, cat, stDate, currBal, timeLeft, remainingPer;
    DBHelper DB;
    String textVal, date, currency, category, startDate, currBalance, prevCat;
    float amount;
    DonutProgressView dpvChart;
    float balancePercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_budget);

        Intent i = getIntent();
        textVal = i.getStringExtra("BName");
        prevCat = i.getStringExtra("BCategory");

        deleteBudget = findViewById(R.id.deleteBudgetBtn);
        Bheading = findViewById(R.id.heading);
        Bheading.setText(textVal);

        amt = findViewById(R.id.amount);
        dt = findViewById(R.id.date);
        curr = findViewById(R.id.curr);
        cat = findViewById(R.id.categoryView);
        stDate = findViewById(R.id.startDate);
        currBal = findViewById(R.id.currBalance);
        timeLeft = findViewById(R.id.timeLeftTxtV);
        remainingPer = findViewById(R.id.remainingPer);

        DB = new DBHelper(this);

        try {
            getSingleBudgetData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dpvChart = findViewById(R.id.dpvChart);

        DonutSection section1 = new DonutSection("Section 1 Name", Color.parseColor("#f44336"), balancePercentage);
        dpvChart.setCap(100f);
        dpvChart.submitData(new ArrayList<>(Collections.singleton(section1)));

        //notification when the budget is reached
        if(balancePercentage == 100f){
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFromDB(View v){
        //display confirmation pop-up before deletion
        new AlertDialog.Builder(this)
                .setTitle("Delete Budget")
                .setMessage("Are you sure you want to delete this Budget?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean deleteData = DB.deleteBudgetData(prevCat);
                        if(deleteData){
                            Toast.makeText(DisplayBudget.this, "Budget deleted", Toast.LENGTH_LONG).show();
                            switchToMainAfterDeletion();
                        }

                        else
                            Toast.makeText(DisplayBudget.this, "Entry not deleted", Toast.LENGTH_LONG).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void switchToMainAfterDeletion(){
        //I think this is redundant. You can normally switch to main activity without using intents.

        Cursor res = DB.getBudgetData();
        ArrayList<String> myArrList = new ArrayList<>();
        Intent i = new Intent(this, Budget.class);
        if(res.getCount() == 0){
            Toast.makeText(DisplayBudget.this, "No entry exists", Toast.LENGTH_SHORT).show();
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

    public void switchToMainActivity(View v){
        Intent switchActivityIntent = new Intent(this, Budget.class);
        startActivity(switchActivityIntent);
    }

    public void getSingleBudgetData() throws ParseException {
        Cursor res = DB.getSingleBudgetData(prevCat);
        while(res.moveToNext()){
            date = res.getString(1);
            Log.d("dt", date);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date compDate = formatter.parse(date);
            String dd1 = formatter.format(compDate);

//            Log.d("compDate", formatter.format(compDate));

            amount = res.getFloat(2);
            currency = res.getString(3);
            category = res.getString(4);

            startDate = res.getString(7);
            Date sDate = formatter.parse(startDate);
            String dd2 = formatter.format(sDate);
//            @SuppressLint("SimpleDateFormat") Date sDate = new SimpleDateFormat("dd-MMM-yyyy").parse(date);

            //Today date
            DatePicker dp = new DatePicker();
            Date today = formatter.parse(dp.getTodaysDate());


            currBalance = res.getString(8);

            balancePercentage = (Float.parseFloat(currBalance)/ amount) * 100;
            String bPercentage = String.format("%.2f", balancePercentage) + "%";
            remainingPer.setText(bPercentage);
            if(bPercentage.equals("100%")){
                Toast.makeText(DisplayBudget.this, "You have reached the limit", Toast.LENGTH_SHORT).show();
            }

            dt.setText(date);
            amt.setText(String.valueOf(amount));
            curr.setText(currency);
            cat.setText(category);
            stDate.setText(startDate);
            currBal.setText(currBalance);

            if (compDate != null && today != null) {
                long difference = Math.abs(compDate.getTime() - today.getTime());
                Log.d("left", String.valueOf(difference) + " days");
                long difftDays = difference / (24 * 60 * 60 * 1000);

                String timeDiff = String.valueOf(difftDays) + " days";
                timeLeft.setText(timeDiff);

            }

        }
    }

    public void switchToUpdateBudget(View v){
        Intent switchActivityIntent = new Intent(this, UpdateBudget.class);
        switchActivityIntent.putExtra("BName", textVal);
        switchActivityIntent.putExtra("category", category);
        switchActivityIntent.putExtra("amount", String.valueOf(amount));
        startActivity(switchActivityIntent);
    }
}