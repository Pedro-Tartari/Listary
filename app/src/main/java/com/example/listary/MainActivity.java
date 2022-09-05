package com.example.listary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.listary.view.MenuListaryActivity;
import com.example.listary.view.registerForm.Register;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setViewId();
    }

    private void setViewId() {
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btEntrar:
                startActivity(new Intent(this, MenuListaryActivity.class));
        }


    }
}