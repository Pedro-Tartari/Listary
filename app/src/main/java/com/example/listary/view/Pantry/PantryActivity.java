package com.example.listary.view.Pantry;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.listary.R;
import com.example.listary.controllers.PantryController;
import com.example.listary.interfaces.Callback;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.LoginActivity;
import com.example.listary.view.menu.MenuActivity;
import com.example.listary.view.newList.NewListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class PantryActivity extends AppCompatActivity {

    private Button btnSavePantry;
    private EditText edPantry;

    private PantryController pantryController = new PantryController(this);

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pentry);

        edPantry = findViewById(R.id.edPantry);

        btnSavePantry = findViewById(R.id.btnSavePentry);
        pantryController.getDataFromDatabase(new Callback() {
            @Override
            public void onCallback(Object modelClass) {
                edPantry.setText((String) modelClass);
            }
        });
        btnSavePantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pantryController.verifyFields(edPantry)) {
                    view.startAnimation(buttonClick);
                    pantryController.returnNewPantry(edPantry);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header_pantry, menu);

        this.setTitle(getResources().getString(R.string.despensa));

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

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            default:
                return true;
        }
    }
}


