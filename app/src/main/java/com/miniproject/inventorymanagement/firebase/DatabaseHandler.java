package com.miniproject.inventorymanagement.firebase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;
import com.miniproject.inventorymanagement.common.ProductList;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

    public void createDocuments(String userId) {
        List<String> firebaseCollections = new ArrayList<>(Arrays.asList("companies", "products", "transactions", "users"));

        for (String collection: firebaseCollections) {
            DocumentReference docRef = firestore.collection(collection).document(userId);
            docRef.set(new HashMap<>());
        }

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

    public Task<DocumentSnapshot> refreshProducts() {
        DocumentReference productRef = getProductsRef();
        Map<String, Product> newProducts;
        Task<DocumentSnapshot> task = productRef.get();
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> documentData = document.getData();
                        for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            Product newProduct = new Product();
                            if (value instanceof HashMap) {
                                Map<String, Object> rawProductData = (Map<String, Object>) value;

                                for (Map.Entry<String, Object> rawProdMap: rawProductData.entrySet()) {
                                    String keu2 = rawProdMap.getKey().toString();
                                    Object aluev = rawProdMap.getValue();
                                    Log.e(TAG, "ALUEV: " + aluev + " KEU2: " + keu2);
//                                    if (keu2.compareTo("id") == 0) {
//                                        Log.e(TAG, "DONE:ID");
//                                        newProduct.setId((String) aluev);
//                                    }
//                                    else if (keu2 == "name") {
//                                        prdname = (String) aluev;
//                                    } else if (keu2 == "description") {
//                                        prdDescription = (String) aluev;
//                                    } else if (keu2 == "normalBuyPrice") {
//                                        buyP = (Integer) aluev;
//                                    } else if (keu2 == "normalSellPrice") {
//                                        sellP = (Integer) aluev;
//
//                                    }
                                    switch (keu2) {
                                        case "id":
                                            newProduct.setId((String) aluev);
                                            break;
                                        case "name":
                                            newProduct.setName((String) aluev);
                                            break;
                                        case "description":
                                            newProduct.setDescription((String) aluev);
                                            break;
                                        case "normalBuyPrice":
                                            newProduct.setNormalBuyPrice((Long) aluev);
                                            break;
                                        case "normalSellPrice":
                                            newProduct.setNormalSellPrice((Long) aluev);
                                            break;
                                    }

                                }

                            }
                            Log.e(TAG, newProduct.getId() + newProduct.getName());
                            products.put(newProduct.getId(), newProduct);
                            Log.d(TAG, "PRODUCTS: " + products);
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        // TODO:  refresh products from firestore
        return task;
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
        try {
            // Get a reference to the "products" collection in Firestore
            CollectionReference productsCollectionRef = firestore.collection("products");

            // Create a new document with a custom ID (e.g., product ID)
            // You can also use .add() to auto-generate a document ID if needed
            if (company == null) {
                fetchCompany();
            }
            DocumentReference productDocRef = productsCollectionRef.document(company.getId());

            // Create a map for the product data
            Map<String, Object> productData = new HashMap<>();
            productData.put("id", product.getId());
            productData.put("name", product.getName());
            productData.put("description", product.getDescription());
            productData.put("normalBuyPrice", product.getNormalBuyPrice());
            productData.put("normalSellPrice", product.getNormalSellPrice());

            // Add the product data to Firestore
            productDocRef.update(product.getId(), productData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Product added successfully
                            // You can perform any necessary actions here
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error adding product
                            // Handle the error, such as showing an error message to the user
                        }
                    });

            return 1; // Return a success code
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return an error code
        }
    }


    public int addProduct(String id, String name, String description, int normalBuyPrice, int normalSellPrice) {

        // TODO: checking product_id duplication NOT WORKING!!
        // TODO: fix code below!!
        for (Product p: products.values()) {
            if (p.getId() == id) {
                return 101;
            }
        }
        Product newProduct = new Product(id, name, description, normalBuyPrice, normalSellPrice);
        products.put(id, newProduct);
        return addProductToFirebase(newProduct);
    }
}
