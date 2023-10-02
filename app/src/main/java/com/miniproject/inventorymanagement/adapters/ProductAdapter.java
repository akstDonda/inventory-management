package com.miniproject.inventorymanagement.adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.ProductList;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.firebase.Product;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Integer lowStockLimit;
    private Boolean variableLowStock;
    private List<String> categoryList;

    public interface OnItemClickListener {
        // Implement the onItemClick method from the interface
        void onItemClick(ProductList product);

        void onItemClick(Product product);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ProductAdapter(Map<String, Product> productsMap, Integer lowStockLimit) {
        this.lowStockLimit = lowStockLimit;
        this.productList = getProductListFromMap(productsMap, lowStockLimit);
        Log.e(TAG, productList + "");
    }

    public ProductAdapter(Map<String, Product> productsMap) {
        this(productsMap, -1);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameEditText;
        private TextView productDescriptionEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameEditText = itemView.findViewById(R.id.productNameTextView);
            productDescriptionEditText = itemView.findViewById(R.id.productCurrentStockTextView);

            // Set an OnClickListener to handle item clicks
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        Product clickedProduct = productList.get(position);
                        listener.onItemClick(clickedProduct);
                    }
                }
            });
        }

        public void bind(Product product) {
            productNameEditText.setText(product.getName());
            productDescriptionEditText.setText(Integer.toString(product.getStockIn() - product.getStockOut()));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setQuery(String query) {
        productList.clear();
        for (Product product : DatabaseHandler.getInstance().getProductsList()) {
            if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                productList.add(product);
            }
        }
        notifyDataSetChanged();
    }

    public void setProductList(Map<String, Product> productsMap) {
        productList = getProductListFromMap(productsMap, lowStockLimit);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProductList(List<Product> productList) {
        this.productList.clear();
        for (Product product : productList) {
            if (categoryList == null)
                this.productList.add(product);
            else
                for (String cId : categoryList) {
                    if (cId.equals(product.getCategoryId()))
                        this.productList.add(product);
                }
        }
        notifyDataSetChanged();
    }

    public void setCategoryFilter(String categoryId) {
        if (categoryList == null)
            categoryList = new ArrayList<>();
        if (categoryList.contains(categoryId))
            categoryList.remove(categoryId);
        else if (categoryId != null && !categoryId.equals(""))
            categoryList.add(categoryId);
    }

    private ArrayList<Product> getProductListFromMap(Map<String, Product> productsMap, Integer lowStockLimit) {
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

