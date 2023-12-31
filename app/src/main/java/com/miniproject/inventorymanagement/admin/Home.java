package com.miniproject.inventorymanagement.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.miniproject.inventorymanagement.common.Category;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.Dashboard;
import com.miniproject.inventorymanagement.common.LowStock;
import com.miniproject.inventorymanagement.common.ProductList;
import com.miniproject.inventorymanagement.common.Settings;
import com.miniproject.inventorymanagement.common.Transactions;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.firebase.User;

public class Home extends AppCompatActivity {
    Button addremove, add, remove;
    CardView productsCardView, transactionsCardView, settingsCardView, dashboardCardView, lowStockCardView, addEmployeeCardView,categoryCardView;

    ImageView hmenu, hback;
    TextView greet;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        
        
        greet = findViewById(R.id.lblhome);
        User u = DatabaseHandler.getInstance().getUser();
        if (u != null) {
            if (u.isAdmin()) {
                greet.setText("Welcome, " + u.getDisplayName() + " (admin)!");
            }
            else {
            greet.setText("Welcome, " + u.getDisplayName() + "!");}
        }
        greet.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        greet.setTypeface(ResourcesCompat.getFont(this, R.font.youngserif_regular));


        addremove = findViewById(R.id.btn_add_remove);
        add = findViewById(R.id.btn_add_transaction);
        remove = findViewById(R.id.btn_add_product);
        
//        hmenu = findViewById(R.id.homemenu);
//        hback = findViewById(R.id.homeback);

        // Card Views
        productsCardView = findViewById(R.id.card_addproduct);
        transactionsCardView = findViewById(R.id.card_transaction);
        settingsCardView = findViewById(R.id.card_settings);
        dashboardCardView = findViewById(R.id.card_dashboard);
        lowStockCardView = findViewById(R.id.card_lowstock);
        addEmployeeCardView = findViewById(R.id.card_add_employee);
        categoryCardView=findViewById(R.id.card_addcategory);

        // Click Handlers
        productsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProductList.class));
            }
        });
        dashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });
        lowStockCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LowStock.class));
            }
        });
        addEmployeeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(Home.this, ApproveUser.class);
               startActivity(intent);

            }
        });
        settingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });
        transactionsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Transactions.class));
            }
        });
        categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Category.class));
            }
        });

        // product add remove btn
        addremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentVisibility = add.getVisibility();
                if (currentVisibility == View.VISIBLE) {
                    add.setVisibility(View.GONE);
                    remove.setVisibility(View.GONE);
                } else {
                    add.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.VISIBLE);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AddNewTransaction.class);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AddNewProduct.class);
                startActivity(intent);
            }
        });

//        // menu open
//        hmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home.this, Menu.class);
//                startActivity(intent);
//            }
//        });
//
//        // back to login
//        hback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }

}