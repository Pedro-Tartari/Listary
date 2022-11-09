package com.example.listary.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.listary.model.ReservedId;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MenuController {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void verifyReservedId(String uID){

        db.collection("data").document(uID).collection("reservedID")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() > 0) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Log.d("Resultado", "Room already exists, start the chat");
                                }
                            } else {
                                DocumentReference idReference = db.collection("data")
                                        .document(uID).collection("reservedID")
                                        .document("reservedProductId");

                                ReservedId reservedId = new ReservedId(0);
                                idReference.set(reservedId);

                                DocumentReference idListReference = db.collection("data")
                                        .document(uID).collection("reservedID")
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
