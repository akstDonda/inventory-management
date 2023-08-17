package com.miniproject.inventorymanagement.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class User {
    private String name;
    private String id;
    private String email;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int refreshUserData(FirebaseAuth firebaseAuth) {
        if (firebaseAuth == null) {
            return 1;
        }

        this.id = firebaseAuth.getCurrentUser().getUid();
        this.name = firebaseAuth.getCurrentUser().getDisplayName();
        this.email = firebaseAuth.getCurrentUser().getEmail();
        return 0;
    }
}
