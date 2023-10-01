package com.miniproject.inventorymanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.Category;

import java.util.Map;

public class CategoryAdapterForDashboard extends CategoryAdapter{
    public CategoryAdapterForDashboard(Map<String, Category> categories) {
        super(categories);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category2, parent, false);
        return new ViewHolder(view);
    }


}
