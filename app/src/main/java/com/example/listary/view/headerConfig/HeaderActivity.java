package com.example.listary.view.headerConfig;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listary.R;

public class HeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_header, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.novaLista:
                Toast.makeText(HeaderActivity.this, "Voce clicou em Novo lista", Toast.LENGTH_LONG).show();
                break;

            case R.id.consultarListas:
                Toast.makeText(HeaderActivity.this, "Voce clicou em Consultar Listas", Toast.LENGTH_LONG).show();
                break;

            case R.id.despensa:
                Toast.makeText(HeaderActivity.this, "Voce clicou em Despensa", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
