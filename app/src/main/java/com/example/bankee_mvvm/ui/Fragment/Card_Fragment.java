package com.example.bankee_mvvm.ui.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Card_ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Card_Fragment extends Fragment {

    private Card_ViewModel cardViewModel;
    private TextView noCardTextView;
    private ConstraintLayout cardLayout;
    private ProgressBar progressBar;
    private FloatingActionButton addCardFab, deleteCardFab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_card_, container, false);

        noCardTextView = view.findViewById(R.id.no_card_text_view);
        cardLayout = view.findViewById(R.id.my_card);
        progressBar = view.findViewById(R.id.progressBarCard);
        addCardFab = view.findViewById(R.id.add_card_fab);
        deleteCardFab = view.findViewById(R.id.delete_card_fab);

        cardViewModel = new ViewModelProvider(this).get(Card_ViewModel.class);

        cardViewModel.getAllCards().observe(getViewLifecycleOwner(), cards -> {
            if (cards.isEmpty()){
                noCardTextView.setVisibility(View.VISIBLE);
                cardLayout.setVisibility(View.GONE);
            }else{
                noCardTextView.setVisibility(View.GONE);
                cardLayout.setVisibility(View.VISIBLE);

                // Display the first card's details as an example
                Card card = cards.get(0);
                // Update the UI with card details
                TextView cardNumber = cardLayout.findViewById(R.id.card_no);
                TextView cardHolderName = cardLayout.findViewById(R.id.holder_name);
                TextView expirationDate = cardLayout.findViewById(R.id.expire_date);
                TextView cardBalance = cardLayout.findViewById(R.id.balance);

                cardNumber.setText(card.getCardNumber());
                cardHolderName.setText(card.getCardHolderName());
                expirationDate.setText(card.getExpirationDate());
                cardBalance.setText(String.format("Balance: $%.2f", card.getBalance()));
            }
        });

        addCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCardDialog();
            }
        });




        return view;
    }

    private void showAddCardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_card, null);
        builder.setView(dialogView);

        final EditText cardNumberEditText = dialogView.findViewById(R.id.cardNumberEditText);
        final EditText cardHolderNameEditText = dialogView.findViewById(R.id.cardHolderNameEditText);
        final EditText expirationDateEditText = dialogView.findViewById(R.id.expirationDateEditText);
        final EditText cvvEditText = dialogView.findViewById(R.id.cvvEditText);
        final EditText balanceEditText = dialogView.findViewById(R.id.balanceEditText);
        final Button addCardButton = dialogView.findViewById(R.id.addCardButton);

        final AlertDialog dialog = builder.create();

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String cardHolderName = cardHolderNameEditText.getText().toString().trim();
                String expirationDate = expirationDateEditText.getText().toString().trim();
                String cvv = cvvEditText.getText().toString().trim();
                String balanceStr = balanceEditText.getText().toString().trim();
                double balance = balanceStr.isEmpty() ? 0.0 : Double.parseDouble(balanceStr);

                if (!cardNumber.isEmpty() && !cardHolderName.isEmpty() && !expirationDate.isEmpty() && !cvv.isEmpty()) {
                    Card card = new Card();
                    card.setCardNumber(cardNumber);
                    card.setCardHolderName(cardHolderName);
                    card.setExpirationDate(expirationDate);
                    card.setCvv(cvv);
                    card.setBalance(balance);
                    cardViewModel.insert(card);

                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}