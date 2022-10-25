package com.example.listary.view.newList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.listary.R;
import com.example.listary.adapters.AutoCompleteProductAdapter;
import com.example.listary.model.ProductItem;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.Login;
import com.example.listary.view.menu.MenuListaryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewListActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("product");

    private AutoCompleteTextView ac_tv_Product;
    private AutoCompleteProductAdapter autoCompleteProductAdapter;

    private RecyclerView rvShoppingList;
    private RecycleViewerListAdapter recycleViewerListAdapter;

    private List<ProductItem> productItemsList = new ArrayList<>();

    List<ProductItem> teste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        getDataFromFire();

        ac_tv_Product = findViewById(R.id.ac_tv_Product);
        rvShoppingList = findViewById(R.id.rvShoppingList);
        rvShoppingList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        autoCompleteProductAdapter = new AutoCompleteProductAdapter(this, productItemsList);
        ac_tv_Product.setAdapter(autoCompleteProductAdapter);

//        recycleViewerListAdapter = new RecycleViewerListAdapter(productItemsList);

        ac_tv_Product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                addItemToRecycle(position);
            }
        });

        recycleViewerListAdapter = new RecycleViewerListAdapter(teste);
        rvShoppingList.setAdapter(recycleViewerListAdapter);
    }

    private void addItemToRecycle(int position) {

        teste.add(productItemsList.get(position));



       Log.e("ItemCount", String.valueOf(recycleViewerListAdapter.getItemCount()));

    }

    private void getDataFromFire() {
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (isFinishing() || isDestroyed()) return;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Deu certo", document.getId() + " => " + document.getData());
                                String name = document.getString("name");
                                String local = document.getString("location");

                                productItemsList.add(new ProductItem(name, local));

                                autoCompleteProductAdapter.updateList(productItemsList);
                            }
                        } else {
                            Log.d("deu errado", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header_new_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.menuListary:
                intent = new Intent(this, MenuListaryActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.consultarProduto:
                intent = new Intent(this, SearchProductActivity.class);
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
}