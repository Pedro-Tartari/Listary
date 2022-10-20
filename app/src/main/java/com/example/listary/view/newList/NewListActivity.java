package com.example.listary.view.newList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.listary.R;
import com.example.listary.adapters.ProductsAdapter;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.Login;
import com.example.listary.view.menu.MenuListaryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewListActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    private String uid;

    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("product");

    private AutoCompleteTextView ac_tv_Product;

    List<ProductItem> productItemsList = new ArrayList<>();

    AutoCompleteProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        ac_tv_Product = findViewById(R.id.ac_tv_Product);

        getDataFromFire();

        adapter = new AutoCompleteProductAdapter(this, productItemsList);
        ac_tv_Product.setAdapter(adapter);

    }

    private void fillList() {

        System.out.println("tteste" + productItemsList.toString());

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

                                adapter.updateList(productItemsList);
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