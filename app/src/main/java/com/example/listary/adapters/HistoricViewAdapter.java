package com.example.listary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.ProductItem;


import java.util.ArrayList;
import java.util.List;

public class HistoricViewAdapter extends RecyclerView.Adapter<HistoricViewAdapter.ViewHolder> {

    private List<ProductItem> shoppingList;
    private Context context;

    public HistoricViewAdapter(Context context, List shoppingList) {
        this.context = context;
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public HistoricViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_historic_view_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.productName.setText(shoppingList.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productLocal;
        private TextView productPrice;
        private TextView productQuantity;

        public ViewHolder(View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.tvHistoricViewNameItem);
            productLocal = itemView.findViewById(R.id.tvHistoricViewLocalItem);
            productPrice = itemView.findViewById(R.id.tvHistoricViewPriceItem);
            productQuantity = itemView.findViewById(R.id.tvHistoricViewQuantityItem);

        }
    }
}
