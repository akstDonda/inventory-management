package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.TransactionAdapter;
import com.miniproject.inventorymanagement.admin.AddNewTransaction;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class Transactions extends AppCompatActivity {
    DatabaseHandler dbhandler;
    FloatingActionButton btn;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        dbhandler=DatabaseHandler.getInstance();

//        dbhandler.addTransaction(Integer.toString(1), new Timestamp(new Date(Integer.toUnsignedLong(56657))),25000,10,dbhandler.getProducts().get("a").getId());
//        dbhandler.addTransaction(Integer.toString(2), new Timestamp(new Date(Integer.toUnsignedLong(56657))),23000,10,dbhandler.getProducts().get("b").getId());
        Task<DocumentSnapshot> task = dbhandler.refreshTransactions();


        RecyclerView recyclerView=findViewById(R.id.all_transaction_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TransactionAdapter adapter = new TransactionAdapter(dbhandler.getTransactions());
        recyclerView.setAdapter(adapter);

        task.addOnSuccessListener(documentSnapshot -> {
            adapter.setList(dbhandler.getTransactionsList());
            adapter.notifyDataSetChanged();
        });

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