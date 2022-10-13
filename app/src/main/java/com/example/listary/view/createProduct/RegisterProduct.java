package com.example.listary.view.createProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listary.R;
import com.example.listary.model.Pastry;
import com.example.listary.model.Product;
import com.example.listary.view.Pantry.PantryActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterProduct extends AppCompatActivity {

    private EditText edRegisterProductName,edRegisterProductBrand,edRegisterProductLocal,edRegisterProductPrice;
    private Button btnSaveProduct;

    private String uid;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);
        uid = user.getUid();
        setViewId();


    }

    private void post() {
        String name = edRegisterProductName.getText().toString();
        String brand = edRegisterProductBrand.getText().toString();
        String local = edRegisterProductLocal.getText().toString();
        Double price = Double.parseDouble(edRegisterProductPrice.getText().toString());

        Product product = new Product.ProductBuilder(
                name,price,1
        )
                .selectBrand(brand)
                .selectLocation(local)
                .build();

        if (name.isEmpty()){
            Toast.makeText(this, "Campo não pode estar vazio", Toast.LENGTH_LONG).show();
        } else{
            DocumentReference documentReference = db.collection("data").document(uid).collection("product").document();
            documentReference.set(product)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterProduct.this, "Sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterProduct.this, "Erro", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


    private void setViewId() {
        edRegisterProductName = findViewById(R.id.edRegisterProductName);
        edRegisterProductBrand = findViewById(R.id.edRegisterProductBrand);
        edRegisterProductLocal = findViewById(R.id.edRegisterProductLocal);
        edRegisterProductPrice = findViewById(R.id.edRegisterProductPrice);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });

    }

    public void btCancelRegisterProduct(View view) {
        finish();
    }

}