package com.example.listary.view.createProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.listary.R;
import com.example.listary.adapters.ProductsAdapter;
import com.example.listary.model.Product;
import com.example.listary.view.loginForm.Login;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchProductActivity extends AppCompatActivity {

    private ProductsAdapter listAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("product");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_products);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,

                DividerItemDecoration.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Query query = docRef.orderBy("name",
                Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Product> options =
                new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();

        listAdapter = new ProductsAdapter(options);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        listAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.novaLista:
                Toast.makeText(this, "Voce clicou em Novo lista", Toast.LENGTH_LONG).show();
                return true;


            case R.id.consultarListas:
                Toast.makeText(this, "Voce clicou em Consultar Listas", Toast.LENGTH_LONG).show();
                return true;

            case R.id.despensa:
                Toast.makeText(this, "Voce clicou em Despensa", Toast.LENGTH_LONG).show();
                return true;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Login.class));
            default:
                return true;
        }
    }

    public void iconAddProduct(View view) {
        Intent intent = new Intent(this, RegisterProduct.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
