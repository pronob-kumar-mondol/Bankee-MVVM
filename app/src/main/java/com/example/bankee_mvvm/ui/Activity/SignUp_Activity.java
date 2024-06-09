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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bankee_mvvm.MainActivity;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Auth_ViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_Activity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, fullNameEditText;
    private TextView logIn;
    private Button signupButton;
    private ProgressBar progressBar;
    private Auth_ViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        fullNameEditText = findViewById(R.id.fullNameEditTxt);
        emailEditText = findViewById(R.id.emailEditTxt);
        passwordEditText = findViewById(R.id.passEditTxt);
        signupButton = findViewById(R.id.btn);
        progressBar = findViewById(R.id.progressBar);
        logIn=findViewById(R.id.logIn);

        authViewModel = new ViewModelProvider(this).get(Auth_ViewModel.class);



        authViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Toast.makeText(SignUp_Activity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    navigateToHome();
                }
            }
        });

        authViewModel.getAuthStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                progressBar.setVisibility(View.GONE);
                if (!isSuccess) {
                    Toast.makeText(SignUp_Activity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String fullName = fullNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (validateInputs(fullName, email, password)) {
                    authViewModel.signup(email, password, fullName);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUp_Activity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish();
        });
    }
    private boolean validateInputs(String fullName, String email, String password) {
        return !fullName.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    private void navigateToHome() {
        Intent intent = new Intent(SignUp_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}