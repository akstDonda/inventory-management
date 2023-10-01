package com.miniproject.inventorymanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.Category;

import java.util.Map;

public class CategoryAdapterForDashboard extends CategoryAdapter {
    private OnItemClickListener onItemClickListener; // Define an interface for item click callbacks

    public CategoryAdapterForDashboard(Map<String, Category> categories) {
        super(categories);
    }

    // Define an interface for item click callbacks
    public interface OnItemClickListener {
        void onItemClick(String categoryId);
        void onItemClick(View view, int position);
    }

    // Provide a way to set the listener from outside the adapter
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category2, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        // Set an OnClickListener on the item view
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                onItemClickListener.onItemClick(categoryList.get(position).getId());
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
                Category c =  categoryList.get(viewHolder.getAdapterPosition());

            }
        });

        return viewHolder;
    }
}
