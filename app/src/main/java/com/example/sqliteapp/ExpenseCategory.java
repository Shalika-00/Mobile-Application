package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ExpenseCategory extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ExpenseModelClass> userList;
    ExpenseAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_category);

        initData();
        initRecyclerView();
    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new ExpenseModelClass(R.drawable.food,"Food & Drinks"));

        userList.add(new ExpenseModelClass(R.drawable.house,"Housing"));

        userList.add(new ExpenseModelClass(R.drawable.shopping,"Shopping"));

        userList.add(new ExpenseModelClass(R.drawable.transport,"Transportation"));

        userList.add(new ExpenseModelClass(R.drawable.vehicle,"Vehicle"));

        userList.add(new ExpenseModelClass(R.drawable.entertain,"Life & Entertainment"));

        userList.add(new ExpenseModelClass(R.drawable.pc,"Communication, PC"));

        userList.add(new ExpenseModelClass(R.drawable.coin,"Financial Expenses"));

        userList.add(new ExpenseModelClass(R.drawable.invest,"Investments"));

        userList.add(new ExpenseModelClass(R.drawable.invest,"Others"));



    }

    private void initRecyclerView(){
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adaptor= new ExpenseAdapter(userList);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }

    public void backToExpense(View v){
        Intent i = new Intent(this, Expense.class);
        startActivity(i);
    }
}