package com.miniproject.inventorymanagement.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.CategoryAdapter;
import com.miniproject.inventorymanagement.admin.AddNewCategory;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class Category extends AppCompatActivity {

    SearchView categorySearchView;
    FloatingActionButton addNewCategory;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        addNewCategory = findViewById(R.id.addNewCategoryButton);
        categorySearchView = findViewById(R.id.categorySearchView);

        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewCategory.class));
                finish();
            }
        });


        dbHandler = DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbHandler.refreshCategories();


        RecyclerView recyclerView = findViewById(R.id.Category_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CategoryAdapter adapter = new CategoryAdapter(dbHandler.getCategories());

        recyclerView.setAdapter(adapter);
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //TODO:
                //adapter.setCategory(dbhandler.getCategoriesList());

            }
        });

        //query
        categorySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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