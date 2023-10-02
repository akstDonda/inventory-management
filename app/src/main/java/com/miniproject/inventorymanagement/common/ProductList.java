package com.miniproject.inventorymanagement.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.admin.AddNewProduct;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class ProductList extends AppCompatActivity {
    FloatingActionButton add;
    DatabaseHandler dbhandler;
    SearchView productSearch;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        productSearch=findViewById(R.id.productSearchView);

        add=findViewById(R.id.addNewProductButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewProduct.class));
                finish();
            }
        });

        dbhandler=DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbhandler.refreshProducts();


        RecyclerView recyclerView=findViewById(R.id.product_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(dbhandler.getProducts());
        recyclerView.setAdapter(adapter);

        if (getIntent().hasExtra("filterCategory")) {
            String filterCategory = getIntent().getStringExtra("filterCategory");
            adapter.setCategoryFilter(filterCategory);
        }

        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                adapter.setProductList(dbhandler.getProductsList());
            }
        });

        productSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.setQuery(s);
                return true;
            }
        });


        
    }

}