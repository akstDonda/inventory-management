package com.miniproject.inventorymanagement.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.Category;

import java.util.Map;

public class CategoryAdapter2 extends CategoryAdapter {
    OnItemClickListener onItemClickListener;

    public CategoryAdapter2(Map<String, Category> transactionMap, OnItemClickListener onItemClickListener) {
        super(transactionMap);
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(String categoryId); // Change the parameter to categoryId
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.categoryNameTextView);
        }

        public void bind(String categoryId, String categoryName) {
            textView.setText(categoryName);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(categoryId); // Pass categoryId
                }
            });
        }
    }
}
