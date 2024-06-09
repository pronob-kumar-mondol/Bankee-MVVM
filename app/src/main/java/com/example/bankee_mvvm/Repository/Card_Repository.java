package com.example.bankee_mvvm.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bankee_mvvm.Model.Card;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Card_Repository {

    private final DatabaseReference cardReference;
    private final MutableLiveData<List<Card>> cardsLiveData;

    public Card_Repository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.cardReference = database.getReference("cards");
        this.cardsLiveData = new MutableLiveData<>();
    }


    public void insert(Card card) {
        String id = cardReference.push().getKey();
        card.setId(id);
        cardReference.child(id).setValue(card);
    }

    public void update(Card card) {
        cardReference.child(card.getId()).setValue(card);
    }

    public void delete(Card card) {
        cardReference.child(card.getId()).removeValue();
    }

    public LiveData<List<Card>> getAllCards() {
        cardReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Card> cardList = new ArrayList<>();
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    Card card = cardSnapshot.getValue(Card.class);
                    cardList.add(card);
                }
                cardsLiveData.setValue(cardList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
        return cardsLiveData;
    }

    }
