package com.example.listary.view.newList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.ProductItem;

import java.util.List;

public class RecycleViewerListAdapter extends RecyclerView.Adapter<RecycleViewerListAdapter.ViewHolder> {

    List<ProductItem> items;

    public RecycleViewerListAdapter(List<ProductItem> items) {
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRecycleName, tvRecycleValue, tvRecycleLocal;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecycleName = itemView.findViewById(R.id.tvRecycleName);
            tvRecycleValue = itemView.findViewById(R.id.tvRecycleValue);
            tvRecycleLocal = itemView.findViewById(R.id.tvRecycleLocal);

        }
    }
}


