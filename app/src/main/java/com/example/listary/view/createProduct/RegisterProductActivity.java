package com.example.listary.view.createProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.listary.R;
import com.example.listary.controllers.ProductController;

import com.example.listary.model.Firestore;
import com.example.listary.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class RegisterProductActivity extends AppCompatActivity {

    private EditText edRegisterProductName,edRegisterProductBrand,
            edRegisterProductLocal,edRegisterProductPrice;

    private Button btnSaveProduct;

    private Firestore connection = new Firestore();

    private int updateOption = 0;

    private ProductController productController = new ProductController();

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(getResources().getString(R.string.cadastrar_produto));
        setContentView(R.layout.activity_register_product);

        edRegisterProductName = findViewById(R.id.edRegisterProductName);
        edRegisterProductBrand = findViewById(R.id.edRegisterProductBrand);
        edRegisterProductLocal = findViewById(R.id.edRegisterProductLocal);
        edRegisterProductPrice = findViewById(R.id.edRegisterProductPrice);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);

        Bundle data = getIntent().getExtras();
        String documentId = (String) data.get("documentId");
        updateOption = data.getInt("updateOption");

        if(updateOption == 0){
            setViewId(connection.getUserId(), updateOption);
        }
        else{
            setViewForUpdate(connection.getUserId(), documentId, updateOption);
        }

    }

    private void setViewId(String uid, Integer updateOption) {

        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productController.verifyFields(edRegisterProductName, edRegisterProductPrice, btnSaveProduct)) {
                    productController.returnNewProduct(edRegisterProductName, edRegisterProductBrand,
                            edRegisterProductLocal, edRegisterProductPrice, updateOption, "null");

                    view.startAnimation(buttonClick);

                    Intent intent = new Intent(getApplicationContext(), SearchProductActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void btCancelRegisterProduct(View view) {
        view.startAnimation(buttonClick);
        finish();
    }

    private void setViewForUpdate(String uid, String documentId, Integer updateOption) {

        DocumentReference docRef =
                connection.getDb().collection("data")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("product")
                        .document(documentId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Result", "DocumentSnapshot data: " + document.getData());
                        Product product = document.toObject(Product.class);
                        edRegisterProductName.setText(product.getName());
                        edRegisterProductBrand.setText(product.getProductBrand());
                        edRegisterProductLocal.setText(product.getLocation());
                        edRegisterProductPrice.setText(String.valueOf(product.getPrice()));

                    }
                }
             }
            });

        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productController.verifyFields(edRegisterProductName, edRegisterProductPrice, btnSaveProduct)) {
                    productController.returnNewProduct(edRegisterProductName, edRegisterProductBrand,
                            edRegisterProductLocal, edRegisterProductPrice, updateOption, documentId);

                    view.startAnimation(buttonClick);

                    Intent intent = new Intent(getApplicationContext(), SearchProductActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        }
    }
