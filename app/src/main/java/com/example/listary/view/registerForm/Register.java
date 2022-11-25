package com.example.listary.view.registerForm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listary.R;
import com.example.listary.controllers.RegisterController;
import com.example.listary.model.User;
import com.example.listary.view.loginForm.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;


public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText edUserName,edEmail,edPassword;
    private Button btnRegister;
    private FirebaseFirestore db;
    private RegisterController registerController = new RegisterController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setViewId();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerController.checkAllFields(edUserName, edEmail, edPassword)) {
                    createUser();
                }
            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Register.this, Login.class));
        finish();
    }

    private void setViewId() {
        edUserName = findViewById(R.id.edUserName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }


    private void createUser() {
        auth.fetchSignInMethodsForEmail(registerController.getEmail())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            auth.createUserWithEmailAndPassword(registerController.getEmail(),registerController.getPassword())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                User user = new User(registerController.getUserName(), registerController.getEmail(), registerController.getPassword());
                                                db.collection("users")
                                                        .add(user);
                                                FirebaseAuth.getInstance().signOut();
                                                startActivity(new Intent(Register.this, Login.class));
                                                finish();
                                            }
                                        }
                                    });


                        } else {
                            Toast.makeText(Register.this, "Email ja cadastrado", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}
