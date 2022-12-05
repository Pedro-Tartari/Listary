package com.example.listary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.ShoppingList;

import java.util.List;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private List<ShoppingList> productArrayList;
    private Context context;

    public CartAdapter(List<ShoppingList> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shopping_cart_item,
                parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingList currentItem = productArrayList.get(position);
        holder.tvCartItemName.setText(currentItem.getName());
        holder.tvCartItemQuantity.setText(String.valueOf(currentItem.getDate()));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCartItemName;
        private  TextView tvCartItemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCartItemName =  itemView.findViewById(R.id.tvCartItemName);
            tvCartItemQuantity =   itemView.findViewById(R.id.tvCartItemQuantity);

        }
    }
}
