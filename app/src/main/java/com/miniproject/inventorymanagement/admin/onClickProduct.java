package com.miniproject.inventorymanagement.admin;

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
import com.miniproject.inventorymanagement.adapters.ProductAdapter2;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class onClickProduct extends AppCompatActivity {

    DatabaseHandler dbhandler;
    SearchView productSearch;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_product);

        dbhandler=DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbhandler.refreshProducts();

        RecyclerView recyclerView=findViewById(R.id.product_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter2(dbhandler.getProducts());
        recyclerView.setAdapter(adapter);
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