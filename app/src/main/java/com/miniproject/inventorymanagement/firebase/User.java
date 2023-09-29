package com.miniproject.inventorymanagement.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;
import java.util.Objects;

public class User {
    private String name;
    private String id;
    private String email;
    private String companyId;

    public int refreshBasicUserData(FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            return 1;
        }

        id = firebaseAuth.getCurrentUser().getUid();
        name = firebaseAuth.getCurrentUser().getDisplayName();
        email = firebaseAuth.getCurrentUser().getEmail();
        return 0;
    }

    public Task<DocumentSnapshot> refreshCompanyId() {
        DocumentReference userRef = DatabaseHandler.getInstance().getUserRef();
        Task<DocumentSnapshot> task = userRef.get();
        task.addOnSuccessListener(documentSnapshot -> {
            Map<String, Object> documentData = documentSnapshot.getData();
            assert documentData != null;
            companyId = Objects.requireNonNull(documentData.get("companyId")).toString();
        });
        return task;

    }

    public Task<DocumentSnapshot> refreshAllUserData() {
        FirebaseAuth fbAuth = DatabaseHandler.getInstance().getFirebaseAuth();
        refreshBasicUserData(fbAuth);
        return refreshCompanyId();
    }

    // Tag 1
    public Boolean isAdmin() {
        if (id == null)
            refreshAllUserData();
        if (id == null)
            throw new IllegalStateException("User id not set");
        return id.equals(DatabaseHandler.getInstance().getCompany().getAdminId());
    }

    // Getters
    public String getId() {
        if (id == null)
            refreshAllUserData();
        if (id == null)
            throw new IllegalStateException("User id not set");
        return id;
    }

    public String getName() {
        if (name == null)
            refreshAllUserData();
        return name;
    }

    public String getEmail() {
        if (email == null)
            refreshAllUserData();
        if (email == null)
            throw new IllegalStateException("User email not set");
        return email;
    }

    public String getCompanyId() {
        return companyId;
    }
}
