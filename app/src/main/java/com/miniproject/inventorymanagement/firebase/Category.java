package com.miniproject.inventorymanagement.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class Category {
    final private String id;
    private String name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category(String id) {
        this.id = id;
    }

    // Firestore methods
    public void updateSelfInFirestore() {
        DatabaseHandler.getInstance().getCategoriesRef().update(id, this);
    }


    // Setters and Getters
    public void setName(String name) {
        this.name = name;
        updateSelfInFirestore();
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
