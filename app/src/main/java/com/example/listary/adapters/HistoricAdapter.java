package com.example.listary.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listary.R;

import com.example.listary.model.ShoppingListDocument;
import com.example.listary.view.historic.HistoricViewActivity;

import com.example.listary.view.historic.HistoricActivity;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HistoricAdapter extends FirestoreRecyclerAdapter<ShoppingListDocument, HistoricAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AlertDialog alertDialog;

    private int updateOption = 0;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    public HistoricAdapter(@NonNull FirestoreRecyclerOptions<ShoppingListDocument> options) {
        super(options);
    }

    @NonNull
    @Override
    public HistoricAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_historic_item, parent, false);
        return new HistoricAdapter.ViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull HistoricAdapter.ViewHolder holder, int position, @NonNull ShoppingListDocument model) {
        holder.historicListName.setText(model.shoppingList.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                int position = holder.getBindingAdapterPosition();
                String documentId = getSnapshots().getSnapshot(position).getId();

                Intent viewHistoricList = new Intent(holder.itemView.getContext(), HistoricViewActivity.class);
                viewHistoricList.putExtra("documentId",documentId);
                holder.itemView.getContext().startActivity(viewHistoricList);
                HistoricActivity.self_intent.finish();

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.startAnimation(buttonClick);
                int position = holder.getBindingAdapterPosition();
                deleteHistoricList(position, holder);
                return true;
            }
        });
    }


    private void deleteHistoricList(int position, ViewHolder holder) {

        CollectionReference docRef =
                db.collection("data")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("shoppingList");

            String documentId = getSnapshots().getSnapshot(position).getId();
            AlertDialog.Builder alert = new AlertDialog.Builder(holder.itemView.getContext());
            alert.setCancelable(false);
            alert.setTitle("Excluir Lista");

            alert.setMessage("Você tem certeza que quer excluir essa lista do histórico?");

            alert.setMessage("Você tem certeza que deseja excluir essa lista?");

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

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView historicListName;

            public ViewHolder(View itemView) {
                super(itemView);

                historicListName = itemView.findViewById(R.id.tvHistoricListName);

            }
        }
    }


