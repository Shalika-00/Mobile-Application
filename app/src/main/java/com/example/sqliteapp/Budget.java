package com.example.sqliteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Budget extends AppCompatActivity {

    Button createBudget;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        LinearLayout yourlayout= (LinearLayout) findViewById(R.id.yourlayout);

        DB = new DBHelper(this);
        Cursor res = DB.getBudgetData();

        ArrayList<String> BudNames = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();

        if(res.getCount() == 0){
            Toast.makeText(Budget.this, "Your budget is empty", Toast.LENGTH_SHORT).show();
        }

        while(res.moveToNext()){
            BudNames.add(res.getString(0));
            categories.add(res.getString(4));
        }

        if(!BudNames.isEmpty()){
            Log.d("val", BudNames.get(0));
            for (int j = 0; j < BudNames.size(); j++){
                Button btn = new Button (Budget.this);
                btn.setWidth(5);
                btn.setHeight(20);
                btn.setTextSize(25);
                btn.setPadding(20, 0, 0, 0);

                btn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

                if(categories.get(j).equals("Shopping") || categories.get(j).equals("Housing"))
                    btn.setBackgroundColor(Color.parseColor("#FEB450"));
                else if(categories.get(j).equals("Food & Drinks") || categories.get(j).equals("Life & Entertainment"))
                    btn.setBackgroundColor(Color.parseColor("#EE5F5F"));
                else if(categories.get(j).equals("Vehicle"))
                    btn.setBackgroundColor(Color.parseColor("#FBEE4A"));
                else if(categories.get(j).equals("Transportation"))
                    btn.setBackgroundColor(Color.parseColor("#57C7F1"));
                else if(categories.get(j).equals("Communication, PC"))
                    btn.setBackgroundColor(Color.parseColor("#E668FA"));
                else if(categories.get(j).equals("Investments") || categories.get(j).equals("Financial Expenses"))
                    btn.setBackgroundColor(Color.parseColor("#42E375"));
                else if(categories.get(j).equals("Others"))
                    btn.setBackgroundColor(Color.parseColor("#F6E315"));

                //Get the width of the screen
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;

                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                //calculate and set the button width
                int buttonWidth = (int) (width / 1.5);
                buttonLayoutParams.width = buttonWidth;

                if(j == 0)
                    buttonLayoutParams.setMargins(80, 500, 80, 0);
                else
                    buttonLayoutParams.setMargins(80, 20, 80, 0);

                btn.setLayoutParams(buttonLayoutParams);

                //Set button text
                String head = BudNames.get(j);
                String category = categories.get(j);
                btn.setText(head);
                yourlayout.addView(btn);

                btn.setOnClickListener(handleOnClick(btn, head, category));
            }
        }
        else{
            TextView t = findViewById(R.id.tv);
            t.setText("Your budget is empty");
        }


        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        createBudget = findViewById(R.id.createBudgetBtn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch(id){
                    case R.id.nav_home:
//                        Toast.makeText(MainActivity.this, "Home is clicked", Toast.LENGTH_SHORT).show();break;
                        switchIntent(MainActivity.class);
                        break;
                    case R.id.nav_expenses:
//                        Toast.makeText(MainActivity.this, "Expenses is clicked", Toast.LENGTH_SHORT).show();
                        switchIntent(Expense.class);
                        break;
                    case R.id.nav_income:
//                        Toast.makeText(MainActivity.this, "Income is clicked", Toast.LENGTH_SHORT).show();
                        switchIntent(Income.class);
                        break;
                    case R.id.nav_budget:
//                        Toast.makeText(MainActivity.this, "Budget is clicked", Toast.LENGTH_SHORT).show();
                        switchIntent(Budget.class);
                        break;
                    case R.id.nav_goals:
//                        Toast.makeText(MainActivity.this, "Goal is clicked", Toast.LENGTH_SHORT).show();
                        switchIntent(GoalHome.class);
                        break;
                    case R.id.nav_login:
//                        Toast.makeText(MainActivity.this, "Login is clicked", Toast.LENGTH_SHORT).show();
//                        switchIntent(Login.class);
                        logOut();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    public void switchIntent(Class<?> cls){
        Intent i = new Intent(this, cls);
        startActivity(i);
    }

    public void logOut(){
        // remove the session and open login screen
        SessionManagement sessionManagement = new SessionManagement(Budget.this);
        sessionManagement.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent i = new Intent(Budget.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }


    public void switchToCreateBudget(View v){
        Intent switchActivityIntent = new Intent(this, CreateBudgetUI.class);
        startActivity(switchActivityIntent);
    }

    View.OnClickListener handleOnClick(final Button button, String heading, String category) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Budget.this, "Button is clicked", Toast.LENGTH_SHORT).show();
                Log.d("gg", "Button clicked");
                switchToDisplayBudget(heading, category);
            }
        };
    }

    public void switchToDisplayBudget(String heading, String category){
        Intent switchActivityIntent = new Intent(this, DisplayBudget.class);
        switchActivityIntent.putExtra("BName", heading);
        switchActivityIntent.putExtra("BCategory", category);
        startActivity(switchActivityIntent);
    }
}