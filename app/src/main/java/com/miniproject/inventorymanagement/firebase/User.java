package com.miniproject.inventorymanagement.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;
import java.util.Objects;

public class User {
    private String displayName;
    private String id;
    private String email;
    private String companyId;

    public int refreshBasicUserData(FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            return 1;
        }

        id = firebaseAuth.getCurrentUser().getUid();
        displayName = firebaseAuth.getCurrentUser().getDisplayName();
        email = firebaseAuth.getCurrentUser().getEmail();
        return 0;
    }

    public Task<DocumentSnapshot> refreshFirestoreData() throws IllegalStateException{
        DocumentReference userRef = DatabaseHandler.getInstance().getUserRef();
        Task<DocumentSnapshot> task = userRef.get();
        task.addOnSuccessListener(documentSnapshot -> {
            Map<String, Object> documentData = documentSnapshot.getData();
            if (documentData == null)
                throw new IllegalStateException("No Document Found");
            companyId = Objects.requireNonNull(documentData.get("companyId")).toString();
            if (documentData.containsKey("displayName"))
                displayName = Objects.requireNonNull(documentData.get("displayName")).toString();
            System.out.println("Fetched CompanyId: " + companyId);
        });
        return task;
    }


    public Task<DocumentSnapshot> refreshAllUserData() {
        FirebaseAuth fbAuth = DatabaseHandler.getInstance().getFirebaseAuth();
        refreshBasicUserData(fbAuth);
        return refreshFirestoreData();
    }

    // Tag 1
    public Boolean isAdmin() {
        if (id == null)
            refreshAllUserData();
        if (id == null)
            throw new IllegalStateException("User id not set");
        return id.equals(DatabaseHandler.getInstance().getCompany().getAdminId());
    }

    public Boolean isAuthorized() {
        return DatabaseHandler.getInstance().getCompany().getUsers().contains(getId());
    }

    // Setters
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        DatabaseHandler.getInstance().getUserRef().update("displayName", displayName);
    }
    // Getters
    public String getId() {
        if (id == null)
            refreshAllUserData();
        if (id == null)
            throw new IllegalStateException("User id not set");
        return id;
    }

    public String getDisplayName() {
        if (displayName == null)
            refreshAllUserData();
        return displayName;
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
