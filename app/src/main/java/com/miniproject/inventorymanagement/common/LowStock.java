package com.miniproject.inventorymanagement.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class LowStock extends AppCompatActivity {
    DatabaseHandler dbHandler;
    SearchView searchLowStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_low_stock);

        searchLowStock=findViewById(R.id.lowStockSearchView);




        dbHandler = DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbHandler.refreshProducts();

        RecyclerView recyclerView = findViewById(R.id.lowStockRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(dbHandler.getProducts(), 10);
        recyclerView.setAdapter(adapter);
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                adapter.setProductList(dbHandler.getProducts());
                adapter.notifyDataSetChanged();
            }
        });

        searchLowStock.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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