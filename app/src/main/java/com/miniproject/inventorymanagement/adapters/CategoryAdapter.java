package com.miniproject.inventorymanagement.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.admin.Home;
import com.miniproject.inventorymanagement.firebase.Category;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.firebase.Product;
import com.miniproject.inventorymanagement.firebase.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    protected List<Category> categoryList;

    public CategoryAdapter(Map<String, Category> transactionMap) {
        categoryList = new ArrayList<>(transactionMap.values());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
//        holder.productIdTextView.setText(transaction.getProductId());
//        holder.dateTextView.setText(transaction.getTimestamp().toString());
//        holder.quantityTextView.setText(String.valueOf(transaction.getQuantity()));
//        holder.priceTextView.setText(String.valueOf(transaction.getPrice()));
            holder.categoryNameTextView.setText(String.valueOf(category.getId()));


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productIdTextView;
        TextView dateTextView;
        TextView quantityTextView;
        TextView priceTextView;
        TextView categoryNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            productIdTextView = itemView.findViewById(R.id.product_id_textview);
//            dateTextView = itemView.findViewById(R.id.date_textview);
//            quantityTextView = itemView.findViewById(R.id.quantity_textview);
//            priceTextView = itemView.findViewById(R.id.price_textview);
            categoryNameTextView= itemView.findViewById(R.id.categoryNameTextView);

        }
    }



    // Queries
    @SuppressLint("NotifyDataSetChanged")
    public void setQuery(String query) {
        categoryList.clear();
        for (Category category : DatabaseHandler.getInstance().getCategoriesList()) {
            try {
                if (category.getId().toLowerCase().contains(query.toLowerCase()) ) {
                    categoryList.add(category);
                }
            }catch (Exception e){

            }
        }
        notifyDataSetChanged();
    }
    public void setList(List<Category> list) {
        categoryList = list;
        notifyDataSetChanged();
    }



    public List<Category> getList() {
        return categoryList;
    }



}

