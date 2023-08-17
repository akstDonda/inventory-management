package com.miniproject.inventorymanagement.firebase;

import java.util.Date;

public class Transaction {
    public int id;
    public Date date;
    public int quantity;
    public int price;
    public String product_id;

    public Transaction (int id, Date date, int price, int quantity, String product_id) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.product_id = product_id;
    }
}
