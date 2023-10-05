package com.miniproject.inventorymanagement.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.CategoryAdapterForDashboard;
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

        //query
        SearchView categorySearchView_dash = findViewById(R.id.categorySearchView_dash);
        categorySearchView_dash.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        Button logOutUser = findViewById(R.id.logOutUser);
        if (dbHandler.getUser().isAdmin()) {
            logOutUser.setVisibility(View.GONE);
        } else {
            logOutUser.setVisibility(View.VISIBLE);
        }

        logOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, UserTypeActivity.class));
            }
        });


    }
}