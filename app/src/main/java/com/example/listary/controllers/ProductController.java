package com.example.listary.controllers;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.listary.interfaces.Callback;
import com.example.listary.interfaces.DatabaseInterface;
import com.example.listary.model.Firestore;
import com.example.listary.model.Product;
import com.example.listary.model.ProductItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProductController implements DatabaseInterface <Product>{

    private Firestore connection = new Firestore();
    private long idValue;
    private String name, local, brand, id;
    private double price;

    public void returnNewProduct(EditText edRegisterProductName, EditText edRegisterProductBrand,
                                 EditText edRegisterProductLocal, EditText edRegisterProductPrice,
                                 String uID, Integer updateOption, String productId) {

        String name = edRegisterProductName.getText().toString();
        String brand = edRegisterProductBrand.getText().toString();
        String local = edRegisterProductLocal.getText().toString();
        Double price = Double.parseDouble(edRegisterProductPrice.getText().toString());

        Product product = new Product.ProductBuilder(
                name, price, 0)
                .selectBrand(brand)
                .selectLocation(local)
                .build();

        if(updateOption == 0) {
            sendDataToDatabase(product, uID);
        }else{
            updateDataToDatabase(product, uID, productId);
        }
    }

    public boolean verifyFields(EditText etRegisterProductName, EditText etRegisterProductPrice, Button btnSaveProduct) {

        String blockedCharacters = "#|%*!=+-/?[]{},@%¨.;";

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if (charSequence != null && blockedCharacters.contains(("" + charSequence))) {
                    etRegisterProductName.setError("Caracteres Inválidos");
                    return "";
                }
                return null;
            }
        };

        etRegisterProductName.setFilters(new InputFilter[]{filter});

        if (etRegisterProductName.getText().length() <= 0) {
            etRegisterProductName.setError("O nome não pode ser vazio");
            etRegisterProductName.requestFocus();
            return false;

        }

        else if (etRegisterProductPrice.getText().length() <= 0) {
            etRegisterProductPrice.setError("Insira algum valor para o produto");
            etRegisterProductName.requestFocus();
            return false;
        } else {
            return true;
        }
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
                                 name = document.getString("name");
                                 local = document.getString("location");
                                 price = document.getDouble("price");
                                 brand = document.getString("productBrand");
                                 id = document.getId();

                                 callback.onCallback(new ProductItem(name, local, brand, price, id));

                            }
                        } else {
                            Log.d("DB Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void sendDataToDatabase(Product product, String userId) {

        DocumentReference idReference = connection.getDb().collection("data")
                .document(userId).collection("reservedID")
                .document("reservedProductId");

        CollectionReference docRef =
                connection.getDb().collection("data")
                        .document(userId)
                        .collection("product");

        DocumentReference productReference = connection.getDb().collection("data")
                .document(userId).collection("product").document();

        idReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    idValue = (long) document.get("id");
                }
            }
        });

        productReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                String id = productReference.getId();
                docRef.document(id).update("id", FieldValue.increment(idValue));
                idReference.update("id", FieldValue.increment(1));
            }
        });

    }

    @Override
    public void updateDataToDatabase(Product product, String userId, String productId) {

        DocumentReference productReference = connection.getDb().collection("data")
                .document(userId).collection("product").document(productId);

        productReference.update("name", product.getName(), "productBrand", product.getProductBrand(),
                "location", product.getLocation(), "price", product.getPrice());

    }

}
