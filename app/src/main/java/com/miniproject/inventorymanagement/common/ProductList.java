package com.miniproject.inventorymanagement.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.admin.AddProductsDetaileAdd;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class ProductList extends AppCompatActivity {
    Button add;
    DatabaseHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


//        add=findViewById(R.id.iconButton_add_product);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), AddProductsDetaileAdd.class));
//            }
//        });
        dbhandler=DatabaseHandler.getInstance();

        dbhandler.addProduct("a","pen","black pen",4,5);
        dbhandler.addProduct("b","nothing","white pen",40000,50000);


        RecyclerView recyclerView=findViewById(R.id.product_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(dbhandler.getProducts());
        recyclerView.setAdapter(adapter);
    }

}