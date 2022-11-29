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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;
import com.example.listary.model.ProductItem;
import com.example.listary.view.createProduct.RegisterProductActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<ProductItem> productArrayList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("product");

    private AlertDialog alertDialog;
    private int updateOption = 0;

    SearchProductActivity searchProductActivity;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_itens_product,
                parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem currentItem = productArrayList.get(position);
        holder.productNameItem.setText(currentItem.getProductName());
        holder.productPriceItem.setText(String.valueOf(currentItem.getProductPrice()));
        holder.tvCadProdLocal.setText(currentItem.getProductLocal());
        holder.tvCadProdMarca.setText(currentItem.getProductBrand());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                updateProduct(position, holder);
            }
        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                int position = holder.getBindingAdapterPosition();
//                deleteProduct(position, holder);
//                return true;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameItem;
        private  TextView productPriceItem;
        private TextView tvCadProdLocal;
        private TextView tvCadProdMarca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCadProdLocal = itemView.findViewById(R.id.tvCadProdLocal);
            productNameItem =  itemView.findViewById(R.id.tvItenProductName);
            productPriceItem =   itemView.findViewById(R.id.tvItenProductPrice);
            tvCadProdMarca = itemView.findViewById(R.id.tvCadProdMarca);

        }
    }

    public SearchAdapter(List<ProductItem> productArrayList, SearchProductActivity searchProductActivity) {
        this.productArrayList = productArrayList;
        this.searchProductActivity = searchProductActivity;

    }

    public void filterList(List<ProductItem> filteredList) {
        productArrayList = filteredList;
        notifyDataSetChanged();
    }

    public void deleteProduct(Integer position, RecyclerView.ViewHolder holder){

        ProductItem currentItem = productArrayList.get(position);
        String documentId = currentItem.getId();
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
                                searchProductActivity.updaterRecycle();
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

    public void updateProduct(Integer position, SearchAdapter.ViewHolder holder){
        ProductItem currentItem = productArrayList.get(position);
        updateOption = 1;
        String documentId = currentItem.getId();
        Intent updateProduct  = new Intent(holder.itemView.getContext(), RegisterProductActivity.class);
        updateProduct.putExtra("updateOption", updateOption);
        updateProduct.putExtra("documentId", documentId);
        holder.itemView.getContext().startActivity(updateProduct);
        SearchProductActivity.self_intent.finish();

    }
}
