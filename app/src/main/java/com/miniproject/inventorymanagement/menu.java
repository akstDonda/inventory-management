package com.miniproject.inventorymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.admin.Home;
import com.miniproject.inventorymanagement.common.LoginActivity;

public class menu extends AppCompatActivity {

    TextView txtlout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtlout=findViewById(R.id.btn_admin_logOut);

        //logout
        txtlout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(menu.this, LoginActivity.class));
                finish();
            }
        });



    }
}