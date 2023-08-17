package com.miniproject.inventorymanagement.firebase;

import androidx.annotation.NonNull;

import java.util.List;

public class Product {
    private String id;
    private String name;
    private String description;

    private int normalBuyPrice;
    private int normalSellPrice;

    private int stockIn;
    private int stockOut;
    public List<Integer> transactions;

    public Product(String id, String name, String description, int normalBuyPrice, int normalSellPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.normalBuyPrice = normalBuyPrice;
        this.normalSellPrice = normalSellPrice;

        this.stockIn = 0;
        this.stockOut = 0;
    }

    public void addTransaction(@NonNull Transaction transaction){
        transactions.add(transaction.getId());
        addStock(transaction.getQuantity());
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



    // Getters
    public String getId() {
        return id;
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
    public int getNormalBuyPrice() {
        return normalBuyPrice;
    }
    public int getNormalSellPrice() {
        return normalSellPrice;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<Integer> getTransactions() {
        return transactions;
    }
}
