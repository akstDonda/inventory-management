package com.miniproject.inventorymanagement.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.Category;
import com.miniproject.inventorymanagement.common.ProductList;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.HashMap;
import java.util.Map;

public class AddNewCategory extends AppCompatActivity {

    ImageView imgbtnback;
    DatabaseHandler dbHandler;

    String categoryName, categoryId;
    TextInputLayout categoryNameText, categoryIdText;

    Button addCategoryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);

        this.setTitle("Category Add");

        imgbtnback=findViewById(R.id.backbtnhomeat);


        dbHandler=DatabaseHandler.getInstance();

        categoryIdText = findViewById(R.id.addCategoryId);
        categoryNameText = findViewById(R.id.addCategoryName);
        addCategoryButton = findViewById(R.id.addCategoryButton);


        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryId = categoryIdText.getEditText().getText().toString();
                categoryName = categoryNameText.getEditText().getText().toString();


                //createAndAddCategory
                dbHandler.createAndAddCategory(categoryId, categoryName,null);
                Toast.makeText(AddNewCategory.this, dbHandler.getCategories().get(categoryId).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddNewCategory.this, Category.class);
                startActivity(intent);
                finish();

            }
        });

        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddNewCategory.this,Home.class);
                startActivity(intent);
                finish();

            }
        });

    }



    public void insertdata(){

        Map<String, Object> category_detailes = new HashMap<>();
        category_detailes.put("category_id",categoryId);
        category_detailes.put("category_name",categoryName);



    }
}