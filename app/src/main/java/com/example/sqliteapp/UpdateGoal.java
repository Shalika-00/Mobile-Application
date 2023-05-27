package com.example.sqliteapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Calendar;


public class UpdateGoal extends AppCompatActivity {

    EditText name, goalAmount, goalDescription, addSavings;
    String GoalName, goalAmt, goalDesc, goalSavings;
    TextView GoalHeading;
    Button update;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_goal);

        GoalHeading = findViewById(R.id.GoalHeading);
        goalAmount = findViewById(R.id.goalAmount);
        goalDescription = findViewById(R.id.goalDescription);

        addSavings = findViewById(R.id.addSavings);

        Intent i = getIntent();

        GoalName = i.getStringExtra("Gname");
        goalAmt = i.getStringExtra("GAmt");
        goalDesc = i.getStringExtra("GDesc");
        goalSavings = i.getStringExtra("GSave");
        GoalHeading.setText(GoalName);
        goalAmount.setText(goalAmt);
        goalDescription.setText(goalDesc);




        update = findViewById(R.id.update);
        DB = new DBHelper(this);
    }
            public void onClick(View view) {
                String nameSrc = GoalHeading.getText().toString();
                String goalAmountSrc = goalAmount.getText().toString();
                float goalAmounts = Float.parseFloat(goalAmountSrc);
                String goalDescriptionSrc = goalDescription.getText().toString();

                String addSavingsSrc = addSavings.getText().toString();
                float addSavingsAmount = Float.parseFloat(addSavingsSrc);
                //
                addSavingsAmount = Float.parseFloat(goalSavings) + addSavingsAmount;

                if( TextUtils.isEmpty(GoalHeading.getText()) || goalAmounts == 0){
                    Toast.makeText(UpdateGoal.this, "Please Fill all the Fields", Toast.LENGTH_LONG).show();

                    name.setError( "Details are required" );
                }
                //display confirmation pop-up before deletion
                float finalAddSavingsAmount = addSavingsAmount;
                new AlertDialog.Builder(this)
                        .setTitle("Update Goal")
                        .setMessage("Are you sure you want to Update this Goal?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Boolean checkupdatedata = DB.updateGoalData(nameSrc, goalAmounts, goalDescriptionSrc, finalAddSavingsAmount);
                                if (checkupdatedata) {
                                    Toast.makeText(UpdateGoal.this, "Goal updated", Toast.LENGTH_SHORT).show();
                                    switchToMainActivity();
                                } else {
                                    Toast.makeText(UpdateGoal.this, "Goal Not updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                            })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
    public void switchToMainActivity(){
        Intent switchActivityIntent = new Intent(this, GoalHome.class);
        startActivity(switchActivityIntent);
    }


    public void backToViewGoal(View v){
        Intent i = new Intent(this, ViewGoal.class);
        i.putExtra("Gname", GoalName);
        startActivity(i);
    }



}
