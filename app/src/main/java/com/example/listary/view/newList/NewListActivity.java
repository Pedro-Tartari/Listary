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
import com.example.listary.listners.OnAlterQuantityItem;
import com.example.listary.model.ProductItem;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.Login;
import com.example.listary.view.menu.MenuListaryActivity;
import com.example.listary.view.shoppingCart.ShoppingCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.santalu.maskara.widget.MaskEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NewListActivity extends AppCompatActivity implements OnAlterQuantityItem {

    //FireBase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("product");
   private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

                                acProductList.add(new ProductItem(name, local, brand,price, id));
                                autoCompleteProductAdapter.updateList(acProductList);
                            }
                        } else {
                            Log.d("DB Error", "Error getting documents: ", task.getException());
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
                    rvSelectedProductList, Float.parseFloat(tvListTotalPrice.getText().toString()), user.getUid(), 0, "null",
                    edShoppingListDate);

            startActivity(new Intent(NewListActivity.this, MenuListaryActivity.class));
            finish();
        }
    }

    public void toCart(View view) {
        if (shoppingListController.verifyFields(edShoppingListName, edShoppingListDate)) {
            shoppingListController.returnNewShoppingList(edShoppingListName,
                    rvSelectedProductList, Float.parseFloat(tvListTotalPrice.getText().toString()), user.getUid(), 0, "null",
                    edShoppingListDate);

            startActivity(new Intent(NewListActivity.this, ShoppingCart.class));
            finish();
        }
    }
}