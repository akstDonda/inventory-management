package com.miniproject.inventorymanagement.firebase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    // Attributes
    private static DatabaseHandler instance;

    final private FirebaseFirestore firebaseFirestore;
    final private FirebaseAuth  firebaseAuth;

    final private Map<String, Transaction> transactions;
    final private Map<String, Product> products;
    final private Map<String, Category> categories;
    final private Company company;
    final private User user;

    // Constructor
    private DatabaseHandler() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        transactions  = new java.util.HashMap<>();
        products = new java.util.HashMap<>();
        categories = new java.util.HashMap<>();
        user = new User();
        company = new Company();
        // TODO: fetchCompany Doesn't complete instantly making if condition below unreliable
        // TODO: Initialize other shared data
    }
    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }


    // Firebase Auth
    public Task<AuthResult> signInWithEmailAndPassword(@NonNull String email,@NonNull String password) {
        Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(email, password);
        task.addOnSuccessListener(authResult -> {
            Task<DocumentSnapshot> task2 = getUser().refreshAllUserData();
            task2.addOnSuccessListener(documentSnapshot -> {
                getCompany().refreshCompanyData();
            });
        });
        return task;
    }

    // Firestore
    public void createDocuments(String userId) {
        List<String> firebaseCollections = new ArrayList<>(Arrays.asList("companies", "products", "transactions", "users", "categories"));

        for (String collection: firebaseCollections) {
            DocumentReference docRef = firebaseFirestore.collection(collection).document(userId);
            docRef.set(new HashMap<>());
            Log.d(TAG, "Created Doc (" + collection + ") for user (" + userId + ")");
        }

    }


    // Products

    // Transactions
    public void createAndAddTransaction(String productId, Timestamp timestamp, Integer quantity, Integer pricePerUnit) {
        /*
        Add Transaction to local and Firestore, also adds transaction id in product.
        */
        Transaction newTransaction = new Transaction(timestamp, quantity, pricePerUnit, productId);
        Product productToChange = getProducts().get(newTransaction.getProductId());
        if (productToChange == null) {
            throw new IllegalArgumentException("Product with id (" + productId + ") not found");
        }
        transactions.put(newTransaction.getId(), newTransaction);
        newTransaction.updateSelfInFirestore();
        productToChange.addTransaction(newTransaction);
    }

    // Users



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


                        String id = (String) transactionData.get("id");
                        int amount = ((Long) transactionData.get("amount")).intValue();
                        int price = ((Long) transactionData.get("price")).intValue();
                        String productId = (String) transactionData.get("productId");
                        Timestamp timestamp = (Timestamp) transactionData.get("timestamp");

                        Transaction transaction = new Transaction(id, timestamp, price, amount, productId);
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

    public int updateProductToFirestore(String productId) {
        // TODO: add new product to firestore
        return 0;
    }

    public Task<DocumentSnapshot> refreshTransactions() {
        DocumentReference  transactionDocRef = getTransactionsRef();
        Task<DocumentSnapshot> task = transactionDocRef.get();
        transactions.clear();
        task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> documentData = documentSnapshot.getData();
                for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                    Transaction newTransaction = new Transaction(entry.getKey().toString());
                    Object obj = entry.getValue();
                    if (obj instanceof HashMap) {
                        Map<String, Object> rawTransactionMaap = (Map<String, Object>) obj;
                        for (Map.Entry<String, Object> rawTransactionMap : rawTransactionMaap.entrySet()) {
                            String key = rawTransactionMap.getKey().toString();
                            Object value = rawTransactionMap.getValue();
                            switch (key) {
                                case "quantity":
                                    newTransaction.setQuantity(((Long) value).intValue());
                                    break;
                                case "price":
                                    newTransaction.setPrice(((Long) value).intValue());
                                    break;
                                case "productId":
                                    newTransaction.setProductId((String) value);
                                    break;
                                case "timestamp":
                                    newTransaction.setTimestamp((Timestamp) value);
                                    break;
                            }
                        }
                        transactions.put(newTransaction.getId(), newTransaction);
                        Log.d(TAG, transactions.get(newTransaction.getId()) + "");
                    }
                }
            }
        });
        return task;


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
                                            newProduct.setNormalBuyPrice(((Long) aluev).intValue());
                                            break;
                                        case "normalSellPrice":
                                            newProduct.setNormalSellPrice(((Long) aluev).intValue());
                                            break;
                                        case "stockIn":
                                            newProduct.setStockIn(((Long) aluev).intValue());
                                            break;
                                        case "stockOut":
                                            newProduct.setStockOut(((Long) aluev).intValue());
                                            break;
                                    }

                                }

                            }
                            products.put(newProduct.getId(), newProduct);
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
        // TODO: what is company is null OR user?
        return user.getId().compareTo(company.getAdminId()) == 0;
    }


    public int refreshUserData() {
        return user.refreshBasicUserData(firebaseAuth);
    }


    public int addProductToFirebase(Product product) {
        try {
            // Get a reference to the "products" collection in Firestore
            CollectionReference productsCollectionRef = firebaseFirestore.collection("products");

            // Create a new document with a custom ID (e.g., product ID)
            // You can also use .add() to auto-generate a document ID if needed
            getCompany();
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


    // Getters
    public User getUser() {
        if (user == null)
            return new User();
        return user;
    }
    public Company getCompany() {
        if (company == null)
            return new Company();
        return company;
    }

    public FirebaseFirestore getFirebaseFirestore() {
        return firebaseFirestore;
    }
    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
    public Map<String, Transaction> getTransactions() {
        return transactions;
    }
    public Map<String, Product> getProducts() {
        return products;
    }

    public List<Product> getProductsList() {
        return new ArrayList<Product>(products.values());
    }
    public List<Transaction> getTransactionsList() {
        return new ArrayList<Transaction>(transactions.values());
    }
    public List<Category> getCategoriesList() {
        return new ArrayList<Category>(categories.values());
    }

    public DocumentReference getProductsRef() {
        return firebaseFirestore.collection("products").document("Yy5LPrpfC4fcU3FfhQsYFbV2prt1");
//        return firebaseFirestore.collection("products").document(company.getId());
    }
    public DocumentReference getTransactionsRef() {
        // TODO: remove it after testing up to line GETTRAREF1
        return firebaseFirestore.collection("transactions").document("Yy5LPrpfC4fcU3FfhQsYFbV2prt1");
        // TODO: GETTRAREF1: remove up to here ----------------
        // TODO: uncomment code below to get transactions ref for company
//        return firebaseFirestore.collection("transactions").document(company.getId());
    }
    public DocumentReference getUserRef() {
        // TODO: what if no user?
        return firebaseFirestore.collection("users").document(user.getId());
    }
    public DocumentReference getCompanyRef() {
        return firebaseFirestore.collection("companies").document("Yy5LPrpfC4fcU3FfhQsYFbV2prt1");
        // TODO: what if no company?
        // return firebaseFirestore.collection("companies").document(company.getId());
    }
    public DocumentReference getCategoriesRef() {
        // TODO: fix hardcoded line below
        // TODO: what if no Company
        return firebaseFirestore.collection("categories").document("Yy5LPrpfC4fcU3FfhQsYFbV2prt1");
    }

}
