package com.example.listary.controllers;

import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.listary.model.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.santalu.maskara.widget.MaskEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingListController {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private long idValue;
    private Map<String, Object> data = new HashMap<>();

    public void returnNewShoppingList(EditText listName, List productList, Float totalPrice,
                                      String uID, Integer updateOption, String listId, MaskEditText listDate){

        // Verificar Nome da Lista, Data e Valor de cada Produto
        //Produto talvez tenha que verificar na Activity

        String name = listName.getText().toString();
        String date = listDate.getText().toString();

        ShoppingList shoppingList = new ShoppingList(name,productList, totalPrice, 0, date);
        Log.e("LTM","" + shoppingList.getDate());

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

        idReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    idValue = (long) document.get("id");
                }
            }
        });

        data.put("shoppingList", shoppingList);
        docRef.add(data).addOnSuccessListener(documentReference -> {
            String id = documentReference.getId();
            docRef.document(id).update("shoppingList.id", FieldValue.increment(idValue));
            idReference.update("id", FieldValue.increment(1));

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean verifyFields(EditText name, MaskEditText date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dates = date.getText().toString();

        if (name.getText().length() <= 0) {
            name.setError("O nome não pode ser vazio");
            name.requestFocus();
            return false;
        }

        try
        {
            LocalDate currentTime = LocalDate.parse(dates,formatter);

        }
        catch (Exception error)
        {
            date.setError("Data inválida");
            return false;
        }

        return true;
    }
}

