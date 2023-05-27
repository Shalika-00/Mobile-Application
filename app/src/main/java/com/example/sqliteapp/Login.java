package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText PIN;
    Button login;
    int pinNo, count = 1;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PIN = findViewById(R.id.pin);

        login = findViewById(R.id.logInBtn);
        DB = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();

    }

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(Login.this);
        int userId = sessionManagement.getSession();

        if(userId != -1){
            //If id logged in, move to main activity
            movetoMainActivity();
        }
        else{
            //do nothing
        }
    }

    private void movetoMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



    public void login(View v){
        String pin = PIN.getText().toString();
        Log.d("pin", "pin: " + pin );
        Cursor res = DB.getUserData();
        if(res.getCount() == 0 || pin == null){
            Toast.makeText(Login.this, "Please sign up", Toast.LENGTH_LONG).show();
        }
        else{
            while(res.moveToNext()){
                pinNo = Integer.parseInt(pin);
                int existingPin = res.getInt(2);
                if(pinNo == existingPin){
                    PIN.setText(null);

                    //log to the app and save the session of the user
                    //move to main activity
                    User user = new User(12, "Ranuja");

                    SessionManagement sessionManagement = new SessionManagement(Login.this);
                    sessionManagement.saveSession(user);


                    //move to success page
                    movetoMainActivity();
                }
                else{
                    count++;
                    if(count > 3){
                        login.setEnabled(false);
                    }
                    Toast.makeText(Login.this, "Incorrect PIN", Toast.LENGTH_LONG).show();
                    PIN.setText(null);
                }

            }
        }
    }

    public void switchToSignUp(View v){
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

}