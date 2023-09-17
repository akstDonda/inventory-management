package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import com.google.firebase.Timestamp;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.adapters.TransactionAdapter;
import com.miniproject.inventorymanagement.admin.AddNewTransaction;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class Transactions extends AppCompatActivity {
    DatabaseHandler dbhandler;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        dbhandler=DatabaseHandler.getInstance();

        dbhandler.addTransaction(Integer.toString(1), new Timestamp(new Date(Integer.toUnsignedLong(56657))),25000,10,dbhandler.getProducts().get("a").getId());
        dbhandler.addTransaction(Integer.toString(2), new Timestamp(new Date(Integer.toUnsignedLong(56657))),23000,10,dbhandler.getProducts().get("b").getId());


        RecyclerView recyclerView=findViewById(R.id.all_transaction_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TransactionAdapter adapter = new TransactionAdapter(dbhandler.getTransactions());
        recyclerView.setAdapter(adapter);

        btn = findViewById(R.id.addTransactionButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Transactions.this, AddNewTransaction.class);
                startActivity(intent);
            }
        });
    }
}