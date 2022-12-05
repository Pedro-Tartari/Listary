package com.example.listary.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.listary.interfaces.Callback;
import com.example.listary.interfaces.DatabaseInterface;
import com.example.listary.model.Firestore;
import com.example.listary.model.ProductItem;
import com.example.listary.model.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ShoppingCartController implements DatabaseInterface<ShoppingList> {

    private Firestore connection = new Firestore();

    @Override
    public void sendDataToDatabase(ShoppingList object) {

    }

    @Override
    public void updateDataToDatabase(ShoppingList object, String documentId) {

    }

    @Override
    public void getDataFromDatabase(Callback callback) {

        CollectionReference docRef =
                connection.getDb().collection("data")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("shoppingList");

        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB success", document.getId() + " => " + document.getData());
                                String name = document.getString("name");
                                List<ProductItem> list = document.toObject(ShoppingList.class).getProductList();

                                callback.onCallback(new ShoppingList(name, list));
                            }
                        } else {
                            Log.d("DB Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
