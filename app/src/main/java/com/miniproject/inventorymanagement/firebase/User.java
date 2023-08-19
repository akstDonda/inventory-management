package com.miniproject.inventorymanagement.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class User {
    private String name;
    private String id;
    private String email;

    public int refreshUserData(FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            return 1;
        }

        id = firebaseAuth.getCurrentUser().getUid();
        name = firebaseAuth.getCurrentUser().getDisplayName();
        email = firebaseAuth.getCurrentUser().getEmail();
        return 0;
    }



    // Getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
