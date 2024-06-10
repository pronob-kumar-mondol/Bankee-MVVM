package com.example.bankee_mvvm.ui.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Transaction_ViewModel;

public class SendMoney_Activity extends AppCompatActivity {

    private EditText ammount, email,cardNumber;
    private Transaction_ViewModel transactionViewModel;
    private AppCompatButton sendMoneyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_money);

        ammount = findViewById(R.id.ammount);
        email = findViewById(R.id.editText);
        cardNumber = findViewById(R.id.editText2);

        sendMoneyButton = findViewById(R.id.btn);

        transactionViewModel = new ViewModelProvider(this).get(Transaction_ViewModel.class);

        sendMoneyButton.setOnClickListener(v -> sendMoney());







    }

    private void sendMoney() {
        String amountText = ammount.getText().toString();
        String emailText = email.getText().toString();
        String cardNumberText = cardNumber.getText().toString();

        if (TextUtils.isEmpty(amountText) || TextUtils.isEmpty(emailText) ||TextUtils.isEmpty(cardNumberText)) {
            Toast.makeText(this, "Please enter both amount and receiver's email", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);

        // Call the sendMoney method from TransactionViewModel
        transactionViewModel.sendMoney(emailText,cardNumberText, amountText);

        // Optionally, you can finish the activity after sending money
        finish();
    }
}