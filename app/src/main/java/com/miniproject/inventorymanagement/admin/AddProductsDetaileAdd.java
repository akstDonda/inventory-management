package com.miniproject.inventorymanagement.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.miniproject.inventorymanagement.R;

public class AddProductsDetaileAdd extends AppCompatActivity {

    ImageView imgbtnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_detaile_add);
        this.setTitle("Product Add");

        imgbtnback=findViewById(R.id.backbtnhome);

        // back to home
        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddProductsDetaileAdd.this,Home.class);
                startActivity(intent);
            }
        });


    }
}