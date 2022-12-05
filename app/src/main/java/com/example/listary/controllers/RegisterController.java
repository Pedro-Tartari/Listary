package com.example.listary.controllers;
import android.util.Patterns;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.listary.model.Firestore;
import com.example.listary.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterController {

    private String userName, email, password;
    private Firestore firestore = new Firestore();
    private boolean result;

    public boolean checkAllFields(EditText edUserName, EditText edEmail, EditText edPassword) {

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

    public boolean createUserFirestore(){
        firestore.getAuth().fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            firestore.getAuth().createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                User user = new User(userName, email, password);
                                                firestore.getDb().collection("users")
                                                        .add(user);
                                                FirebaseAuth.getInstance().signOut();
                                                result = true;

                                            }
                                        }
                                    });

                        }
                        else {
                            result = false;

                        }

                    }
                });
        return result;
    }
}
