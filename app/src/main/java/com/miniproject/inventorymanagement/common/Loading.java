package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.admin.Home;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_loading);

        Task<DocumentSnapshot> task = DatabaseHandler.getInstance().getUser().refreshAllUserData();
        task.addOnSuccessListener(documentSnapshot -> {
            Toast.makeText(this,"Logged in as " + DatabaseHandler.getInstance().getUser().getName(), Toast.LENGTH_SHORT).show();
            Task<DocumentSnapshot> task2 = DatabaseHandler.getInstance().getCompany().refreshCompanyData();
            task2.addOnSuccessListener(documentSnapshot2 -> {
                Toast.makeText(this, DatabaseHandler.getInstance().getCompany().getName() + " is your company", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            });
        });

    }
}