package com.example.listary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.ProductItem;

import java.util.List;

public class RecycleViewerShoppingAdapter extends RecyclerView.Adapter<RecycleViewerShoppingAdapter.ViewHolder> {

    List<ProductItem> items;
    private double productQuantity;
    private double productTotalPrice;

    public RecycleViewerShoppingAdapter(List<ProductItem> items) {
        this.items = items;
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
        productQuantity = Double.parseDouble(holder.edRecycleQuantity.getText().toString());
        items.get(position).setProductQuantity(productQuantity);
        productTotalPrice = (items.get(position).getProductPrice() * items.get(position).getProductQuantity());
        /* NAO SEI SE VAI PRECISAR DESSE COMANDO COMENTADO
        holder.edRecycleQuantity.setText(String.valueOf(items.get(position).getProductQuantity()));
         */

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


