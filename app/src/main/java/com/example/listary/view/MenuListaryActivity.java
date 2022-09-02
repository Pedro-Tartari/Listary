package com.example.listary.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.listary.R;
import com.example.listary.view.searchProduct.SearchProductActivity;

public class MenuListaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listary);
    }

    public void abrirTelaProdutos(View view) {
        Intent intent = new Intent(this, SearchProductActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void abrirTelaCadastroLista(View view) {
        Intent intent = new Intent(this, NovaListaActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void abrirTelaHistorico(View view) {
        Intent intent = new Intent(this, HistoricoActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void  abrirTelaDespensa(View view) {
        Intent intent = new Intent(this, DespensaActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }




}