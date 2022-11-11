package com.example.listary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.ShoppingList;
import com.example.listary.model.ShoppingListRecord;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HistoricAdapter extends FirestoreRecyclerAdapter<ShoppingList, HistoricAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("shoppingList");

    public HistoricAdapter(@NonNull FirestoreRecyclerOptions<ShoppingList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoricAdapter.ViewHolder holder, int position, @NonNull ShoppingList model) {
        holder.historicListName.setText("Lista: " + model.getName());
    }

    @NonNull
    @Override
    public HistoricAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_historic_item, parent, false);
        return new HistoricAdapter.ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView historicListName;

        public ViewHolder(View itemView) {
            super(itemView);

            historicListName = itemView.findViewById(R.id.tvHistoricListName);

        }
    }
}
