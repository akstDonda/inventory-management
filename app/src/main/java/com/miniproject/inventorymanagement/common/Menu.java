package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.admin.Home;

public class Menu extends AppCompatActivity {

    TextView txtlout;

    ImageView imgback;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imgback=findViewById(R.id.homeback);
        txtlout=findViewById(R.id.btn_admin_logOut);

        //logout
        txtlout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Menu.this, LoginActivity.class));
            finish();
        });

        //back button
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,Home.class);
                startActivity(intent);
            }
        });



    }
}