package com.example.listary.view.newList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listary.R;
import com.example.listary.view.loginForm.Login;
import com.google.firebase.auth.FirebaseAuth;

public class NewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
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
                finish();
            default:
                return true;
        }
    }
}