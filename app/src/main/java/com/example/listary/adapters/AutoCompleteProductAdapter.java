package com.example.listary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.listary.R;
import com.example.listary.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteProductAdapter extends ArrayAdapter<ProductItem> {

    private List<ProductItem> productListFull;

    public AutoCompleteProductAdapter(@NonNull Context context, @NonNull List<ProductItem> productList) {
        super(context, 0, productList);
        productListFull = new ArrayList<>(productList);

    }

    public void updateList(@NonNull List<ProductItem> newList) {
        productListFull = new ArrayList<>(newList);
        clear();
        addAll(productListFull);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return productFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_auto_complete_row, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.tvCustomName);
        TextView textViewLocal = convertView.findViewById(R.id.tvCustomLocal);

        ProductItem productItem = getItem(position);

        if (productItem != null){
            textViewName.setText(productItem.getProductName());
            textViewLocal.setText(productItem.getProductLocal());
        }

        return convertView;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ProductItem> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(productListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProductItem item : productListFull) {
                    if (item.getProductName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ProductItem) resultValue).getProductName();
        }
    };
}
