package com.miniproject.inventorymanagement.common;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miniproject.inventorymanagement.R;

public class UserTypeActivity extends AppCompatActivity {
    FloatingActionButton adminLoginButton, userLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_usertype);
        adminLoginButton = findViewById(R.id.floating_action_button_admin);
        userLoginButton = findViewById(R.id.floating_action_button_user);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserTypeActivity.this, LoginActivity.class);
                intent.putExtra("isAdmin", true);
                startActivity(intent);
            }
        });

        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserTypeActivity.this, LoginActivity.class);
                intent.putExtra("isAdmin", false);
                startActivity(intent);
            }
        });

    }

}