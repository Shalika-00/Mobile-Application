package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Wallet.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE BudgetDetails(BudgetName TEXT PRIMARY KEY, Date TEXT, Amount NUMERIC, Currency TEXT, Category TEXT, almostComplete NUMERIC, overspent NUMERIC, startDate TEXT, currentAmount NUMERIC)");
        DB.execSQL("CREATE TABLE IncomeDetails(incomeID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, category TEXT, date TEXT, amount REAL)");
        DB.execSQL("CREATE TABLE GoalData(name TEXT PRIMARY KEY, estimated_date TEXT, gaol_amount REAL, category TEXT, goal_description TEXT, add_savings REAL, today_date TEXT)");
        DB.execSQL("CREATE TABLE UserDetails(Name TEXT , Currency TEXT, PIN NUMERIC PRIMARY KEY)");
        DB.execSQL("CREATE TABLE ExpenseDetails(expenseID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, category TEXT, date TEXT, amount REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE if exists BudgetDetails");
        DB.execSQL("DROP TABLE if exists IncomeDetails");
        DB.execSQL("DROP TABLE if exists GoalData");
        DB.execSQL("DROP TABLE if exists UserDetails");
        DB.execSQL("DROP TABLE if exists ExpenseDetails");
    }

    public boolean insertBudgetData(String BudName, String date, float amount, String currency, String category, int almostComplete, int overspent, String startDate, float currentAmount){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BudgetName", BudName);
        contentValues.put("Date", date);
        contentValues.put("Amount", amount);
        contentValues.put("Currency", currency);
        contentValues.put("Category", category);
        contentValues.put("almostComplete", almostComplete);
        contentValues.put("overspent", overspent);
        contentValues.put("startDate", startDate);
        contentValues.put("currentAmount", currentAmount);

        if(BudName == null || category == null || amount == 0){
            return false;
        }
        else{
            long result = DB.insert("BudgetDetails", null, contentValues);
            if(result == -1)
                return false;
            else
                return true;
        }

    }

    public Cursor getBudgetData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM BudgetDetails", null);
        return cursor;
    }

    public Cursor getSingleBudgetData(String category){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM BudgetDetails WHERE Category = ?", new String[] {category});
        return cursor;
    }

    public Cursor getSingleBudgetDataUsingCategory(String category){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM BudgetDetails WHERE Category = ?", new String[] {category});
        return cursor;
    }

    public boolean deleteBudgetData(String category){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM BudgetDetails WHERE Category = ?", new String[] {category});
        if(cursor.getCount() > 0){
            long result = DB.delete("BudgetDetails","Category=?", new String[] {category});
            if(result == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }

    public boolean updateBudgetCurrBalance(float newCurrAmt, String category){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("currentAmount", newCurrAmt);

        Cursor cursor = DB.rawQuery("SELECT * FROM BudgetDetails WHERE  Category= ?", new String[] {category});
        if(cursor.getCount() > 0){
            long result = DB.update("BudgetDetails", contentValues, "Category=?", new String[] {category});
            if(result == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }

    public boolean updateBudget(String category, float amt, String compDate, String BName){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BudgetName", BName);
        contentValues.put("Amount", amt);
        contentValues.put("Date", compDate);

        Cursor cursor = DB.rawQuery("SELECT * FROM BudgetDetails WHERE Category = ?", new String[] {category});
        if(cursor.getCount() > 0){
            long result = DB.update("BudgetDetails", contentValues, "Category=?", new String[] {category});
            if(result == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }


    public boolean insertIncomeData(String category, String date, float amount){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", category);
        contentValues.put("date", date);
        contentValues.put("amount", amount);

        long result = DB.insert("IncomeDetails", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean updateIncomeData(String itemID, String category, String date, float amount){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("ID", itemID);
        contentValues.put("Category", category);
        contentValues.put("Date", date);
        contentValues.put("Amount", amount);

        String query  = "SELECT * FROM IncomeDetails WHERE incomeID = " + itemID;
        Cursor cursor = DB.rawQuery(query,null);
        if(cursor.getCount() > 0){
            long result = DB.update("IncomeDetails", contentValues, "incomeID=?", new String[]{itemID} );
            if(result == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteIncomeData(int itemID){
        SQLiteDatabase DB = this.getWritableDatabase();

        String query  = "SELECT * FROM IncomeDetails WHERE incomeID = " + itemID;
        Cursor cursor = DB.rawQuery(query, null);
        if(cursor.getCount() > 0){
            long result = DB.delete("IncomeDetails","incomeID= "+itemID, null);
            if(result == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }

    public Cursor getIncomeData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM IncomeDetails", null);
        return cursor;
    }

    public Cursor getSingleIncomeDataUsingIncomeID(int id){
        SQLiteDatabase DB = this.getWritableDatabase();
        String query  = "SELECT * FROM IncomeDetails WHERE incomeID = " + id;
        Cursor cursor = DB.rawQuery(query, null);
        return cursor;
    }




    public Cursor getGoalData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM GoalData", null);
        return cursor;
    }

    public Cursor getSingleGoalData(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM GoalData WHERE name = ?", new String[] {name});
        return cursor;
    }

    public boolean insertGoalData(String name, String estimated_date, float gaol_amount, String category, String goal_description, float add_savings, String today_date ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("estimated_date", estimated_date);
        contentValues.put("gaol_amount", gaol_amount);
        contentValues.put("category", category);
        contentValues.put("goal_description", goal_description);
        contentValues.put("add_savings", add_savings);
        contentValues.put("today_date", today_date);


        long result = DB.insert("GoalData", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateGoalData(String name, float gaol_amount, String goal_description, float add_savings ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("estimated_date", estimated_date);
        contentValues.put("gaol_amount", gaol_amount);
//        contentValues.put("category", category);
        contentValues.put("goal_description", goal_description);
        contentValues.put("add_savings", add_savings);
        Cursor cursor = DB.rawQuery("Select * from GoalData where name = ?", new String[]{name});
        if(cursor.getCount()>0) {
            long result = DB.update("GoalData", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }


    }

    public boolean deleteGoalData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from GoalData where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("GoalData", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }



    public Cursor getExpenseData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM ExpenseDetails", null);
        return cursor;
    }

    public boolean insertExpenseData(String category, String date, float amount){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", category);
        contentValues.put("date", date);
        contentValues.put("amount", amount);

        long result = DB.insert("ExpenseDetails", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }


    public boolean updateExpense(String itemID, String category, String date, float amount){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("expenseID", itemID);
        contentValues.put("Category", category);
        contentValues.put("Date", date);
        contentValues.put("Amount", amount);

        String query  = "SELECT * FROM ExpenseDetails WHERE expenseID = " + itemID;
        Cursor cursor = DB.rawQuery(query,null);
        if(cursor.getCount() > 0){
            long result = DB.update("ExpenseDetails", contentValues, "expenseID=?",new String[]{itemID});
            if(result == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteExpenseData(int itemID){
        SQLiteDatabase DB = this.getWritableDatabase();

        String query  = "SELECT * FROM ExpenseDetails WHERE expenseID = " + itemID;
        Cursor cursor = DB.rawQuery(query, null);
        if(cursor.getCount() > 0){
            long result = DB.delete("ExpenseDetails","expenseID= "+itemID, null);
            if(result == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }

    public Cursor getSingleExpenseDataUsingIncomeID(int id){
        SQLiteDatabase DB = this.getWritableDatabase();
        String query  = "SELECT * FROM ExpenseDetails WHERE expenseID = " + id;
        Cursor cursor = DB.rawQuery(query, null);
        return cursor;
    }


    public boolean insertUserData(String name, String currency, int PIN){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Currency", currency);
        contentValues.put("PIN", PIN);

        long result = DB.insert("UserDetails", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getUserData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM UserDetails", null);
        return cursor;
    }
}
