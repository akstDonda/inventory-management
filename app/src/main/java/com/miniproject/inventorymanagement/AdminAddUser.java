package com.miniproject.inventorymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class AdminAddUser extends AppCompatActivity {
    Button addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);
        addUser = findViewById(R.id.btn_add_user);
    }
}