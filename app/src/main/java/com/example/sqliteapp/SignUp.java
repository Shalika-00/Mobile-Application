package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    EditText Uname, Curr, Pin, ConfPin;
    String username, currency;
    int pin, confpin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Uname = findViewById(R.id.Uname);
        Curr = findViewById(R.id.currency);
        Pin = findViewById(R.id.enterPin);
        ConfPin = findViewById(R.id.confirmPin);

        DB = new DBHelper(this);
    }

    public void signUp(View v){
        username = Uname.getText().toString();
        currency = Curr.getText().toString();
        pin = Integer.parseInt(Pin.getText().toString());
        confpin =  Integer.parseInt(ConfPin.getText().toString());

        if(pin == confpin){
            boolean checkInsertedData = DB.insertUserData(username, currency, pin);
            Log.d("boolVal", "val: " + checkInsertedData);

            if(checkInsertedData){
                Toast.makeText(SignUp.this, "New user created", Toast.LENGTH_LONG).show();
                Uname.setText(null);
                Curr.setText(null);
                Pin.setText(null);
                ConfPin.setText(null);
                Intent i = new Intent(this, Login.class);
                startActivity(i);
            }
            else
                Toast.makeText(SignUp.this, "user not created", Toast.LENGTH_LONG).show();
        }
    }

    public void switchToLogIn(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}