package com.example.sqliteapp;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;


public class ViewGoal extends AppCompatActivity {
        Button delete, update;
        TextView goalDescription, goalHeading, timeLeft,remainingPer, amountLeft, goal_date, categoryView;
        String textVal, goalDesc, goalHead;
        //
        DonutProgressView dpvChart;
        String goalDate, goalcategory;
        float goalAmt, goalSavings, balancePercentage, remainingAmnt;

    DBHelper DB;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.view_goal);

            Intent i = getIntent();
            textVal = i.getStringExtra("Gname");

            update = findViewById(R.id.update);
            delete = findViewById(R.id.delete);

            categoryView = findViewById(R.id.categoryValue);
            goal_date = findViewById(R.id.goal_date);
            amountLeft  =findViewById(R.id.progress_rem_amount);
            timeLeft = findViewById(R.id.progress_rem_time);
            goalDescription = findViewById(R.id.goalDesc);
            goalHeading = findViewById(R.id.GoalHeading);
            remainingPer = findViewById(R.id.remainingPer);


            DB = new DBHelper(this);

            try {
                getSingleGoalData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dpvChart = findViewById(R.id.dpvChart);

            DonutSection section1 = new DonutSection("Section 1 Name", Color.parseColor("#f44336"), balancePercentage);
            dpvChart.setCap(100f);
            dpvChart.submitData(new ArrayList<>(Collections.singleton(section1)));

            //notification when the goal is reached
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



        public void launchUpdateGoal(View v){
            Intent i = new Intent(this, UpdateGoal.class);
            i.putExtra("Gname", textVal);
            i.putExtra("Gdate", String.valueOf(goalDate));
            i.putExtra("GAmt", String.valueOf(goalAmt));
            i.putExtra("GDesc", goalDesc);
            i.putExtra("GSave", String.valueOf(goalSavings));


            startActivity(i);
        }

        public void switchToMainActivity(View v){
            Intent switchActivityIntent = new Intent(this, GoalHome.class);
            startActivity(switchActivityIntent);
        }

        public void getSingleGoalData() throws ParseException {
            Cursor res = DB.getSingleGoalData(textVal);
            while(res.moveToNext()){
                goalHead = res.getString(0);

                goalDate = res.getString(1);
                goalAmt = res.getFloat(2);
                goalcategory = res.getString(3);
                goalDesc = res.getString(4);
                goalSavings = res.getFloat(5);
                goalDescription.setText(goalDesc);
                goalHeading.setText(goalHead);
//                amountLeft.setText(String.valueOf(goalAmt));
//                amountLeft.setText(String.valueOf(goalAmt));
                goal_date.setText("     "+goalDate);
                categoryView.setText("         "+goalcategory);


                //
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                Date compDate = formatter.parse(goalDate);
//                String dd1 = formatter.format(compDate);

                AddNewGoal dp = new AddNewGoal();
                Date today = formatter.parse(dp.getTodaysDate());

                if (compDate != null && today != null) {
                    long difference = Math.abs(compDate.getTime() - today.getTime());
                    Log.d("left", String.valueOf(difference) + " days");
                    long difftDays = difference / (24 * 60 * 60 * 1000);

                    String timeDiff = String.valueOf(difftDays) + " days";
                    timeLeft.setText(""+timeDiff);
                }

                balancePercentage = (goalSavings)/ (goalAmt) * 100;
                String bPercentage = String.format("%.2f", balancePercentage) + "%";
                remainingPer.setText(bPercentage);
                if(bPercentage.equals("100%")){
                    Toast.makeText(ViewGoal.this, "You have reached the goal", Toast.LENGTH_SHORT).show();
                }

                remainingAmnt = goalAmt - goalSavings;
//                String remAmnt = String.valueOf(remainingAmnt);
//                Log.d("remt", remAmnt);
                amountLeft.setText("LKR "+String.valueOf(remainingAmnt));

            }
        }

        //delete

    public void deleteGoal(View v){
        //display confirmation pop-up before deletion
        new AlertDialog.Builder(this)
                .setTitle("Delete Goal")
                .setMessage("Are you sure you want to delete this Goal?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean deleteData = DB.deleteGoalData(textVal);
                        if(deleteData){
                            Toast.makeText(ViewGoal.this, "Goal deleted", Toast.LENGTH_LONG).show();
                            switchToMainAfterDeletion();
                        }

                        else
                            Toast.makeText(ViewGoal.this, "Goal not deleted", Toast.LENGTH_LONG).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void switchToMainAfterDeletion(){

        Cursor res = DB.getGoalData();
        ArrayList<String> myArrList = new ArrayList<>();
        Intent i = new Intent(this, GoalHome.class);
        if(res.getCount() == 0){
            Toast.makeText(ViewGoal.this, "You have no goal at the moment", Toast.LENGTH_SHORT).show();
            i.putExtra("EmptyMsg", "You have no goal at the moment");
            startActivity(i);
            return;
        }

        while(res.moveToNext()){
            myArrList.add(res.getString(0));
        }

        i.putStringArrayListExtra("COOL", myArrList);
        startActivity(i);
    }

    }

