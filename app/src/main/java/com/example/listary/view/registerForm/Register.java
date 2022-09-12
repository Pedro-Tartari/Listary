package com.example.listary.view.registerForm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listary.MainActivity;
import com.example.listary.R;
import com.example.listary.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText edUserName,edEmail,edPassword;
    private Button btnRegister;
    private String userName, email, password;
    private boolean isAllFieldsChecked = false;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setViewId();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    creatUser();
                    startActivity(new Intent(Register.this, MainActivity.class));
                }
            }

        });

    }

    private void setViewId() {
        edUserName = findViewById(R.id.edUserName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private boolean CheckAllFields() {
        userName = edUserName.getText().toString();
        email = edEmail.getText().toString();
        password = edPassword.getText().toString();

        if (userName.length() == 0) {
            edUserName.setError("Esse campo é obrigatório");
            return false;
        }

        if (email.length() == 0) {
            edEmail.setError("Esse campo é obrigatório");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Informe um email válido");
            return false;
        }

        if (password.length() == 0) {
            edPassword.setError("Esse campo é obrigatório");
            return false;
        } else if (password.length() < 6) {
            edPassword.setError("A senha deve conter ao menos 6 caracteres");
            return false;
        }

        return true;
    }

    private void creatUser() {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(userName, email, password);
                            db.collection("users")
                                    .add(user);
                        }
                    }
                });
    }
}
