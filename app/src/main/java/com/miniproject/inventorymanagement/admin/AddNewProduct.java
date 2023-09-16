package com.miniproject.inventorymanagement.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.HashMap;
import java.util.Map;

public class AddNewProduct extends AppCompatActivity {

    ImageView imgbtnback;
    DatabaseHandler dbHandler;

    String productName, productId, productDescription;
    TextInputLayout productNameText, productIdText, productDescriptionText, normalBuyPriceText, normalSellPriceText;
    int normalProductSellingPrice, normalProductBuyingPrice;
    Button addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        this.setTitle("Product Add");

        imgbtnback=findViewById(R.id.backbtnhome);

        dbHandler=DatabaseHandler.getInstance();

        productNameText = findViewById(R.id.addProductName);
        productDescriptionText = findViewById(R.id.addProductDescription);
        productIdText = findViewById(R.id.addProductId);
        normalBuyPriceText = findViewById(R.id.addProductNormalBuyPrice);
        normalSellPriceText = findViewById(R.id.addProductNormalSellPrice);

        addProductButton = findViewById(R.id.btn_sbmt);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productName = productNameText.getEditText().getText().toString();
                productId = productIdText.getEditText().getText().toString();
                productDescription = productDescriptionText.getEditText().getText().toString();
                normalProductBuyingPrice = Integer.parseInt(normalBuyPriceText.getEditText().getText().toString());
                normalProductSellingPrice = Integer.parseInt(normalSellPriceText.getEditText().getText().toString());
                
                dbHandler.addProduct(productId, productName, productDescription, normalProductBuyingPrice, normalProductSellingPrice);
                Toast.makeText(AddNewProduct.this, dbHandler.getProducts().get(productId).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        // back to home
        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddNewProduct.this,Home.class);
                startActivity(intent);
                //dbHandler.addProduct();
            }
        });




    }

    public void insertdata(){

        Map<String, Object> product_detailes = new HashMap<>();
        product_detailes.put("product_id",productId);
        product_detailes.put("product_name",productName);



    }
}