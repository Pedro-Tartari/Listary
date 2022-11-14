package com.example.listary.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.listary.model.Pastry;
import com.example.listary.view.Pantry.PantryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PantryController {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public void show(EditText edPantry, String uID) {
        DocumentReference docRef = db.collection("data").document(uID).collection("pantry").document("FQpG5QWFiJ4xStsiDING");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String banco = document.getString("description");
                        edPantry.setText(banco);
                        edPantry.setSelection(edPantry.getText().length());
                    } else {
                        Log.d("n", "No such document");
                    }
                } else {
                    Log.d("erro", "get failed with ", task.getException());
                }
            }
        });
    }

    public void post(EditText edPantry, String uID, Context context) {
        String description = edPantry.getText().toString();
        Pastry pantry = new Pastry(
                description
        );

        if (description.isEmpty()) {
            edPantry.setError("Campo de descrição não pode estar vazio");
        } else {
            DocumentReference documentReference = db.collection("data").document(uID).collection("pantry").document("FQpG5QWFiJ4xStsiDING");
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
    }
}
