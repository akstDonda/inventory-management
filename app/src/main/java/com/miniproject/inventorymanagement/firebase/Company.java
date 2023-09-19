package com.miniproject.inventorymanagement.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Company {
    private String name;
    private String id;
    private String adminId;
    private List<String> users;


    // Firestore methods
    public Task<DocumentSnapshot> refreshCompanyData() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        Task<DocumentSnapshot> task = dbHandler.getCompanyRef().get();
        task.addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> companyData;
                companyData = documentSnapshot.getData();
                assert companyData != null;
                System.out.println(companyData);
                name = Objects.requireNonNull(companyData.get("name")).toString();
                adminId = Objects.requireNonNull(companyData.get("adminId")).toString();
                users = (List<String>) companyData.get("users");
            }
        });
        return task;
    }

    public void updateSelfInFirestore() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        dbHandler.getCompanyRef().update("name", name);
        dbHandler.getCompanyRef().update("adminId", adminId);
        dbHandler.getCompanyRef().update("users", users);
    }

    // Some Tag
    public void addUser(String userId) {
        users.add(userId);
        updateSelfInFirestore();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
        updateSelfInFirestore();
    }

    public String getAdminId() {
        return adminId;
    }

    public List<String> getUsers() {
        return users;
    }
}
