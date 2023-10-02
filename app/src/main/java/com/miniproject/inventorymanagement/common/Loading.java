package com.miniproject.inventorymanagement.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.admin.Home;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_loading);
        Task<DocumentSnapshot> task = null;

        // TODO: doesn't handle exception correctly
        try {
            task = DatabaseHandler.getInstance().getUser().refreshAllUserData();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UserTypeActivity.class));
            DatabaseHandler.getInstance().getFirebaseAuth().signOut();
            return;
        }
        assert task != null;
        task.addOnSuccessListener(documentSnapshot -> {
                Toast.makeText(this, "Logged in as " + DatabaseHandler.getInstance().getUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                Task<DocumentSnapshot> task2 = DatabaseHandler.getInstance().getCompany().refreshCompanyData();
                task2.addOnSuccessListener(documentSnapshot2 -> {
                    DatabaseHandler dbHandler = DatabaseHandler.getInstance();
                    Task<DocumentSnapshot> refreshUserTask = dbHandler.getUser().refreshAllUserData();
                    Task<DocumentSnapshot> refreshCompanyTask = dbHandler.getCompany().refreshCompanyData();
                    Task<DocumentSnapshot> refreshProductTask = dbHandler.refreshProducts();
                    Task<DocumentSnapshot> refreshTransactionTask = dbHandler.refreshTransactions();
                    Task<DocumentSnapshot> refreshCategoriesTask = dbHandler.refreshCategories();

                    Tasks.whenAllSuccess(refreshUserTask, refreshCompanyTask, refreshProductTask, refreshTransactionTask, refreshCategoriesTask)
                            .addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                                @Override
                                public void onSuccess(List<Object> objects) {
                                    if (dbHandler.getUser().isAdmin()) {
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                    } else if (dbHandler.getUser().isAuthorized()) {
                                        Toast.makeText(Loading.this, "hello", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                    } else {
                                        Toast.makeText(Loading.this, "!!!Invalid", Toast.LENGTH_SHORT).show();
                                        DatabaseHandler.getInstance().getFirebaseAuth().signOut();
                                    }
                                    //  Toast.makeText(Loading.this, "Unauthorized!", Toast.LENGTH_SHORT).show();
                                }
                            });
//                if (DatabaseHandler.getInstance().getUser().isAuthorized()) {
//                    startActivity(new Intent(getApplicationContext(), Home.class));
//                }
//                Toast.makeText(this, DatabaseHandler.getInstance().getCompany().getName() + " is your company", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), Home.class));
                });
            });


        }

}