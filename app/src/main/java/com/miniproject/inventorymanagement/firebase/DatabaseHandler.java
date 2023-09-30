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
import com.google.firebase.firestore.WriteBatch;
import com.google.firestore.v1.Write;
import com.miniproject.inventorymanagement.adapters.ProductAdapter;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DatabaseHandler {
    // Attributes
    private static DatabaseHandler instance;

    final private FirebaseFirestore firebaseFirestore;
    final private FirebaseAuth firebaseAuth;

    final private Map<String, Transaction> transactions;
    final private Map<String, Product> products;
    final private Map<String, Category> categories;
    final private Company company;
    final private User user;


    // Constructor
    private DatabaseHandler() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        transactions = new java.util.HashMap<>();
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
    public Task<AuthResult> signInWithEmailAndPassword(@NonNull String email, @NonNull String password) {
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
    public Task<Void> createDocuments(String userId) {
        List<String> firebaseCollections = new ArrayList<>(Arrays.asList("companies", "products", "transactions", "users", "categories"));
        WriteBatch batch = getFirebaseFirestore().batch();
        for (String collection : firebaseCollections) {
            DocumentReference docRef = firebaseFirestore.collection(collection).document(userId);
            batch.set(docRef, new HashMap<>());
            Log.d(TAG, "Created Doc (" + collection + ") for user (" + userId + ")");
        }
        return batch.commit();
    }


    // Products
    public Task<DocumentSnapshot> refreshProducts() {
        // TODO: better code required
        DocumentReference productRef = getProductsRef();
        products.clear();
        Task<DocumentSnapshot> task = productRef.get();
        task.addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                Map<String, Object> documentData = documentSnapshot.getData();
                assert documentData != null;
                for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    Product newProduct = new Product(key);
                    if (value instanceof HashMap) {
                        Map<String, Object> rawProductData = (Map<String, Object>) value;

                        for (Map.Entry<String, Object> rawProdMap : rawProductData.entrySet()) {
                            String prodMapKey = rawProdMap.getKey();
                            Object prodMapValue = rawProdMap.getValue();
                            switch (prodMapKey) {
                                case "id":
                                    newProduct.setId((String) prodMapValue);
                                    break;
                                case "name":
                                    newProduct.setName((String) prodMapValue);
                                    break;
                                case "description":
                                    newProduct.setDescription((String) prodMapValue);
                                    break;
                                case "normalBuyPrice":
                                    newProduct.setNormalBuyPrice(((Long) prodMapValue).intValue());
                                    break;
                                case "normalSellPrice":
                                    newProduct.setNormalSellPrice(((Long) prodMapValue).intValue());
                                    break;
                                case "stockIn":
                                    newProduct.setStockIn(((Long) prodMapValue).intValue());
                                    break;
                                case "stockOut":
                                    newProduct.setStockOut(((Long) prodMapValue).intValue());
                                    break;
                                case "categoryId":
                                    newProduct.setCategoryId((String) prodMapValue);
                                    break;
                            }

                        }

                    }
                    products.put(newProduct.getId(), newProduct);
                }
            }
        });
        return task;
    }
    public void deleteProductAndItsTransactions(String productId) {
        Product productToDelete = getProducts().get(productId);
        if (productToDelete == null) {
            throw new IllegalArgumentException("Product with id (" + productId + ") not found");
        }
        for (String transactionId : productToDelete.getTransactions()) {
            deleteTransaction(transactionId);
        }
        Task<Void> task = getProductsRef().set(products);
    }

    // Transactions
    public int createAndAddTransaction(String productId, Timestamp timestamp, Integer quantity, Integer pricePerUnit) throws IllegalArgumentException {
        /*
        Add Transaction to local and Firestore, also adds transaction id in product.
        */

        /* TODO: what if transaction with id exists: transaction is overwritten
            in stockIn, stockOut in Products fix it */
        if (quantity < 0 && quantity * -1 > Objects.requireNonNull(products.get(productId)).getCurrentStock()) {
            return 1;
        }
        Transaction newTransaction = new Transaction(timestamp, quantity, pricePerUnit, productId);
        Product productToChange = getProducts().get(newTransaction.getProductId());
        if (productToChange == null) {
            throw new IllegalArgumentException("Product with id (" + productId + ") not found");
        }
        transactions.put(newTransaction.getId(), newTransaction);
        newTransaction.updateSelfInFirestore();
        productToChange.addTransaction(newTransaction);
        return 0;
    }
    public Task<DocumentSnapshot> refreshTransactions() {
        DocumentReference transactionDocRef = getTransactionsRef();
        Task<DocumentSnapshot> task = transactionDocRef.get();
        task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                transactions.clear();
                Map<String, Object> documentData = documentSnapshot.getData();
                assert documentData != null;
                for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                    Transaction newTransaction = new Transaction(entry.getKey());
                    Object obj = entry.getValue();
                    if (obj instanceof HashMap) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        for (Map.Entry<String, Object> rawTransactionMap : mapObj.entrySet()) {
                            String key = rawTransactionMap.getKey();
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
    public Task<Void> deleteTransaction(String transactionId) {
        Transaction transactionToDelete = getTransactions().get(transactionId);
        if (transactionToDelete == null) {
            throw new IllegalArgumentException("Transaction with id (" + transactionId + ") not found");
        }
        transactions.remove(transactionToDelete.getId());
        return getProductsRef().set(transactions);
        // TODO: update firebase
    }

    // Categories
    public Task<Void> createAndAddCategory(String id, String name, String colorHex) {
        Category newCategory = new Category(id, name, colorHex);
        categories.put(newCategory.getId(), newCategory);
        return newCategory.updateSelfInFirestore();
    }
    public Task<DocumentSnapshot> refreshCategories() {
        DocumentReference categoriesRef = getCategoriesRef();
        Task<DocumentSnapshot> task = categoriesRef.get();
        task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                categories.clear();
                Map<String, Object> documentData = documentSnapshot.getData();
                for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                    Category newCategory = new Category(entry.getKey());
                    Object obj = entry.getValue();
                    if (obj instanceof HashMap) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        for (Map.Entry<String, Object> rawCategoryMap : mapObj.entrySet()) {
                            String key = rawCategoryMap.getKey();
                            Object value = rawCategoryMap.getValue();
                            switch (key) {
                                case "name":
                                    newCategory.setName((String) value);
                                    break;
                                case "colorHex":
                                    newCategory.setColorHex((String) value);
                                    break;
                            }
                        }
                        }
                        categories.put(newCategory.getId(), newCategory);
                    }
                }
            }
        );
        return task;
    }

    // Users






    @Deprecated
    public boolean isAdmin() {
        // what is company is null OR user?
        // return user.getId().compareTo(company.getAdminId()) == 0;
        return user.isAdmin();
    }


    public int refreshUserData() {
        return user.refreshBasicUserData(firebaseAuth);
    }


    public int addProductToFirebase(Product product) {
        for (String productIds: products.keySet()) {
            if (productIds.compareTo(product.getId()) == 0) {
                return -1;
            }
        }
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

    public int addProduct(String id, String name, String description, int normalBuyPrice, int normalSellPrice, String categoryId) {

        // TODO: checking product_id duplication NOT WORKING!!
        // TODO: fix code below!!
        if (products.containsKey(id)) {
            return 101;
        }
        if (categoryId == null || !categories.containsKey(categoryId)) {
            return 102;
        }
        Product newProduct = new Product(id, name, description, normalBuyPrice, normalSellPrice, categoryId);
        newProduct.updateSelfInFirestore();
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

    public Map<String, Category> getCategories() {
        return categories;
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
        return firebaseFirestore.collection("products").document(getUser().getCompanyId());
//        return firebaseFirestore.collection("products").document(company.getId());
    }

    public DocumentReference getTransactionsRef() {
        return firebaseFirestore.collection("transactions").document(getUser().getCompanyId());
    }

    public DocumentReference getUserRef() {
        // TODO: what if no user?
        return firebaseFirestore.collection("users").document(user.getId());
    }

    public DocumentReference getCompanyRef() {
        System.out.println(getUser().getCompanyId());
        System.out.println(getUser().getCompanyId() == null);
        return firebaseFirestore.collection("companies").document(getUser().getCompanyId());
        // TODO: what if no company?
        // return firebaseFirestore.collection("companies").document(company.getId());
    }

    public DocumentReference getCategoriesRef() {
        // TODO: fix hardcoded line below
        // TODO: what if no Company
        return firebaseFirestore.collection("categories").document(getUser().getCompanyId());
    }

}
