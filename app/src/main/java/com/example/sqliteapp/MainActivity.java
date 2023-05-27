package com.example.sqliteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class MainActivity extends AppCompatActivity {
    DBHelper DB;
    DonutProgressView incChart, expChart, goalChart, budChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
//        tv = findViewById(R.id.amnt);


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
        generateBudChart();
        generateIncomeChart();
        generateGoalChart();
        generateExpReport();

//        notification when the budget is reached
//        if(balancePercentage == 100f){
//            try {
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                r.play();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void switchIntent(Class<?> cls){
        Intent i = new Intent(this, cls);
        startActivity(i);
    }


    public void switchToGoal(View v){
        Intent i = new Intent(this, GoalHome.class);
        startActivity(i);
    }

    public void switchToBudget(View v){
        Intent i = new Intent(this, Budget.class);
        startActivity(i);
    }

    public void ModifyIncome(View v){
        Intent i = new Intent(this, Modify_Income.class);
        startActivity(i);
    }

    public void ModifyExpense(View v){
        Intent i = new Intent(this, Modify_Expense.class);
        startActivity(i);
    }

    public void logOut(){
        // remove the session and open login screen
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        moveToLogin();
    }

    private void moveToLogin() {
        Intent i = new Intent(MainActivity.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }



    public void generateIncomeChart(){
        float checks=0,
                childSupport=0,
                duesAndGrants=0,
                gifts=0,
                interests=0,
                lending=0,
                lottery=0,
                refunds=0,
                rental=0,
                sale=0,
                wage=0,
                other=0;

        incChart = findViewById(R.id.incDpvChart);
        Cursor res2 = DB.getIncomeData();
        float totalIncome = 0;
        while(res2.moveToNext()){
            totalIncome += res2.getFloat(3);
            if(res2.getString(1).equals("Checks, Coupons"))
                checks = res2.getFloat(3);
            else if(res2.getString(1).equals("Child Support"))
                childSupport = res2.getFloat(3);
            else if(res2.getString(1).equals("Dues & Grants"))
                duesAndGrants = res2.getFloat(3);
            else if(res2.getString(1).equals("Gifts"))
                gifts = res2.getFloat(3);
            else if(res2.getString(1).equals("Interests, Dividends"))
                interests = res2.getFloat(3);
            else if(res2.getString(1).equals("Lending, Renting"))
                lending = res2.getFloat(3);
            else if(res2.getString(1).equals("Lottery, Gambling"))
                lottery = res2.getFloat(3);
            else if(res2.getString(1).equals("Refunds (Tax,Purchase)"))
                refunds = res2.getFloat(3);
            else if(res2.getString(1).equals("Rental Income"))
                rental = res2.getFloat(3);
            else if(res2.getString(1).equals("Sale"))
                sale = res2.getFloat(3);
            else if(res2.getString(1).equals("Wage, Invoices"))
                wage = res2.getFloat(3);
            else if(res2.getString(1).equals("Other"))
                other = res2.getFloat(3);
        }

        DonutSection section1 = new DonutSection("Section 1 Name", Color.parseColor("#f44336"), (checks/totalIncome)*100);
        DonutSection section2 = new DonutSection("Section 2 Name", Color.parseColor("#4fb200"), (childSupport/totalIncome)*100);
        DonutSection section3 = new DonutSection("Section 3 Name", Color.parseColor("#3c2ada"), (duesAndGrants/totalIncome)*100);
        DonutSection section4 = new DonutSection("Section 4 Name", Color.parseColor("#FF6600"), (gifts/totalIncome)*100);
        DonutSection section5 = new DonutSection("Section 5 Name", Color.parseColor("#FFCC00"), (interests/totalIncome)*100);
        DonutSection section6 = new DonutSection("Section 6 Name", Color.parseColor("#663300"), (lending/totalIncome)*100);
        DonutSection section7 = new DonutSection("Section 7 Name", Color.parseColor("#003333"), (lottery/totalIncome)*100);
        DonutSection section8 = new DonutSection("Section 8 Name", Color.parseColor("#FF33FF"), (refunds/totalIncome)*100);
        DonutSection section9 = new DonutSection("Section 9 Name", Color.parseColor("#00FFFF"), (rental/totalIncome)*100);
        DonutSection section10 = new DonutSection("Section 10 Name", Color.parseColor("#9933FF"), (sale/totalIncome)*100);
        DonutSection section11 = new DonutSection("Section 11 Name", Color.parseColor("#CCFFFF"), (wage/totalIncome)*100);
        DonutSection section12 = new DonutSection("Section 12 Name", Color.parseColor("#7B68EE"), (other/totalIncome)*100);

        incChart.setCap(100f);
        ArrayList<DonutSection> ar = new ArrayList();
        ar.add(section1);
        ar.add(section2);
        ar.add(section3);
        ar.add(section4);
        ar.add(section5);
        ar.add(section6);
        ar.add(section7);
        ar.add(section8);
        ar.add(section9);
        ar.add(section10);
        ar.add(section11);
        ar.add(section12);

        incChart.submitData(ar);
    }

    public void generateBudChart() {
        float FoodAndDrinks = 0,
                Shopping = 0,
                Housing = 0,
                Transportation = 0,
                Vehicle = 0,
                Entertainment = 0,
                Communication = 0,
                Financial = 0,
                Investments = 0,
                Others = 0;

        budChart = findViewById(R.id.budDpvChart);
        Cursor res = DB.getBudgetData();
        float totalBudget = 0;

        while (res.moveToNext()) {
            totalBudget += res.getFloat(2);

            if (res.getString(4).equals("Shopping"))
                Shopping = res.getFloat(2);
            else if (res.getString(4).equals("Housing"))
                Housing = res.getFloat(2);
            else if (res.getString(4).equals("Food & Drinks"))
                FoodAndDrinks = res.getFloat(2);
            else if (res.getString(4).equals("Transportation"))
                Transportation = res.getFloat(2);
            else if (res.getString(4).equals("Vehicle"))
                Vehicle = res.getFloat(2);
            else if (res.getString(4).equals("Life & Entertainment"))
                Entertainment = res.getFloat(2);
            else if (res.getString(4).equals("Communication, PC"))
                Communication = res.getFloat(2);
            else if (res.getString(4).equals("Financial Expenses"))
                Financial = res.getFloat(2);
            else if (res.getString(4).equals("Investments"))
                Investments = res.getFloat(2);
            else if (res.getString(4).equals("Others"))
                Others = res.getFloat(2);
        }

        DonutSection section1 = new DonutSection("Section 1 Name", Color.parseColor("#f44336"), (FoodAndDrinks/totalBudget)*100);
        DonutSection section2 = new DonutSection("Section 2 Name", Color.parseColor("#4fb200"), (Housing/totalBudget)*100);
        DonutSection section3 = new DonutSection("Section 3 Name", Color.parseColor("#3c2ada"), (Transportation/totalBudget)*100);
        DonutSection section4 = new DonutSection("Section 4 Name", Color.parseColor("#FF6600"), (Vehicle/totalBudget)*100);
        DonutSection section5 = new DonutSection("Section 5 Name", Color.parseColor("#FFCC00"), (Entertainment/totalBudget)*100);
        DonutSection section6 = new DonutSection("Section 6 Name", Color.parseColor("#663300"), (Communication/totalBudget)*100);
        DonutSection section7 = new DonutSection("Section 7 Name", Color.parseColor("#003333"), (Financial/totalBudget)*100);
        DonutSection section8 = new DonutSection("Section 8 Name", Color.parseColor("#FF33FF"), (Investments/totalBudget)*100);
        DonutSection section9 = new DonutSection("Section 9 Name", Color.parseColor("#00FFFF"), (Others/totalBudget)*100);
        DonutSection section10 = new DonutSection("Section 10 Name", Color.parseColor("#9933FF"), (Shopping/totalBudget)*100);


        budChart.setCap(100f);
        ArrayList<DonutSection> budAr = new ArrayList();
        budAr.add(section1);
        budAr.add(section2);
        budAr.add(section3);
        budAr.add(section4);
        budAr.add(section5);
        budAr.add(section6);
        budAr.add(section7);
        budAr.add(section8);
        budAr.add(section9);
        budAr.add(section10);


        budChart.submitData(budAr);
    }

    public void generateGoalChart(){
        float HolidayTrip = 0,
                Education = 0,
                Emergency = 0,
                Health = 0,
                Party = 0,
                kidsSpoiling = 0,
                Charity = 0,
                home = 0,
                vehicle = 0,
                Other = 0;

        goalChart = findViewById(R.id.goalDpvChart);
        Cursor res3 = DB.getGoalData();
        float totalGoals = 0;

        while (res3.moveToNext()) {
            totalGoals += res3.getFloat(2);

            if (res3.getString(3).equals("Holiday Trip"))
                HolidayTrip = res3.getFloat(2);
            else if (res3.getString(3).equals("Education"))
                Education = res3.getFloat(2);
            else if (res3.getString(3).equals("Emergency Fund"))
                Emergency = res3.getFloat(2);
            else if (res3.getString(3).equals("Health Care"))
                Health = res3.getFloat(2);
            else if (res3.getString(3).equals("Party"))
                Party = res3.getFloat(2);
            else if (res3.getString(3).equals("Kids Spoiling"))
                kidsSpoiling = res3.getFloat(2);
            else if (res3.getString(3).equals("Charity"))
                Charity = res3.getFloat(2);
            else if (res3.getString(3).equals("New Home"))
                home = res3.getFloat(2);
            else if (res3.getString(3).equals("New Vehicle"))
                vehicle = res3.getFloat(2);
            else if (res3.getString(3).equals("Other"))
                Other = res3.getFloat(2);
        }

        DonutSection section1 = new DonutSection("Section 1 Name", Color.parseColor("#f44336"), (HolidayTrip/totalGoals)*100);
        DonutSection section2 = new DonutSection("Section 2 Name", Color.parseColor("#4fb200"), (Education/totalGoals)*100);
        DonutSection section3 = new DonutSection("Section 3 Name", Color.parseColor("#3c2ada"), (Emergency/totalGoals)*100);
        DonutSection section4 = new DonutSection("Section 4 Name", Color.parseColor("#FF6600"), (Health/totalGoals)*100);
        DonutSection section5 = new DonutSection("Section 5 Name", Color.parseColor("#FFCC00"), (Party/totalGoals)*100);
        DonutSection section6 = new DonutSection("Section 6 Name", Color.parseColor("#663300"), (kidsSpoiling/totalGoals)*100);
        DonutSection section7 = new DonutSection("Section 7 Name", Color.parseColor("#003333"), (Charity/totalGoals)*100);
        DonutSection section8 = new DonutSection("Section 8 Name", Color.parseColor("#663300"), (home/totalGoals)*100);
        DonutSection section9 = new DonutSection("Section 9 Name", Color.parseColor("#003333"), (vehicle/totalGoals)*100);
        DonutSection section10 = new DonutSection("Section 10 Name", Color.parseColor("#FF33FF"), (Other/totalGoals)*100);

        goalChart.setCap(100f);
        ArrayList<DonutSection> goalAr = new ArrayList();
        goalAr.add(section1);
        goalAr.add(section2);
        goalAr.add(section3);
        goalAr.add(section4);
        goalAr.add(section5);
        goalAr.add(section6);
        goalAr.add(section7);
        goalAr.add(section8);
        goalAr.add(section9);
        goalAr.add(section10);

        goalChart.submitData(goalAr);

    }

    public void generateExpReport(){
        float FoodAndDrinks = 0,
                Shopping = 0,
                Housing = 0,
                Transportation = 0,
                Vehicle = 0,
                Entertainment = 0,
                Communication = 0,
                Financial = 0,
                Investments = 0,
                Others = 0;

        expChart = findViewById(R.id.expDpvChart);
        Cursor res = DB.getExpenseData();
        float totalExpense = 0;

        while (res.moveToNext()) {
            totalExpense += res.getFloat(3);

            if (res.getString(1).equals("Shopping"))
                Shopping = res.getFloat(3);
            else if (res.getString(1).equals("Housing"))
                Housing = res.getFloat(3);
            else if (res.getString(1).equals("Food & Drinks"))
                FoodAndDrinks = res.getFloat(3);
            else if (res.getString(1).equals("Transportation"))
                Transportation = res.getFloat(3);
            else if (res.getString(1).equals("Vehicle"))
                Vehicle = res.getFloat(3);
            else if (res.getString(1).equals("Life & Entertainment"))
                Entertainment = res.getFloat(3);
            else if (res.getString(1).equals("Communication, PC"))
                Communication = res.getFloat(3);
            else if (res.getString(1).equals("Financial Expenses"))
                Financial = res.getFloat(3);
            else if (res.getString(1).equals("Investments"))
                Investments = res.getFloat(3);
            else if (res.getString(1).equals("Others"))
                Others = res.getFloat(3);
        }

        DonutSection section1 = new DonutSection("Section 1 Name", Color.parseColor("#f44336"), (FoodAndDrinks/totalExpense)*100);
        DonutSection section2 = new DonutSection("Section 2 Name", Color.parseColor("#4fb200"), (Housing/totalExpense)*100);
        DonutSection section3 = new DonutSection("Section 3 Name", Color.parseColor("#3c2ada"), (Transportation/totalExpense)*100);
        DonutSection section4 = new DonutSection("Section 4 Name", Color.parseColor("#FF6600"), (Vehicle/totalExpense)*100);
        DonutSection section5 = new DonutSection("Section 5 Name", Color.parseColor("#FFCC00"), (Entertainment/totalExpense)*100);
        DonutSection section6 = new DonutSection("Section 6 Name", Color.parseColor("#663300"), (Communication/totalExpense)*100);
        DonutSection section7 = new DonutSection("Section 7 Name", Color.parseColor("#003333"), (Financial/totalExpense)*100);
        DonutSection section8 = new DonutSection("Section 8 Name", Color.parseColor("#FF33FF"), (Investments/totalExpense)*100);
        DonutSection section9 = new DonutSection("Section 9 Name", Color.parseColor("#00FFFF"), (Others/totalExpense)*100);
        DonutSection section10 = new DonutSection("Section 10 Name", Color.parseColor("#9933FF"), (Shopping/totalExpense)*100);


        expChart.setCap(100f);
        ArrayList<DonutSection> ExpAr = new ArrayList();
        ExpAr.add(section1);
        ExpAr.add(section2);
        ExpAr.add(section3);
        ExpAr.add(section4);
        ExpAr.add(section5);
        ExpAr.add(section6);
        ExpAr.add(section7);
        ExpAr.add(section8);
        ExpAr.add(section9);
        ExpAr.add(section10);


        expChart.submitData(ExpAr);

    }
}



