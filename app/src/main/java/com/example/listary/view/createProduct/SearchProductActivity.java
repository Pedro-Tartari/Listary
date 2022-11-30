package com.example.listary.view.createProduct;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;

import com.example.listary.R;
import com.example.listary.adapters.SearchAdapter;
import com.example.listary.controllers.ProductController;
import com.example.listary.interfaces.Callback;
import com.example.listary.model.ProductItem;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.LoginActivity;
import com.example.listary.view.menu.MenuActivity;
import com.example.listary.view.newList.NewListActivity;

import com.google.firebase.auth.FirebaseAuth;;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {

    public static Activity self_intent;

    private EditText edSearchProduct;
    private List<ProductItem> acProductList = new ArrayList<>();
    private RecyclerView acRecyclerView;
    private SearchAdapter searchAdapter;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    ProductController productController = new ProductController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_products);

        this.setTitle(getResources().getString(R.string.consultar_Produto));

        self_intent = this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        acRecyclerView = findViewById(R.id.rvProducts);
        acRecyclerView.setHasFixedSize(true);

        getDataFromFirestore();
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

        new ItemTouchHelper(itemTouch).attachToRecyclerView(acRecyclerView);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromFirestore();;
        buildRecyclerView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
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
        acRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchAdapter = new SearchAdapter(acProductList, this);
        acRecyclerView.setAdapter(searchAdapter);
    }

    private void getDataFromFirestore() {
        productController.getDataFromDatabase(new Callback(){
            @Override
            public void onCallback(Object modelClass) {
                acProductList.add((ProductItem) modelClass);
            }

        });
    }

    public void updaterRecycle(){
        acProductList.clear();
        getDataFromFirestore();
        buildRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header_product, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.menuListary:
                intent = new Intent(this, MenuActivity.class);
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
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            default:
                return true;
        }
    }

    public void iconAddProduct(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(this, RegisterProductActivity.class);
        intent.putExtra("updateOption", 0);
        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback itemTouch = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            searchAdapter.deleteProduct(position, viewHolder);
            searchAdapter.notifyDataSetChanged();
        }
    };

}
