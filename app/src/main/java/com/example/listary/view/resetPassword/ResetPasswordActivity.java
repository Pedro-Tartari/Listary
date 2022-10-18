package com.example.listary.view.resetPassword;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edResetEmail;
    private Button btnResetPassword;
    FirebaseAuth auth;
    private String email;
    private boolean isAllFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setViewId();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    resetPassword();
                }
            }
        });
    }

    private void resetPassword() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this, "Verifique seu email", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPasswordActivity.this, "Algo deu errado, tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean CheckAllFields() {
        email = edResetEmail.getText().toString();
        if (email.length() == 0) {
            edResetEmail.setError("Esse campo é obrigatório");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edResetEmail.setError("Informe um email válido");
            return false;
        }
        return true;
    }

    private void setViewId() {
        edResetEmail = findViewById(R.id.edResetEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        auth = FirebaseAuth.getInstance();
    }
}
