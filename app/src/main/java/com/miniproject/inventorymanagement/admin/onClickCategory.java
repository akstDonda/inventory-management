package com.miniproject.inventorymanagement.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.CategoryAdapter;
import com.miniproject.inventorymanagement.adapters.CategoryAdapter2;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class onClickCategory extends AppCompatActivity {
    DatabaseHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_category);

        dbhandler= DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbhandler.refreshCategories();


        RecyclerView recyclerView=findViewById(R.id.Category_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CategoryAdapter adapter2 = new CategoryAdapter2(dbhandler.getCategories());

        recyclerView.setAdapter(adapter2);
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //TODO:
                //adapter.setCategory(dbhandler.getCategoriesList());

            }
        });
    }
}