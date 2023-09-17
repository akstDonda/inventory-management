package com.miniproject.inventorymanagement.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.AdminRegistrationActivity;
import com.miniproject.inventorymanagement.common.LoginActivity;

public class AddNewTransaction extends AppCompatActivity {

    ImageView btnhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_transaction);

        btnhome=findViewById(R.id.backbtnhomeat);

        //back home
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddNewTransaction.this, Home.class);
                startActivity(intent);
            }
        });
    }
}