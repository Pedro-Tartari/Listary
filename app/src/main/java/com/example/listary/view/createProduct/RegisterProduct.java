package com.example.listary.view.createProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.listary.R;
import com.example.listary.controllers.ProductController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterProduct extends AppCompatActivity {

    private EditText edRegisterProductName,edRegisterProductBrand,
            edRegisterProductLocal,edRegisterProductPrice;

    private Button btnSaveProduct;

    private String uid;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);
        uid = user.getUid();
        setViewId(uid);

    }

    private void setViewId(String uid) {
        edRegisterProductName = findViewById(R.id.edRegisterProductName);
        edRegisterProductBrand = findViewById(R.id.edRegisterProductBrand);
        edRegisterProductLocal = findViewById(R.id.edRegisterProductLocal);
        edRegisterProductPrice = findViewById(R.id.edRegisterProductPrice);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        ProductController productController = new ProductController();
        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productController.verifyFields(edRegisterProductName, edRegisterProductPrice, btnSaveProduct)) {
                    productController.returnNewProduct(edRegisterProductName, edRegisterProductBrand,
                            edRegisterProductLocal, edRegisterProductPrice, uid);


                    Intent intent = new Intent(getApplicationContext(), SearchProductActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void btCancelRegisterProduct(View view) {
        finish();
    }

}