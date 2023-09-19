package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.miniproject.inventorymanagement.R;

public class UserLogin extends AppCompatActivity {

    ImageButton elg_back_btn;
    TextView elg_reggt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        elg_back_btn=findViewById(R.id.elogin_back_btn);
        elg_reggt=findViewById(R.id.eopenreg);

        // back to login and go to registration of employee
        elg_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserLogin.this,UserTypeActivity.class);
                startActivity(i);
            }
        });
        elg_reggt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserLogin.this,UserRegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}