package com.miniproject.inventorymanagement.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class AddNewProduct extends AppCompatActivity {

    ImageView imgbtnback;
    DatabaseHandler dbHandler;

    String productName, productId, productDescription;
    EditText productNameText, productIdText, productDescriptionText, productPriceText;
    int normalProductSellingPrice, normalProductBuyingPrice;
    Button addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        this.setTitle("Product Add");

        imgbtnback=findViewById(R.id.backbtnhome);

        dbHandler=DatabaseHandler.getInstance();

        productNameText = findViewById(R.id.edt_productadd_productName);
        productDescriptionText = findViewById(R.id.edt_productadd_productdec);
        productIdText = findViewById(R.id.edt_productadd_productId);
        productPriceText = findViewById(R.id.edt_productadd_productrate);

        addProductButton = findViewById(R.id.btn_product_submit);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productName = productNameText.getText().toString();
                productId = productIdText.getText().toString();
                productDescription = productDescriptionText.getText().toString();
                normalProductBuyingPrice = Integer.parseInt(productPriceText.getText().toString());
                normalProductSellingPrice = Integer.parseInt(productPriceText.getText().toString());
                
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
}