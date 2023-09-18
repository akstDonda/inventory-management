package com.miniproject.inventorymanagement.adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.Product;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Integer lowStockLimit;
    private Boolean variableLowStock;

    public ProductAdapter(Map<String, Product> productsMap) {
        // Convert the Map values to a List
        lowStockLimit = -1;
        productList = getProductListFromMap(productsMap, lowStockLimit);
    }
    public ProductAdapter(Map<String, Product> productsMap, Integer lowStockLimit) {
        // Convert the Map values to a List and add only if stock is low
        this.lowStockLimit = lowStockLimit;
        this.productList = getProductListFromMap(productsMap, lowStockLimit);
        Log.e(TAG, productList + "");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declare your UI components here
        private TextView productNameEditText;
        private TextView productDescriptionEditText;
        // ... other UI components

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your UI components here
            productNameEditText = itemView.findViewById(R.id.productNameTextView);
            productDescriptionEditText = itemView.findViewById(R.id.productCurrentStockTextView);
            // ... initialize other UI components
        }

        public void bind(Product product) {
            // Bind product data to UI components
            productNameEditText.setText(product.getName());
            productDescriptionEditText.setText(Integer.toString(product.getStockIn() - product.getStockOut()));
            // ... bind other data
        }

    }
    public void setProductList(Map<String, Product> productsMap) {
        // TODO: handle variable low stock methods
        productList = getProductListFromMap(productsMap, lowStockLimit);
    }
    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    private ArrayList<Product> getProductListFromMap(Map<String, Product> productsMap, Integer lowStockLimit) {
        // TODO: handle variable low stock methods
        if (lowStockLimit == -1) {
            return new ArrayList<>(productsMap.values());
        }
        ArrayList<Product> productList = new ArrayList<>();
        for (Product product : productsMap.values()) {
            if (product.getCurrentStock() <= lowStockLimit) {
                productList.add(product);
            }
        }
        return productList;
    }
}
