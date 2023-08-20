package com.miniproject.inventorymanagement.firebase;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Transaction {
    private Long id;
    private Date date;
    private int quantity;
    private int price;
    private String product_id;

    public Transaction (Long id, Date date, int price, int quantity, String product_id) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.product_id = product_id;
    }



    // Getters
    public Long getId() {
        return id;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getPrice() {
        return price;
    }
    public String getProductId() {
        return product_id;
    }
    public Date getDate() {
        return date;
    }
}
