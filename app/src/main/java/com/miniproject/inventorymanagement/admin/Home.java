package com.miniproject.inventorymanagement.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.LoginActivity;
import com.miniproject.inventorymanagement.menu;


public class Home extends AppCompatActivity {
    Button addremove,add,remove;
    CardView cardAddProducts;

    ImageView hmenu,hback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home); 

        addremove=findViewById(R.id.btn_add_remove);
        add=findViewById(R.id.btn_add);
        remove=findViewById(R.id.btn_remove);
        cardAddProducts=findViewById(R.id.card_addproduct);
        hmenu=findViewById(R.id.homemenu);
        hback=findViewById(R.id.homeback);



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


        //menu open
        hmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this, menu.class);
                startActivity(intent);
            }
        });

        //back to login
        hback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}