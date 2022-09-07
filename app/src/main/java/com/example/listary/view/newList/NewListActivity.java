package com.example.listary.view.newList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.listary.R;

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
}