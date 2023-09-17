package com.miniproject.inventorymanagement.firebase;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Transaction {
    private final String id;
    private Timestamp timestamp;  // Timestamp is a class from the Firebase SDK
    private int quantity;
    private int price;
    private String productId;

    public Transaction (String id, Timestamp date, int price, int quantity, String productId) {
        this.id = id;
        this.timestamp = date;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
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
