package com.miniproject.inventorymanagement.firebase;

import java.util.List;

public class Product {
    String id;
    String name;
    String description;

    int normalBuyPrice;
    int normalSellPrice;

    private int stockIn;
    private int stockOut;
    public List<Transaction> transactions;

    public Product(String id, String name, String description, int normalBuyPrice, int normalSellPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.normalBuyPrice = normalBuyPrice;
        this.normalSellPrice = normalSellPrice;

        this.stockIn = 0;
        this.stockOut = 0;
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);

        if (transaction.quantity > 0) {
            stockIn += transaction.quantity;
        }
        else {
            stockOut -= transaction.quantity;
        }
    }


    public int getCurrentStock() {
        return (stockIn - stockOut);
    }

    public int getStockIn() {
        return stockIn;
    }

    public int getStockOut() {
        return stockOut;
    }

    public String getId() {
        return id;
    }

    public int addStock(int quantity) {
        if  (quantity > 0) {
            stockIn += quantity;
        }
        else {
            stockOut -= quantity;
        }

        return 0;
    }
}
