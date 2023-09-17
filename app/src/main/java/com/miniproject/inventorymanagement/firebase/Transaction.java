package com.miniproject.inventorymanagement.firebase;

import com.google.firebase.Timestamp;

public class Transaction {
    private final String id;
    private Timestamp timestamp;  // Timestamp is a class from the Firebase SDK
    private int quantity;
    private int price;
    private String productId;

    public Transaction(String id, Timestamp date, int price, int quantity, String productId) {
        this.id = id;
        this.timestamp = date;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }
    public Transaction(Timestamp timestamp, Integer quantity, Integer pricePerUnit, String productId) {
        this.id = productId + timestamp.toString();
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.price = pricePerUnit;
        this.productId = productId;
    }

    public void updateSelfInFirestore() {
        DatabaseHandler.getInstance().getTransactionsRef().update(id, this);
    }

    // Getters
    public String getId() {
        return id;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getPrice() {
        return price;
    }
    public String getProductId() {
        return productId;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }

}
