package com.example.bankee_mvvm.ui.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bankee_mvvm.Adapter.Card_Adapter;
import com.example.bankee_mvvm.CardSpacingItemDecoration;
import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Card_ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Card_Fragment extends Fragment {

    private Card_ViewModel cardViewModel;
    private TextView noCardTextView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Card_Adapter cardAdapter;
    private FloatingActionButton addCardFab, deleteCardFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_, container, false);

        noCardTextView = view.findViewById(R.id.no_card_text_view);
        progressBar = view.findViewById(R.id.progressBarCard);
        recyclerView = view.findViewById(R.id.recyclerView);
        addCardFab = view.findViewById(R.id.add_card_fab);
        deleteCardFab = view.findViewById(R.id.delete_card_fab);

        cardViewModel = new ViewModelProvider(this).get(Card_ViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new Card_Adapter(null);
        recyclerView.setAdapter(cardAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        CardSpacingItemDecoration itemDecoration = new CardSpacingItemDecoration(spacingInPixels);
        recyclerView.addItemDecoration(itemDecoration);

        cardViewModel.getAllCards().observe(getViewLifecycleOwner(), cards -> {
            if (cards.isEmpty()) {
                noCardTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            } else {
                noCardTextView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                cardAdapter.setCards(cards);
                progressBar.setVisibility(View.GONE);
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

                if (!cardNumber.isEmpty() && !cardHolderName.isEmpty() && !expirationDate.isEmpty() && !cvv.isEmpty() && !balanceStr.isEmpty()) {
                    Card card = new Card();
                    card.setCardNumber(cardNumber);
                    card.setCardHolderName(cardHolderName);
                    card.setExpirationDate(expirationDate);
                    card.setCvv(cvv);
                    card.setBalance(balance);
                    cardViewModel.insert(card);

                    dialog.dismiss();
                } else {
                    // Show error message if any field is empty
                    // For simplicity, we can use Toast or set error on EditText fields
                    cardNumberEditText.setError("Required");
                    cardHolderNameEditText.setError("Required");
                    expirationDateEditText.setError("Required");
                    cvvEditText.setError("Required");
                    balanceEditText.setError("Required");
                }
            }
        });

        dialog.show();
    }
}