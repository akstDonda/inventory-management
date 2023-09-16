package com.miniproject.inventorymanagement.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.admin.AddNewProduct;
import com.miniproject.inventorymanagement.admin.Home;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.firebase.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductList extends AppCompatActivity {
    Button add;
    ImageView hmback,searchicon;
    DatabaseHandler dbhandler;
    SearchView searchView;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        add=findViewById(R.id.some1);
        hmback=findViewById(R.id.protohomeback);
        searchicon=findViewById(R.id.shi);
        searchView=findViewById(R.id.simpleSearchView);

        // back to home
        hmback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductList.this, Home.class);
                startActivity(intent);
            }
        });

        // search button click
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cv = searchView.getVisibility();
                if (cv == View.VISIBLE) {
                    searchicon.setVisibility(View.GONE);
                } else {
                    searchView.setVisibility(View.VISIBLE);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewProduct.class));
            }
        });
        dbhandler=DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbhandler.refreshProducts();


        RecyclerView recyclerView=findViewById(R.id.product_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(dbhandler.getProducts());
        recyclerView.setAdapter(adapter);
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                adapter.setProductList(dbhandler.getProducts());
                adapter.notifyDataSetChanged();
            }
        });
    }

}