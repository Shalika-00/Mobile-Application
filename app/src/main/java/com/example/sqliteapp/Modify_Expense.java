package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Modify_Expense extends AppCompatActivity {
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_expense);

        LinearLayout yourlayout= (LinearLayout) findViewById(R.id.incLayout);

        DB = new DBHelper(this);
        Cursor res = DB.getExpenseData();
        ArrayList<String> incCat = new ArrayList<>();
        ArrayList<Integer> incID = new ArrayList<>();
//        ArrayList<String> IncNames = new ArrayList<>();

        if(res.getCount() == 0){
            Toast.makeText(Modify_Expense.this, "Your income is empty", Toast.LENGTH_SHORT).show();
        }

        while(res.moveToNext()){
            incCat.add(res.getString(1));
            incID.add(res.getInt(0));
//            categories.add(res.getString(4));
        }
        if(!incCat.isEmpty()){
            for (int j = 0; j < incCat.size(); j++){
                Button btn = new Button (Modify_Expense.this);
                btn.setWidth(5);
                btn.setHeight(20);
                btn.setTextSize(25);
                btn.setPadding(20, 0, 0, 0);

                btn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

                if(incCat.get(j).equals("Shopping") || incCat.get(j).equals("Housing"))
                    btn.setBackgroundColor(Color.parseColor("#FEB450"));
                else if(incCat.get(j).equals("Food & Drinks") || incCat.get(j).equals("Life & Entertainment"))
                    btn.setBackgroundColor(Color.parseColor("#EE5F5F"));
                else if(incCat.get(j).equals("Vehicle"))
                    btn.setBackgroundColor(Color.parseColor("#FBEE4A"));
                else if(incCat.get(j).equals("Transportation"))
                    btn.setBackgroundColor(Color.parseColor("#57C7F1"));
                else if(incCat.get(j).equals("Communication, PC"))
                    btn.setBackgroundColor(Color.parseColor("#E668FA"));
                else if(incCat.get(j).equals("Investments") || incCat.get(j).equals("Financial Expenses"))
                    btn.setBackgroundColor(Color.parseColor("#42E375"));
                else if(incCat.get(j).equals("Others"))
                    btn.setBackgroundColor(Color.parseColor("#F6E315"));

                btn.setTextColor(Color.parseColor("#000000"));
//                btn.setBackgroundColor(Color.parseColor("#42E375"));

                //Get the width of the screen
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;

                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                //calculate and set the button width
                int buttonWidth = (int) (width / 1.5);
                buttonLayoutParams.width = buttonWidth;

                if(j == 0)
                    buttonLayoutParams.setMargins(80, 50, 80, 0);
                else
                    buttonLayoutParams.setMargins(80, 20, 80, 0);

                btn.setLayoutParams(buttonLayoutParams);

                //Set button text
                String head = incCat.get(j);
                int id = incID.get(j);
                btn.setText(head);
                yourlayout.addView(btn);

                btn.setOnClickListener(handleOnClick(btn, head, id));
            }
        }

    }

    View.OnClickListener handleOnClick(final Button button, String heading, int id) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Modify_Expense.this, "Button is clicked", Toast.LENGTH_SHORT).show();
                switchToDisplayExpenseDetails(heading, id);
            }
        };
    }

    public void switchToDisplayExpenseDetails(String heading, int id){
        Intent switchActivityIntent = new Intent(this, DisplayExpenseDetails.class);
        switchActivityIntent.putExtra("BName", heading);
        switchActivityIntent.putExtra("incID", String.valueOf(id));
        startActivity(switchActivityIntent);
    }

    public void switchToExpense(View v){
        Intent i = new Intent(this, Expense.class);
        startActivity(i);
    }
}