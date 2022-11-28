package com.example.listary.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.listeners.OnAlterQuantityItem;
import com.example.listary.model.ProductItem;

import java.util.List;

public class RecycleViewerShoppingAdapter extends RecyclerView.Adapter<RecycleViewerShoppingAdapter.ViewHolder> {

    private List<ProductItem> items;

    private OnAlterQuantityItem onAlterQuantityItem;

    public RecycleViewerShoppingAdapter(List<ProductItem> items, OnAlterQuantityItem onAlterQuantityItem) {
        this.items = items;
        this.onAlterQuantityItem = onAlterQuantityItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_recycle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvRecycleName.setText(items.get(position).getProductName());
        holder.tvRecycleLocal.setText(items.get(position).getProductLocal());
        holder.tvRecycleValue.setText(Double.toString( items.get(position).getProductPrice()));
        holder.edRecycleQuantity.setText(String.valueOf(items.get(position).getProductQuantity()));
        holder.edRecycleQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!holder.edRecycleQuantity.getText().toString().isEmpty()) {
                    onAlterQuantityItem.onAlterQuantityItem(
                            holder.getBindingAdapterPosition(), Double.parseDouble(holder.edRecycleQuantity.getText().toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRecycleName, tvRecycleValue, tvRecycleLocal;
        private EditText edRecycleQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecycleName = itemView.findViewById(R.id.tvRecycleName);
            tvRecycleValue = itemView.findViewById(R.id.tvRecycleValue);
            tvRecycleLocal = itemView.findViewById(R.id.tvRecycleLocal);
            edRecycleQuantity = itemView.findViewById(R.id.edRecycleQuantity);

        }
    }
}


