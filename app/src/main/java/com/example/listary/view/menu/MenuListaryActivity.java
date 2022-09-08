package com.example.listary.view.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.listary.R;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.newList.NewListActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.searchProduct.SearchProductActivity;

public class MenuListaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void abrirTelaProdutos(View view) {
//        Intent intent = new Intent(this, SearchProductActivity.class);
//        Bundle bundle = new Bundle();
//        intent.putExtras(bundle);
//        startActivity(intent);

    }

    public void abrirTelaCadastroLista(View view) {
        Intent intent = new Intent(this, NewListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void abrirTelaHistorico(View view) {
        Intent intent = new Intent(this, HistoricActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void  abrirTelaDespensa(View view) {
        Intent intent = new Intent(this, PantryActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }




}