package com.example.listary.view.createProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.listary.R;
import com.example.listary.adapters.SearchAdapter;
import com.example.listary.model.ProductItem;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.Login;
import com.example.listary.view.menu.MenuListaryActivity;
import com.example.listary.view.newList.NewListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {


    public static Activity self_intent;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("product");

    private EditText edSearchProduct;
    private List<ProductItem> acProductList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(getResources().getString(R.string.consultar_Produto));
        self_intent = this;
        setContentView(R.layout.activity_create_products);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mRecyclerView = findViewById(R.id.rvProducts);
        mRecyclerView.setHasFixedSize(true);


        getDataFromFire();
        buildRecyclerView();

        edSearchProduct = findViewById(R.id.edSearchProduct);
        edSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }


        });

    }


    private void filter(String text) {
        ArrayList<ProductItem> filteredList = new ArrayList<>();

        for (ProductItem item : acProductList) {
            if (item.getProductName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        searchAdapter.filterList(filteredList);
    }

    private void buildRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchAdapter = new SearchAdapter(acProductList, this);
        mRecyclerView.setAdapter(searchAdapter);
    }

    private void getDataFromFire() {
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (isFinishing() || isDestroyed()) return;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB success", document.getId() + " => " + document.getData());
                                String name = document.getString("name");
                                String local = document.getString("location");
                                Double price = document.getDouble("price");
                                String brand = document.getString("productBrand");
                                String id = document.getId();

                                acProductList.add(new ProductItem(name, local, brand ,price, id));
                            }
                        } else {
                            Log.d("DB Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void updaterRecycle(){
        acProductList.clear();
        getDataFromFire();
        buildRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header_product, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.menuListary:
                intent = new Intent(this, MenuListaryActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.novaLista:
                intent = new Intent(this, NewListActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.historic:
                intent = new Intent(this, HistoricActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.despensa:
                intent = new Intent(this, PantryActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Login.class));
                finish();
            default:
                return true;
        }
    }

    public void iconAddProduct(View view) {
        Intent intent = new Intent(this, RegisterProduct.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
