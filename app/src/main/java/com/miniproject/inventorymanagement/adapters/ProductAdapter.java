package com.miniproject.inventorymanagement.adapters;

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
//
//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
//
//    private List<Product> productList;
//
//    public ProductAdapter(Map<String, Product> productsMap) {
//        // Convert the Map values to a List
//        productList = new ArrayList<>(productsMap.values());
//    }
//
//    public void updateData(Map<String, Product> updatedProductsMap) {
//        productList.clear();
//        productList.addAll(updatedProductsMap.values());
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Product product = productList.get(position);
//        // Bind the product data to the ViewHolder's UI components
//        holder.bind(product);
//    }
//
//    @Override
//    public int getItemCount() {
//        return productList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // Declare your UI components here and initialize them in the constructor
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            // Initialize your UI components here
//        }
//
//        public void bind(Product product) {
//            // Bind the product data to your UI components
//            // For example:
//            // productNameTextView.setText(product.getName());
//            // productDescriptionTextView.setText(product.getDescription());
//            // ...
//        }
//    }
//}
//
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;

    public ProductAdapter(Map<String, Product> productsMap) {
        // Convert the Map values to a List
        productList = new ArrayList<>(productsMap.values());
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
        productList = new ArrayList<>(productsMap.values());
    }
}
