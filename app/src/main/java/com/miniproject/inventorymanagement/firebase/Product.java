package com.miniproject.inventorymanagement.firebase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Product {
    // Attributes
    private String id;
    private String name;
    private String description;

    private int normalBuyPrice;
    private int normalSellPrice;

    private int stockIn;
    private int stockOut;
    private List<String> transactions;
    private String categoryId;

    // Constructors
    public Product(String id, String name, String description, int normalBuyPrice, int normalSellPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.normalBuyPrice = normalBuyPrice;
        this.normalSellPrice = normalSellPrice;

        this.stockIn = 0;
        this.stockOut = 0;
        this.transactions = new ArrayList<String>();
    }
    public Product(String id, String name, String description, int normalBuyPrice, int normalSellPrice, String categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.normalBuyPrice = normalBuyPrice;
        this.normalSellPrice = normalSellPrice;

        this.stockIn = 0;
        this.stockOut = 0;
        this.transactions = new ArrayList<String>();
        this.categoryId = categoryId;
    }
    public Product() {
        this.stockIn = 0;
        this.stockOut = 0;
        this.transactions = new ArrayList<String>();
    }

    // Methods (these methods update local obj but NOT in firestore)
    private void addStock(int quantity) {
        if  (quantity > 0) {
            stockIn += quantity;
        }
        else {
            stockOut -= quantity;
        }
    }

    // Public methods (these methods update local obj AND in firestore)
    public void addTransaction(@NonNull Transaction transaction){
        transactions.add(transaction.getId());
        addStock(transaction.getQuantity());
        updateSelfInFirestore();
    }

    // Firestore functions
    public void updateSelfInFirestore() {
        DatabaseHandler.getInstance().getProductsRef().update(id, this);
    }


    // Setters
    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNormalBuyPrice(int normalBuyPrice) {
        this.normalBuyPrice = normalBuyPrice;
    }
    public void setNormalSellPrice(int normalSellPrice) {
        this.normalSellPrice = normalSellPrice;
    }
    public void setStockIn(int stockIn) {
        this.stockIn = stockIn;
    }
    public void setStockOut(int stockOut) {
        this.stockOut = stockOut;
    }
    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
    public List<String> getTransactions() {
        return transactions;
    }
}
