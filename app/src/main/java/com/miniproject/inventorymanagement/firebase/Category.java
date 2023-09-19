package com.miniproject.inventorymanagement.firebase;

import com.google.android.gms.tasks.Task;

public class Category {
    final private String id;
    private String name;
    private final String colorHex;

    // Constructors
    public Category(String id, String name, String colorHex) {
        this.id = id;
        this.name = name;
        this.colorHex = colorHex;
    }

    public Category(String id, String name) {
        this(id, name, null);
    }

    public Category(String id) {
        this(id, null, null);
    }


    // Firestore methods
    public Task<Void> updateSelfInFirestore() {
        return DatabaseHandler.getInstance().getCategoriesRef().update(id, this);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters and Getters
    public void setName(String name) {
        this.name = name;
        updateSelfInFirestore();
    }

    public String getColorHex() {
        return colorHex;
    }
}
