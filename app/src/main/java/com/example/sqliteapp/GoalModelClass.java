package com.example.sqliteapp;

public class GoalModelClass {
    private int imageview1;
    private String text1;

    GoalModelClass(int imageview1, String text1) {
        this.imageview1 = imageview1;
        this.text1 = text1;
    }

    public int getImageview1() {
        return imageview1;
    }

    public String getText1() {
        return text1;
    }
}
