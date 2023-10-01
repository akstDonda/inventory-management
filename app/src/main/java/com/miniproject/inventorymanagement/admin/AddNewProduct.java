package com.miniproject.inventorymanagement.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.AdminRegistrationActivity;
import com.miniproject.inventorymanagement.common.CategorySelect;
import com.miniproject.inventorymanagement.common.ProductList;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.HashMap;
import java.util.Map;

public class AddNewProduct extends AppCompatActivity {
    private final int CATEGORY_SELECT_REQUEST_CODE = 1;

    ImageView imgbtnback;
    DatabaseHandler dbHandler;
    TextInputEditText textInputEditTextProductName,textInputEditTextCategoryName;
    String productName, productId, productDescription, categoryName;
    TextInputLayout productNameText, categoryNameText ,productIdText, productDescriptionText, normalBuyPriceText, normalSellPriceText;
    int normalProductSellingPrice, normalProductBuyingPrice;
    Button addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        this.setTitle("Product Add");

        imgbtnback=findViewById(R.id.backbtnhome);
        String categoryId = null;
        if (getIntent().getExtras() != null) {
            categoryId = getIntent().getExtras().getString("selectedCategory");
            Toast.makeText(this, categoryId, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "hehe :)", Toast.LENGTH_SHORT).show();
        }

        dbHandler=DatabaseHandler.getInstance();
        textInputEditTextProductName=findViewById(R.id.textInputEditTextProductName);
        textInputEditTextCategoryName=findViewById(R.id.textInputEditTextCategoryName);
        textInputEditTextCategoryName.setText(categoryId);
        textInputEditTextCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNewProduct.this, "Select Category", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewProduct.this, CategorySelect.class);
                startActivityForResult(intent, CATEGORY_SELECT_REQUEST_CODE);
            }
        });
//        textInputEditTextProductName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddNewProduct.this, "Select Product", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AddNewProduct.this, onClickProduct.class);
//                startActivity(intent);
//            }
//        });


        productNameText = findViewById(R.id.addProductName);
        categoryNameText=findViewById(R.id.categoryName);
        productDescriptionText = findViewById(R.id.addProductDescription);
        productIdText = findViewById(R.id.addProductId);
        normalBuyPriceText = findViewById(R.id.addProductNormalBuyPrice);
        normalSellPriceText = findViewById(R.id.addProductNormalSellPrice);
//        EditText editText=productNameText.getEditText();

        addProductButton = findViewById(R.id.btn_sbmt);





        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                try {
                    productName = productNameText.getEditText().getText().toString();
                    productId = productIdText.getEditText().getText().toString();
                    productDescription = productDescriptionText.getEditText().getText().toString();
                    normalProductBuyingPrice = Integer.parseInt(normalBuyPriceText.getEditText().getText().toString());
                    normalProductSellingPrice = Integer.parseInt(normalSellPriceText.getEditText().getText().toString());
                    categoryName=categoryNameText.getEditText().getText().toString();

                    //createAndAddCategory
                    dbHandler.addProduct(productId, productName, productDescription, normalProductBuyingPrice, normalProductSellingPrice,categoryName);
                    Toast.makeText(AddNewProduct.this, dbHandler.getProducts().get(productId).getName(), Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(AddNewProduct.this, ProductList.class);
                    startActivity(intent);
                    finish();
//                }
//                catch (Exception e) {
                    Toast.makeText(AddNewProduct.this, "Fill Buying, Selling Price", Toast.LENGTH_SHORT).show();
//                }


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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CATEGORY_SELECT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String selectedCategory = data.getStringExtra("selectedCategory");
                // Update the category text field with the selected category
                textInputEditTextCategoryName.setText(selectedCategory);
            }
        }
    }
}