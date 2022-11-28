package com.example.listary.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.listary.model.Firestore;
import com.example.listary.model.ReservedId;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MenuController {

   private Firestore connection = new Firestore();

    public void verifyReservedId(){

        CollectionReference docRef =
                connection.getDb().collection("data")
                        .document(connection.getUserId())
                        .collection("reservedID");

                docRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() > 0) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Log.d("Resultado", "Room already exists, start the chat");
                                }
                            } else {
                                DocumentReference idReference =  connection.getDb().collection("data")
                                        .document(connection.getUserId()).collection("reservedID")
                                        .document("reservedProductId");

                                ReservedId reservedId = new ReservedId(0);
                                idReference.set(reservedId);

                                DocumentReference idListReference =  connection.getDb().collection("data")
                                        .document(connection.getUserId()).collection("reservedID")
                                        .document("reservedListId");

                                ReservedId reservedListId = new ReservedId(0);
                                idListReference.set(reservedListId);
                            }
                        } else {
                            Log.d("FTAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
