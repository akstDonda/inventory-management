package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.Date;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;
import com.miniproject.inventorymanagement.adapters.TransactionAdapter;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class Transactions extends AppCompatActivity {
    DatabaseHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        dbhandler=DatabaseHandler.getInstance();

        dbhandler.addTransaction(Integer.toUnsignedLong(1), new Date(Integer.toUnsignedLong(5667)),25000,10,dbhandler.getProducts().get("a"));
        dbhandler.addTransaction(Integer.toUnsignedLong(2), new Date(Integer.toUnsignedLong(56657)),23000,10,dbhandler.getProducts().get("b"));


        RecyclerView recyclerView=findViewById(R.id.all_transaction_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TransactionAdapter adapter = new TransactionAdapter(dbhandler.getTransactions());
        recyclerView.setAdapter(adapter);

    }
}