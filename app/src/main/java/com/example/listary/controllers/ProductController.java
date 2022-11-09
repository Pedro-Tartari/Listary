package com.example.listary.controllers;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.listary.model.Product;
import com.example.listary.view.createProduct.RegisterProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductController {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private long idValue;

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
            sendDataToFirestore(product, uID);
        }else{
            updateDataToFirestore(product, uID, productId);
        }
    }

    private void updateDataToFirestore(Product product, String uID, String productId) {

        DocumentReference productReference = db.collection("data")
                .document(uID).collection("product").document(productId);

        productReference.update("name", product.getName(), "productBrand", product.getProductBrand(),
                "location", product.getLocation(), "price", product.getPrice());
    }

    public void sendDataToFirestore(Product product, String uID) {

        DocumentReference idReference = db.collection("data")
                .document(uID).collection("reservedID")
                .document("reservedProductId");

        CollectionReference docRef =
                db.collection("data")
                        .document(uID)
                        .collection("product");

        DocumentReference productReference = db.collection("data")
                .document(uID).collection("product").document();

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
}
