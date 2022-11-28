package com.example.listary.view.newList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.listary.R;
import com.example.listary.adapters.AutoCompleteProductAdapter;
import com.example.listary.adapters.RecycleViewerShoppingAdapter;
import com.example.listary.controllers.ShoppingListController;
import com.example.listary.interfaces.Callback;
import com.example.listary.listeners.OnAlterQuantityItem;
import com.example.listary.model.ProductItem;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.RegisterProductActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.LoginActivity;
import com.example.listary.view.menu.MenuActivity;
import com.example.listary.view.shoppingCart.ShoppingCartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.santalu.maskara.widget.MaskEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NewListActivity extends AppCompatActivity implements OnAlterQuantityItem {

    //AutoComplete
    private AutoCompleteTextView acProduct;
    private AutoCompleteProductAdapter autoCompleteProductAdapter;
    private List<ProductItem> acProductList = new ArrayList<>();

    //Recyle
    private RecyclerView rvNewShoppingList;
    private RecycleViewerShoppingAdapter recycleViewerShoppingAdapter;
    private List<ProductItem> rvSelectedProductList = new ArrayList<>();

    //View
    private TextView tvListTotalPrice;
    private EditText edShoppingListName;
    private MaskEditText edShoppingListDate;
    private Button btnSaveList;

    //Controller
    private ShoppingListController shoppingListController = new ShoppingListController();

    //Date
    //private Date currentTime = Calendar.getInstance().getTime();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate currentTime = LocalDate.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        this.setTitle(getResources().getString(R.string.nova_lista));
        setViewId();

        getDataFromFire();

        autoCompleteProductAdapter = new AutoCompleteProductAdapter(this, acProductList);
        acProduct.setAdapter(autoCompleteProductAdapter);
        acProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                rvSelectedProductList.add(acProductList.get(position));
                atualizaLista();
            }
        });
        atualizaLista();
    }

    public void atualizaLista(){
        //Aplica adapter no Recyle e mostra a lista selecionadaq
        rvNewShoppingList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycleViewerShoppingAdapter = new RecycleViewerShoppingAdapter(rvSelectedProductList, this);
        rvNewShoppingList.setAdapter(recycleViewerShoppingAdapter);

    }

    private void setViewId() {
        acProduct = findViewById(R.id.acProduct);
        rvNewShoppingList = findViewById(R.id.rvNewShoppingList);
        tvListTotalPrice = findViewById(R.id.tvTotalPrice);
        btnSaveList = findViewById(R.id.btnSaveList);
        edShoppingListName = findViewById(R.id.edShoppingListName);
        edShoppingListDate = findViewById(R.id.edShoppingListDate);
        edShoppingListDate.setText(currentTime.format(formatter));
    }

    private void getDataFromFire() {
        shoppingListController.getDataFromDatabase(new Callback() {
            @Override
            public void onCallback(Object modelClass) {
                acProductList.add((ProductItem) modelClass);
                autoCompleteProductAdapter.updateList(acProductList);
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
                intent = new Intent(this, MenuActivity.class);
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
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            default:
                return true;
        }
    }

    @Override
    public void onAlterQuantityItem(int position, double quantity) {
        ProductItem prdItem = rvSelectedProductList.get(position);
        prdItem.setProductQuantity(quantity);
        prdItem.setProductTotalPrice(prdItem.getProductPrice() * prdItem.getProductQuantity());
        rvSelectedProductList.set(position, prdItem);

        double valorTotal = 0;
        for (ProductItem item:rvSelectedProductList) {
            valorTotal += item.getProductPrice() * item.getProductQuantity();
        }

        tvListTotalPrice.setText(String.valueOf(valorTotal));
        Log.e("Produto","" + rvSelectedProductList.get(0));
    }

    public void sendListToFirestore(View view) {
        if (shoppingListController.verifyFields(edShoppingListName, edShoppingListDate)) {
            shoppingListController.returnNewShoppingList(edShoppingListName,
                    rvSelectedProductList, Float.parseFloat(tvListTotalPrice.getText().toString()),
                    0, "null",
                    edShoppingListDate);

            startActivity(new Intent(NewListActivity.this, MenuActivity.class));
            finish();
        }
    }

    public void toCart(View view) {
        if (shoppingListController.verifyFields(edShoppingListName, edShoppingListDate)) {
            shoppingListController.returnNewShoppingList(edShoppingListName,
                    rvSelectedProductList, Float.parseFloat(tvListTotalPrice.getText().toString()), 0, "null",
                    edShoppingListDate);

            startActivity(new Intent(NewListActivity.this, ShoppingCartActivity.class));
            finish();
        }
    }

    public void iconAddProductFromList(View view) {
        Intent intent = new Intent(this, RegisterProductActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}