package com.example.listary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.ShoppingList;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HistoricViewAdapter extends FirestoreRecyclerAdapter<ShoppingList, HistoricViewAdapter.ViewHolder> {

    public HistoricViewAdapter(@NonNull FirestoreRecyclerOptions<ShoppingList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ShoppingList model) {
        holder.productName.setText(model.getProductList().get(position).getProductName());
       /* holder.productLocal.setText(model.getProductList().get(position).getProductLocal());
        holder.productPrice.setText(String.valueOf(model.getProductList().get(position).getProductPrice()));
        holder.productQuantity.setText(String.valueOf(model.getProductList().get(position).getProductQuantity()));*/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_historic_view_list_item, parent, false);
        return new HistoricViewAdapter.ViewHolder(view);
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
