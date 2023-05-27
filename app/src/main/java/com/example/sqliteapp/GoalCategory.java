package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GoalCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<GoalModelClass> userList;
    GoalAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_activity_category);

        initData();
        initRecyclerView();
    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new GoalModelClass(R.drawable.new_house,"New Home"));

        userList.add(new GoalModelClass(R.drawable.new_vehicle,"New Vehicle"));

        userList.add(new GoalModelClass(R.drawable.holiday_trip,"Holiday Trip"));

        userList.add(new GoalModelClass(R.drawable.education,"Education"));

        userList.add(new GoalModelClass(R.drawable.emergency_fund,"Emergency Fund"));

        userList.add(new GoalModelClass(R.drawable.health_care,"Health Care"));

        userList.add(new GoalModelClass(R.drawable.party,"Party"));

        userList.add(new GoalModelClass(R.drawable.kids_spoiling,"Kids Spoiling"));

        userList.add(new GoalModelClass(R.drawable.charity,"Charity"));

        userList.add(new GoalModelClass(R.drawable.other,"Other"));



    }

    private void initRecyclerView(){
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adaptor= new GoalAdaptor(userList);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }
}