package com.example.bankee_mvvm.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bankee_mvvm.MainActivity;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Auth_ViewModel;

public class Login_Activity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private TextView signUp;
    private Button loginButton;
    private ProgressBar progressBar;
    private Auth_ViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditTxt);
        passwordEditText = findViewById(R.id.passEditTxt);
        loginButton = findViewById(R.id.btn);
        progressBar = findViewById(R.id.progressBar);
        signUp=findViewById(R.id.signUp);

        authViewModel = new ViewModelProvider(this).get(Auth_ViewModel.class);



        authViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                Toast.makeText(Login_Activity.this, "Login successful", Toast.LENGTH_SHORT).show();
                navigateToHome();
            }
        });

        authViewModel.getAuthStatus().observe(this, isSuccess -> {
            progressBar.setVisibility(View.GONE);
            if (!isSuccess) {
                Toast.makeText(Login_Activity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (validateInputs(email, password)) {
                    authViewModel.login(email, password);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login_Activity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login_Activity.this, SignUp_Activity.class);
            startActivity(intent);
        });

    }
    private boolean validateInputs(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    private void navigateToHome() {
        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authViewModel.getAuthStatus()!=null){
            Intent intent=new Intent(Login_Activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

    }


}