package com.miniproject.inventorymanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.Category;
import com.miniproject.inventorymanagement.firebase.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categoryList;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productIdTextView = itemView.findViewById(R.id.product_id_textview);
            dateTextView = itemView.findViewById(R.id.date_textview);
            quantityTextView = itemView.findViewById(R.id.quantity_textview);
            priceTextView = itemView.findViewById(R.id.price_textview);
        }
    }

    public void setList(List<Category> list) {
        categoryList = list;
        notifyDataSetChanged();
    }
}

