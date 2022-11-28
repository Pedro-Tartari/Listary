package com.example.listary.controllers;

import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.listary.interfaces.Callback;
import com.example.listary.interfaces.DatabaseInterface;
import com.example.listary.model.Firestore;
import com.example.listary.model.ProductItem;
import com.example.listary.model.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.santalu.maskara.widget.MaskEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingListController implements DatabaseInterface<ShoppingList> {

    private long idValue;
    private Map<String, Object> data = new HashMap<>();
    private Firestore connection = new Firestore();

    public void returnNewShoppingList(EditText listName, List productList, Float totalPrice,
                                       Integer updateOption, String listId, MaskEditText listDate){

        String name = listName.getText().toString();
        String date = listDate.getText().toString();

        ShoppingList shoppingList = new ShoppingList(name,productList, totalPrice, 0, date);
        Log.e("LTM","" + shoppingList.getDate());

        if(updateOption == 0) {
            sendDataToDatabase(shoppingList);
        }else{
            updateDataToDatabase(shoppingList, listId);
        }
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

    public void getDataFromDatabase(Callback callback){

        CollectionReference docRef =
                connection.getDb().collection("data")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("product");

        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB success", document.getId() + " => " + document.getData());
                                String name = document.getString("name");
                                String local = document.getString("location");
                                Double price = document.getDouble("price");
                                String brand = document.getString("productBrand");
                                String id = document.getId();

                                callback.onCallback(new ProductItem(name, local, brand, price, id));

                            }
                        } else {
                            Log.d("DB Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void sendDataToDatabase(ShoppingList shoppingList) {

        DocumentReference idReference = connection.getDb().collection("data")
                .document(connection.getUserId()).collection("reservedID")
                .document("reservedListId");

        CollectionReference docRef =
                connection.getDb().collection("data")
                        .document(connection.getUserId())
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

    @Override
    public void updateDataToDatabase(ShoppingList shoppingList, String documentId) {

        DocumentReference productReference =  connection.getDb().collection("data")
                .document(connection.getUserId()).collection("shoppingList").document(documentId);

        productReference.update("name", shoppingList.getName(), "productList", shoppingList.getProductList(),
                "totalPrice", shoppingList.getTotalPrice());

    }
}

