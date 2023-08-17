package com.miniproject.inventorymanagement.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class DatabaseHandler {
    private static DatabaseHandler instance;

    private FirebaseFirestore firestore;
    private FirebaseAuth  firebaseAuth;

    private List<Transaction> transactions;
    private List<Product> products;
    private Company company;
    private User user;
    private DatabaseHandler() {
        firestore = FirebaseFirestore.getInstance();
        // Initialize other shared data
    }

    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addTransaction(int id, Date date, int price, int quantity, Product product) {
        Transaction transaction = new Transaction(id, date, price, quantity, product.getId());
        transactions.add(transaction);
        addTransactionToFirestore(transaction);

        product.addStock(quantity);
        updateProductToFirestore(product);
    }

    public int addTransactionToFirestore(Transaction transaction) {
        // TODO: add new transaction to firestore
        return 0;
    }

    public int updateProductToFirestore(Product product) {
        // TODO: add new product to firestore
        return 0;
    }

    public int refreshTransactions() {
        // TODO: refresh transactions from firestore
        return 0;
    }

    public int refreshProducts() {
        // TODO:  refresh products from firestore
        return 0;
    }

    public boolean isAdmin() {
        if (user.getId() == company.getAdminId()){
            return true;
        }
        return false;
    }

    public int refreshCompanyData() {
        return company.refreshCompanyData(firebaseAuth, firestore);
    }

    public int refreshUserData() {
        return user.refreshUserData(firebaseAuth);
    }

}
