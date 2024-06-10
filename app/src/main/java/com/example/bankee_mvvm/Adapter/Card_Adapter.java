package com.example.bankee_mvvm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Card_ViewModel;
import com.example.bankee_mvvm.ViewModel.Shared_ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Card_Adapter extends RecyclerView.Adapter<Card_Adapter.CardHolder> {
    private List<Card> cards = new ArrayList<>();
    private final Shared_ViewModel sharedViewModel;
    private final Card_ViewModel cardViewModel;

    public Card_Adapter(List<Card> cards, Shared_ViewModel sharedViewModel,Card_ViewModel cardViewModel) {
        this.cards = cards;
        this.sharedViewModel = sharedViewModel;
        this.cardViewModel = cardViewModel;
    }

    @NonNull
    @Override
    public Card_Adapter.CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_1, parent, false);
        return new CardHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull Card_Adapter.CardHolder holder, int position) {

        Card currentCard = cards.get(position);
        holder.cardNumberTextView.setText(currentCard.getCardNumber());
        holder.cardHolderNameTextView.setText(currentCard.getCardHolderName());
        holder.expirationDateTextView.setText(currentCard.getExpirationDate());
        holder.balanceTextView.setText(String.valueOf(currentCard.getBalance()));

        holder.itemView.setOnClickListener(v -> {


            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Choose an option")
                    .setItems(new CharSequence[]{"Set Default", "Delete"}, (dialog, which) -> {
                        switch (which) {
                            case 0: // Set Default
                                sharedViewModel.selectCard(currentCard);
                                break;
                            case 1: // Delete

                                deleteCard(v.getContext(), currentCard);
                                break;
                        }
                    });
            builder.create().show();
        });
    }

    private void deleteCard(Context context, Card currentCard) {
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to delete this card?")
                .setPositiveButton("Delete", (dialog, which) -> cardViewModel.deleteCard(currentCard))
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        private final TextView cardNumberTextView;
        private final TextView cardHolderNameTextView;
        private final TextView expirationDateTextView;
        private final TextView balanceTextView;
        public CardHolder(@NonNull View itemView) {
            super(itemView);
            cardNumberTextView = itemView.findViewById(R.id.card_no);
            cardHolderNameTextView = itemView.findViewById(R.id.holder_name);
            expirationDateTextView = itemView.findViewById(R.id.expire_date);
            balanceTextView = itemView.findViewById(R.id.balance);
        }
    }

}
