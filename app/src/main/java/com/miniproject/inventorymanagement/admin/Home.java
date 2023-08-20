package com.miniproject.inventorymanagement.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.common.LoginActivity;
import com.miniproject.inventorymanagement.R;

public class Home extends AppCompatActivity {
    Button btn,addremove,add,remove;
    CardView cardAddProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btn=findViewById(R.id.btn_admin_logOut);
        addremove=findViewById(R.id.btn_add_remove);
        add=findViewById(R.id.btn_add);
        remove=findViewById(R.id.btn_remove);
        cardAddProducts=findViewById(R.id.card_addproducts);


        //cardView
        cardAddProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddProducts.class));

            }
        });



        //product add remove btn
        addremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentVisibility = add.getVisibility();
                if (currentVisibility == View.VISIBLE) {
                    add.setVisibility(View.GONE);
                    remove.setVisibility(View.GONE);
                }
                else {
                    add.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.VISIBLE);
                }
            }
        });

        //logout btn
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this, LoginActivity.class));
                finish();
            }
        });
    }
}