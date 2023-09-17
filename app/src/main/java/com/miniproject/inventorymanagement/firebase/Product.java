package com.miniproject.inventorymanagement.firebase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String name;
    private String description;

    private int normalBuyPrice;
    private int normalSellPrice;

    private int stockIn;
    private int stockOut;
    public List<String> transactions;

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
    public Product() {
        this.stockIn = 0;
        this.stockOut = 0;
        this.transactions = new ArrayList<String>();
    }


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
//    public void setNormalBuyPrice(Long normalBuyPrice) {
//        this.normalBuyPrice = normalBuyPrice.intValue();
//    }
//    public void setNormalSellPrice(Long normalSellPrice) {
//        this.normalSellPrice = normalSellPrice.intValue();
//    }

    public void addTransaction(@NonNull Transaction transaction){
        transactions.add(transaction.getId());
        addStock(transaction.getQuantity());
        addTransactionIdToFirebase(transaction.getId());
    }

    public int addTransactionIdToFirebase(String transactionId){
        // TODO: given transactionId, add it to firebase
        return 0;
    }

    public int addStock(int quantity) {
        if  (quantity > 0) {
            stockIn += quantity;
            updateFirebaseItem("stockIn", quantity);
        }
        else {
            stockOut -= quantity;
            updateFirebaseItem("stockOut", quantity);
        }
        return 0;
    }

    // TODO: better approach to update firebase item
    // TODO: using itemName that is passed in as a parameter in firestore functions
    // TODO: also what about Sting values?
    private int updateFirebaseItem(String itemName, int newValue) {
        if (itemName.equals("stockIn")) {
            // TODO: update stockIn in firebase
            return 0;
        }
        else if (itemName.equals("stockOut")) {
            // TODO:  update stockOut in firebase
            return 0;
        }
        else if (itemName.equals("addTransactionIdToFirebase")) {
            // TODO: update addTransactionIdToFirebase in firebase
            return 0;
        }
        return 1;
    }

    // Setters
    public void setStockIn(int stockIn) {
        this.stockIn = stockIn;
    }
    public void setStockOut(int stockOut) {
        this.stockOut = stockOut;
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
