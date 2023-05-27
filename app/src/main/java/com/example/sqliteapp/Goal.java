package com.example.sqliteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class Goal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_activity_main);
        LinearLayout yourlayout= (LinearLayout) findViewById(R.id.yourlayout);

        DBHelper DB = new DBHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

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
                        switchIntent(Login.class);
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

    public void launchGoalHome() {
        Intent switchToGoals = new Intent(this, GoalHome.class);
        startActivity(switchToGoals);
    }

    // dropdown menu methods0
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

