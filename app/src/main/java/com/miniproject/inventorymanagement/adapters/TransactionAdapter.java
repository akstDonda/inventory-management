package com.miniproject.inventorymanagement.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.firebase.Product;
import com.miniproject.inventorymanagement.firebase.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactionList;

    public TransactionAdapter(Map<String, Transaction> transactionMap) {
        transactionList = new ArrayList<>(transactionMap.values());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.productIdTextView.setText(transaction.getProductId());
        holder.dateTextView.setText(transaction.getTimestamp().toString());
        holder.quantityTextView.setText(String.valueOf(transaction.getQuantity()));
        holder.priceTextView.setText(String.valueOf(transaction.getPrice()));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
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

    public void setList(List<Transaction> list) {
        transactionList = list;
        notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setQuery(String query) {
        transactionList.clear();
        Map<String, Product> productList = DatabaseHandler.getInstance().getProducts();
        for (Transaction transaction : DatabaseHandler.getInstance().getTransactionsList()) {
            Product p = productList.get(transaction.getProductId());
            assert p != null;
            if (p.getId().contains(query) || p.getName().contains(query) || p.getDescription().contains(query)){
                transactionList.add(transaction);
            }
        }
        notifyDataSetChanged();
    }
}

