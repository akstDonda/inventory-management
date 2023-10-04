package com.miniproject.inventorymanagement.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.TimedText;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.Dashboard;
import com.miniproject.inventorymanagement.common.Loading;
import com.miniproject.inventorymanagement.common.ProductList;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import org.w3c.dom.Text;

public class DialogCrud extends AppCompatActivity {
    DatabaseHandler dbhandler;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_crud);

        TextView details_id =findViewById(R.id.details_id);
        TextView details_name =findViewById(R.id.details_Name);
        TextView details_category_name =findViewById(R.id.details_category_Name);
        TextView details_description =findViewById(R.id.details_description);
        TextView detail_buyPrice =findViewById(R.id.details_buyPrice);
        TextView detail_sellPrice =findViewById(R.id.details_sellPrice);




        // Get the Intent that started this activity
        Intent intent = getIntent();
        String product_id = intent.getStringExtra("product_id");
        String product_name = intent.getStringExtra("product_name");
        String product_category = intent.getStringExtra("product_category");
        String product_description = intent.getStringExtra("product_description");
        String product_buyPrice = intent.getStringExtra("product_buyPrice");
        String product_sellPrice = intent.getStringExtra("product_sellPrice");

        details_id.setText(String.format("Product Id :  %s", product_id));
        details_name.setText(String.format("Product Name :  %s", product_name));
        details_category_name.setText(String.format("Product Category :  %s", product_category));
        details_description.setText(String.format("Product Description :  %s", product_description));
        detail_buyPrice.setText(String.format("Product BuyPrice :  %s", product_buyPrice));
        detail_sellPrice.setText(String.format("Product sellPrice :  %s", product_sellPrice));

        dbhandler = DatabaseHandler.getInstance();
        Button product_crud_delete=findViewById(R.id.product_crud_delete);
        product_crud_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhandler.deleteProductAndItsTransactions(product_id);
                Intent intent = new Intent(DialogCrud.this, ProductList.class);
                startActivity(intent);
                finish();

            }
        });

        Button productUpdate = findViewById(R.id.updateProduct);
        productUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DialogCrud.this, AddNewProduct.class);
                intent.putExtra("productId", product_id);
                startActivity(intent);
            }
        });

        if (dbhandler.getUser().isAdmin()) {

        } else if (dbhandler.getUser().isAuthorized()) {
            product_crud_delete.setVisibility(View.GONE);
            productUpdate.setVisibility(View.GONE);
        } else {

        }




    }
}