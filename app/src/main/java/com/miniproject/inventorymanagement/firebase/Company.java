package com.miniproject.inventorymanagement.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class Company {
    private String name;
    private String id;
    private String adminId;
    private List<String> users;
    private DatabaseHandler dbHandler;

    public Company() {
        dbHandler = DatabaseHandler.getInstance();
    }

    public Task<DocumentSnapshot> getCompanyIdFromFirestore() {
        /*
        * This method is used to refresh the company data
        * It returns 0 if the data is successfully refreshed
        * Error Codes: 1004 - No User, 1010 - No Database
        */
        FirebaseAuth fbAuth = dbHandler.getFirebaseAuth();
        FirebaseFirestore fbDb = dbHandler.getFirebaseFirestore();
//        if (fbAuth.getCurrentUser() == null) {
//            return 1004;
//        }
        DocumentReference userDataDocRef = dbHandler.getUserRef();
//        if (userDataDocRef == null) {
//            return 1010;
//        }
        Task<DocumentSnapshot> task = userDataDocRef.get();
        task.addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                id = documentSnapshot.getString("adminId");
                refreshCompanyData();
            }

        });
        return task;
    }

    public int refreshCompanyData() {
        // TODO: refreshing data takes time but it should return something usable or which can track tasks
        if (id == null) {
            Task<DocumentSnapshot> task = getCompanyIdFromFirestore();
            task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    refreshCompanyData();
                }
            });
            return 0;
        }
        Task<DocumentSnapshot> task = dbHandler.getCompanyRef().get();
        task.addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> companyData;
                companyData = documentSnapshot.getData();
                name = companyData.get("name").toString();
                adminId = companyData.get("adminId").toString();
                users = (List<String>) companyData.get("users");
            }
        });
        return 0;
    }


    // Getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAdminId() {
        return adminId;
    }
}
