package com.miniproject.inventorymanagement.firebase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    private static DatabaseHandler instance;

    private FirebaseFirestore firestore;
    private FirebaseAuth  firebaseAuth;

    private Map<Long, Transaction> transactions;
    private Map<String, Product> products;
    private Company company;
    private User user;

    private DatabaseHandler() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        transactions  = new java.util.HashMap<>();
        products = new java.util.HashMap<>();
        fetchUser();
        if  (user != null) {
            fetchCompany();
        }
        // Initialize other shared data
    }

    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }


    public void addTransaction(Long id, Date timestamp, int price, int quantity, Product product) {
        // add transaction to local memory and firestore
        Transaction transaction = new Transaction(id, timestamp, price, quantity, product.getId());
        transactions.put(id, transaction);
        addTransactionToFirestore(transaction);

        // update product in local memory and firestore
      //  product.addTransaction(transaction);
        updateProductToFirestore(product);
    }



    /*
    TODO: ideas NEEDED.
    currently, we can only fetch user data when user is logged in.
    and only using firebase auth. it gives security that no fake user object is created.
    but is it good way to do it?
     */

    public void fetchUser() {
        user = new User();
        user.refreshUserData(firebaseAuth);
    }

    public void fetchCompany() {
        company = new Company();
        company.refreshCompanyData(firebaseAuth, firestore);
    }
    public void fetchTransactions() {
        DocumentReference transactionDocRef = getTransactionsRef();

        transactionDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "got task!!");
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> transactionMap = document.getData();
                    for (Map.Entry<String, Object> entry : transactionMap.entrySet()) {
                        String transactionId = entry.getKey();
                        Map<String, Object> transactionData = (Map<String, Object>) entry.getValue();


                        Long id = Long.parseLong(transactionId);
                        int amount = ((Long) transactionData.get("amount")).intValue();
                        int price = ((Long) transactionData.get("price")).intValue();
                        String productId = (String) transactionData.get("productId");
                        Timestamp timestamp = (Timestamp) transactionData.get("timestamp");

                        Transaction transaction = new Transaction(id, timestamp.toDate(), price, amount, productId);
                        transactions.put(id, transaction);


                        Log.i(TAG, transactionId);
                    }
                }
            }
        });
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
        return user.getId() == company.getAdminId();
    }

    public int refreshCompanyData() {
        return company.refreshCompanyData(firebaseAuth, firestore);
    }

    public int refreshUserData() {
        return user.refreshUserData(firebaseAuth);
    }



    // Getters and Setters
    public FirebaseFirestore getFirestore() {
        return firestore;
    }
    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
    public Map<Long, Transaction> getTransactions() {
        return transactions;
    }
    public Map<String, Product> getProducts() {
        return products;
    }

    public DocumentReference getProductsRef() {
        return firestore.collection("products").document(company.getId());
    }
    public DocumentReference getTransactionsRef() {
        // TODO: remove it after testing up to line GETTRAREF1
        return firestore.collection("transactions").document("Yy5LPrpfC4fcU3FfhQsYFbV2prt1");
        // TODO: GETTRAREF1: remove up to here ----------------
        // TODO: uncomment code below to get transactions ref for company
        // return firestore.collection("transactions").document(company.getId());
    }

    public int addProductToFirebase(Product product) {
        // TODO: add product to firebase
        return 0;
    }

    public int addProduct(String id, String name, String description, int normalBuyPrice, int normalSellPrice) {
        for (String t_id: products.keySet()) {
            if (t_id == id) {
                return 101;
            }
        }
        Product newProduct = new Product(id, name, description, normalBuyPrice, normalSellPrice);
        products.put(id, newProduct);
        return addProductToFirebase(newProduct);
    }
}
