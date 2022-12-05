package com.example.listary.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.listary.interfaces.Callback;
import com.example.listary.interfaces.DatabaseInterface;
import com.example.listary.model.Firestore;
import com.example.listary.model.Pantry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class PantryController implements DatabaseInterface<Pantry> {

    private Firestore connection = new Firestore();
    private String description;

    public PantryController(Context context) {
        this.context = context;
    }

    private Context context;

    public void returnNewPantry(EditText edPantry){

         description = edPantry.getText().toString();

        Pantry pantry = new Pantry(description);

        sendDataToDatabase(pantry);
    }

    public boolean verifyFields(EditText edPantry){
        if(edPantry.getText().toString().isEmpty()){
            edPantry.setError("Campo de descrição não pode estar vazio");
            return false;
        }
        return true;
    }

    @Override
    public void sendDataToDatabase(Pantry pantry) {

            DocumentReference documentReference = connection.getDb().collection("data")
                    .document(connection.getUserId()).collection("pantry")
                    .document("FQpG5QWFiJ4xStsiDING");
            documentReference.set(pantry)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    @Override
    public void updateDataToDatabase(Pantry pantry, String documentId) {

    }

    @Override
    public void getDataFromDatabase(Callback callback) {

        DocumentReference docRef = connection.getDb().collection("data").document(connection.getUserId())
                .collection("pantry")
                .document("FQpG5QWFiJ4xStsiDING");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String descricao = document.getString("description");
                        callback.onCallback(descricao);
                    } else {
                        Log.d("n", "No such document");
                    }
                } else {
                    Log.d("erro", "get failed with ", task.getException());
                }
            }
        });

    }
}
