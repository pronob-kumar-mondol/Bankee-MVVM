package com.example.bankee_mvvm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Shared_ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Card_Adapter extends RecyclerView.Adapter<Card_Adapter.CardHolder> {
    private List<Card> cards = new ArrayList<>();
    private Shared_ViewModel sharedViewModel;

    public Card_Adapter(List<Card> cards) {
        this.cards = cards;
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
            sharedViewModel.selectCard(currentCard);
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        private TextView cardNumberTextView;
        private TextView cardHolderNameTextView;
        private TextView expirationDateTextView;
        private TextView balanceTextView;
        public CardHolder(@NonNull View itemView) {
            super(itemView);
            cardNumberTextView = itemView.findViewById(R.id.card_no);
            cardHolderNameTextView = itemView.findViewById(R.id.holder_name);
            expirationDateTextView = itemView.findViewById(R.id.expire_date);
            balanceTextView = itemView.findViewById(R.id.balance);
        }
    }
}
