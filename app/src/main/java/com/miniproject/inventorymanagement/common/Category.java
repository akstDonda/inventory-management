package com.miniproject.inventorymanagement.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.CategoryAdapter;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.admin.AddNewCategory;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class Category extends AppCompatActivity {

    FloatingActionButton addNewCategory;
    DatabaseHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        addNewCategory=findViewById(R.id.addNewCategoryButton);

        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewCategory.class));
            }
        });


        dbhandler= DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbhandler.refreshCategories();


        RecyclerView recyclerView=findViewById(R.id.Category_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CategoryAdapter adapter = new CategoryAdapter(dbhandler.getCategories());

        recyclerView.setAdapter(adapter);
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //TODO:
               //adapter.setCategory(dbhandler.getCategoriesList());

            }
        });
    }
}