package com.miniproject.inventorymanagement.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Company {
    private String name;
    private String id;
    private String adminId;
    private List<User> users;

    public int refreshCompanyData(FirebaseAuth  auth, FirebaseFirestore db) {
        // TODO: get user from auth
        // TODO: get company data using user data
        return 0;
    }
}
