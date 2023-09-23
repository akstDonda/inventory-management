package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.R;

public class MainSplash extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //user is already create so throw direct mainActivity
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), Loading.class));
                    finish();
                }
                else {
                    Intent intent = new Intent(MainSplash.this, UserTypeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },400);
    }
}