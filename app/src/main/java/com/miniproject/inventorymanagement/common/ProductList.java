package com.miniproject.inventorymanagement.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.admin.AddNewProduct;
import com.miniproject.inventorymanagement.admin.AddNewTransaction;
import com.miniproject.inventorymanagement.admin.DialogCrud;
import com.miniproject.inventorymanagement.admin.Home;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.firebase.Product;


public class ProductList extends AppCompatActivity implements ProductAdapter.OnItemClickListener { // Implement the OnItemClickListener interface
    FloatingActionButton add,addNewTransactiontButton;
    DatabaseHandler dbhandler;
    SearchView productSearch;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        productSearch = findViewById(R.id.productSearchView);

        add = findViewById(R.id.addNewProductButton);
        addNewTransactiontButton=findViewById(R.id.addNewTransactiontButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewProduct.class));
                finish();
            }
        });
        addNewTransactiontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewTransaction.class));
            }
        });


        dbhandler = DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbhandler.refreshProducts();

        RecyclerView recyclerView = findViewById(R.id.product_list_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(dbhandler.getProducts());
        recyclerView.setAdapter(adapter);

        // Set the item click listener
        adapter.setOnItemClickListener(this);

        if (getIntent().hasExtra("filterCategory")) {
            String filterCategory = getIntent().getStringExtra("filterCategory");
            adapter.setCategoryFilter(filterCategory);
        }

        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                adapter.setProductList(dbhandler.getProductsList());
            }
        });


        //user and admin inside dashboard product add and transaction button manage
        if (dbhandler.getUser().isAdmin()) {
            add.setVisibility(View.VISIBLE);
            addNewTransactiontButton.setVisibility(View.GONE);
        } else if (dbhandler.getUser().isAuthorized()) {
            add.setVisibility(View.GONE);
            addNewTransactiontButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(ProductList.this, "!!!Invalid", Toast.LENGTH_SHORT).show();
            DatabaseHandler.getInstance().getFirebaseAuth().signOut();
        }


        productSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    @Override
    public void onItemClick(ProductList product) {
        Toast.makeText(this, "by", Toast.LENGTH_SHORT).show();
    }

    // Implement the onItemClick method from the interface
    @Override
    public void onItemClick(Product product) {
        // Handle item click here
        // 'product' is the clicked product, you can do whatever you want with it
        Toast.makeText(this, product.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProductList.this, DialogCrud.class);
        intent.putExtra("product_id", product.getId());
        intent.putExtra("product_name", product.getName());
        intent.putExtra("product_category", product.getCategoryId());
        intent.putExtra("product_description", product.getDescription());
        String buy=Integer.toString(product.getNormalBuyPrice());
        String sell=Integer.toString(product.getNormalSellPrice());
        intent.putExtra("product_buyPrice", buy);
        intent.putExtra("product_sellPrice", sell);
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@"+ buy);

        startActivity(intent);
    }




}
