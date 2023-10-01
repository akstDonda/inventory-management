package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.admin.AddNewProduct;
import com.miniproject.inventorymanagement.firebase.Category;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.List;

public class CategorySelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        ListView categoryListView = findViewById(R.id.list_view_main);
        final List<Category> cat = DatabaseHandler.getInstance().getCategoriesList();
        final String[] categories = new String[cat.size()];
        for (int i = 0; i < cat.size(); i++) {
            categories[i] = cat.get(i).getId();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoryListView.setAdapter(adapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                // Pass the selected category back to the form activity
                Intent intent = new Intent();
                intent.putExtra("selectedCategory", selectedCategory);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
