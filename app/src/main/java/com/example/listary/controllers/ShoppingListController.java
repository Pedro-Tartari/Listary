package com.example.listary.controllers;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.listary.model.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListController {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private long idValue;

    public void returnNewShoppingList(EditText listName, List productList, Float totalPrice,
                                      String uID, Integer updateOption, String listId){

        String name = listName.getText().toString();

        ShoppingList shoppingList = new ShoppingList(name,productList, totalPrice, 0);

        if(updateOption == 0) {
            sendDataToFirestore(shoppingList, uID);
        }else{
            updateDataToFirestore(shoppingList, uID, listId);
        }
    }

    private void updateDataToFirestore(ShoppingList shoppingList, String uID, String listId) {

        DocumentReference productReference = db.collection("data")
                .document(uID).collection("shoppingList").document(listId);

        productReference.update("name", shoppingList.getName(), "productList", shoppingList.getProductList(),
                "totalPrice", shoppingList.getTotalPrice());
    }

    private void sendDataToFirestore(ShoppingList shoppingList, String uID) {

        DocumentReference idReference = db.collection("data")
                .document(uID).collection("reservedID")
                .document("reservedListId");


        CollectionReference docRef =
                db.collection("data")
                        .document(uID)
                        .collection("shoppingList");


        DocumentReference shoppingListReference = db.collection("data")
                .document(uID).collection("shoppingList").document();

        idReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    idValue = (long) document.get("id");
                }
            }
        });

        shoppingListReference.set(shoppingList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                String id = shoppingListReference.getId();
                docRef.document(id).update("id", FieldValue.increment(idValue));
                idReference.update("id", FieldValue.increment(1));
            }
        });
    }
}

