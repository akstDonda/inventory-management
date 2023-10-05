package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.Category;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.firebase.Product;

import java.util.List;

public class ProductSelect extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_select);


        ListView productListView = findViewById(R.id.category_listview_main);
        final List<Product> cat = DatabaseHandler.getInstance().getProductsList();
        final String[] products = new String[cat.size()];
        for (int i = 0; i < cat.size(); i++) {
            products[i] = cat.get(i).getId();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        productListView.setAdapter(adapter);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProduct = products[position];
                // Pass the selected category back to the form activity
                Intent intent = new Intent();
                intent.putExtra("selectedProduct", selectedProduct);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}