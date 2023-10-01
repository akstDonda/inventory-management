package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.CategoryAdapter;
import com.miniproject.inventorymanagement.adapters.CategoryAdapterForDashboard;
import com.miniproject.inventorymanagement.firebase.Category;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        CategoryAdapterForDashboard adapter = new CategoryAdapterForDashboard(dbHandler.getCategories());

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CategoryAdapterForDashboard.OnItemClickListener() {
            @Override
            public void onItemClick(String categoryId) {
                Intent intent = new Intent(Dashboard.this, ProductList.class);
                intent.putExtra("filterCategory", categoryId);
                startActivity(intent);
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }
}