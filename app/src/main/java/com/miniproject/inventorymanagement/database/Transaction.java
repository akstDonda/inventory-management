package com.miniproject.inventorymanagement.database;

import java.util.Date;

public class Transaction {
    public int id;
    public Date date;
    public int quantity;
    public int price;

    public Transaction (int id, Date date, int price, int quantity) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
    }
}
