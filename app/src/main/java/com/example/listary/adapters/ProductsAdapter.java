package com.example.listary.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.Product;
import com.example.listary.view.createProduct.RegisterProduct;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductsAdapter extends FirestoreRecyclerAdapter<Product, ProductsAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("product");

    private AlertDialog alertDialog;

    private int updateOption = 0;

    public ProductsAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position, @NonNull Product model) {
        holder.productNameItem.setText(model.getName());
        holder.productPriceItem.setText(String.valueOf(model.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                updateProduct(position, holder);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = holder.getBindingAdapterPosition();
                deleteProduct(position, holder);
                return true;
            }
        });
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_itens_product, parent, false);
        return new ProductsAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameItem;
        private  TextView productPriceItem;

        public ViewHolder(View itemView) {
            super(itemView);

            productNameItem =  itemView.findViewById(R.id.tvItenProductName);
            productPriceItem =   itemView.findViewById(R.id.tvItenProductPrice);

        }
    }

    public void deleteProduct(Integer position, ViewHolder holder){

        String documentId = getSnapshots().getSnapshot(position).getId();
        AlertDialog.Builder alert = new AlertDialog.Builder(holder.itemView.getContext());
        alert.setCancelable(false);
        alert.setTitle("Excluir Produto");
        alert.setMessage("Você tem certeza que quer excluir esse produto?");
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                docRef.document(documentId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.itemView.getContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.itemView.getContext(), "Erro ao tentar excluir!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = alert.create();
        alertDialog.show();

    }

    public void updateProduct(Integer position, ViewHolder holder){

        updateOption = 1;
        String documentId = getSnapshots().getSnapshot(position).getId();
        Intent updateProduct  = new Intent(holder.itemView.getContext(), RegisterProduct.class);
        updateProduct.putExtra("updateOption", updateOption);
        updateProduct.putExtra("documentId", documentId);
        holder.itemView.getContext().startActivity(updateProduct);
        SearchProductActivity.self_intent.finish();

    }
}
