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

public class RecycleViewerListAdapter extends RecyclerView.Adapter<RecycleList>{

    List<ProductItem> items;

    public RecycleViewerListAdapter(List<ProductItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecycleList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_recycle, parent, false);
        return new RecycleList(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleList holder, int position) {
        ProductItem productItem = items.get(position);
        holder.tvRecycleName.setText(productItem.getProductName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


class RecycleList extends RecyclerView.ViewHolder{

    TextView tvRecycleName, tvRecycleValue, tvRecycleLocal;
    private RecycleViewerListAdapter adapter;

    public RecycleList(@NonNull View itemView) {
        super(itemView);

        tvRecycleName = itemView.findViewById(R.id.tvRecycleName);
        tvRecycleValue = itemView.findViewById(R.id.tvRecycleValue);
        tvRecycleLocal = itemView.findViewById(R.id.tvRecycleLocal);


    }

    public RecycleList linkAdapter(RecycleViewerListAdapter adapter){
        this. adapter = adapter;
        return this;
    }
}
