package com.example.sqliteapp;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class GoalHome extends AppCompatActivity {
    DBHelper DB;
    Button addNewGoal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_home);
        LinearLayout yourlayout= (LinearLayout) findViewById(R.id.yourlayout);

        DB = new DBHelper(this);
        Cursor res = DB.getGoalData();

        ArrayList<String> GoalNames = new ArrayList<>();

        if(res.getCount() == 0){
            Toast.makeText(GoalHome.this, "You have no goals at the moment", Toast.LENGTH_SHORT).show();
        }

        while(res.moveToNext()){
            GoalNames.add(res.getString(0));
        }

        if(!GoalNames.isEmpty()){
            Log.d("val", GoalNames.get(0));
            for (int j = 0; j < GoalNames.size(); j++){
                Button btn = new Button (GoalHome.this);
                btn.setWidth(5);
                btn.setHeight(20);
                btn.setTextSize(25);
                btn.setAllCaps(false);
                btn.setPadding(20, 0, 0, 0);

                btn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);


                btn.setTextColor(Color.parseColor("#000000"));
                btn.setBackgroundColor(Color.parseColor("#FEB450"));

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
                String head = GoalNames.get(j);
                btn.setText(head);
                yourlayout.addView(btn);

                btn.setOnClickListener(handleOnClick(btn, head));

            }
        }
        else{
            TextView t = findViewById(R.id.tv);
            t.setText("You have no goals at the moment");
        }


        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        addNewGoal = findViewById(R.id.addNewGoal);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //navigation view
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
        SessionManagement sessionManagement = new SessionManagement(GoalHome.this);
        sessionManagement.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent i = new Intent(GoalHome.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    public void launchAddGoal(View v) {
        Intent i = new Intent(this, AddNewGoal.class);
        startActivity(i);
    }

    public void launchGoalHome() {
        Intent switchToGoals = new Intent(this, GoalHome.class);
        startActivity(switchToGoals);
    }

    View.OnClickListener handleOnClick(final Button button, String heading) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(GoalHome.this, heading + " is clicked", Toast.LENGTH_SHORT).show();
                Log.d("gg", "Button clicked");
                launchViewGoal(heading);
            }
        };
    }

    public void launchViewGoal(String heading){
        Intent switchActivityIntent = new Intent(this, ViewGoal.class);
        switchActivityIntent.putExtra("Gname", heading);
        startActivity(switchActivityIntent);
    }

}

