package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CategoryIncome extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initData();
        initRecyclerView();
    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new ModelClass(R.drawable.check_book,"Checks, Coupons"));

        userList.add(new ModelClass(R.drawable.romper,"Child Support"));

        userList.add(new ModelClass(R.drawable.dues,"Dues & Grants"));

        userList.add(new ModelClass(R.drawable.gifts,"Gifts"));

        userList.add(new ModelClass(R.drawable.interests,"Interests, Dividends"));

        userList.add(new ModelClass(R.drawable.rent,"Lending, Renting"));

        userList.add(new ModelClass(R.drawable.dice,"Lottery, Gambling"));

        userList.add(new ModelClass(R.drawable.refund,"Refunds (Tax,Purchase)"));

        userList.add(new ModelClass(R.drawable.rent_income,"Rental Income"));

        userList.add(new ModelClass(R.drawable.sales,"Sale"));

        userList.add(new ModelClass(R.drawable.wages,"Wage, Invoices"));

        userList.add(new ModelClass(R.drawable.other,"Other"));


    }

    private void initRecyclerView(){
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adaptor= new Adaptor(userList);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }

    public void backToIncome(View v){
        Intent i = new Intent(this, Income.class);
        startActivity(i);
    }
}