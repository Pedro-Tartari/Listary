package com.example.listary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.listary.view.menu.MenuListaryActivity;

import com.example.listary.view.registerForm.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView register;
    private EditText edEmailLogin,edPasswordLogin;
    private Button btnLoginUser;
    private FirebaseAuth auth;
    private String email,password;
    private boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        auth = FirebaseAuth.getInstance();
        setViewId();
    }

    private void setViewId() {
        edEmailLogin = findViewById(R.id.edEmailLogin);
        edPasswordLogin = findViewById(R.id.edPasswordLogin);


        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);



        btnLoginUser = (Button) findViewById(R.id.btnLoginUser);
        btnLoginUser.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btnLoginUser:
                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    signIn();

                }
              break;
        }
    }



    private boolean CheckAllFields() {
        email = edEmailLogin.getText().toString();
        password = edPasswordLogin.getText().toString();



        if (email.length() == 0) {
            edEmailLogin.setError("Email is required");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmailLogin.setError("Informe um email valido");
            return false;
        }

        if (password.length() == 0) {
            edPasswordLogin.setError("Password is required");
            return false;
        } else if (password.length() < 8) {
            edPasswordLogin.setError("Password must be minimum 8 characters");
            return false;
        }

        return true;
    }


    private void signIn() {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Colar aqui a validação no email
                            startActivity(new Intent(MainActivity.this, MenuListaryActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Erro ao encontrar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

// envia um email para o cadastrado pedindo a validação

//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            if (user.isEmailVerified()){
//                                    startActivity(new Intent(MainActivity.this, MenuListaryActivity.class));
//        }else{
//        user.sendEmailVerification();
//        Toast.makeText(MainActivity.this, "Verifique seu email", Toast.LENGTH_LONG).show();
//        }