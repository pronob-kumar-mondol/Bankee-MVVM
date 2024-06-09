package com.example.bankee_mvvm.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.Repository.Card_Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Card_ViewModel extends AndroidViewModel {

    private MutableLiveData<List<Card>> cardsLiveData;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;

    public Card_ViewModel(@NonNull Application application) {
        super(application);
        cardsLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        loadCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return cardsLiveData;
    }

    private void loadCards() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            database.getReference("users").child(userId).child("cards")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Card> cardList = new ArrayList<>();
                            for (DataSnapshot cardSnapshot : snapshot.getChildren()) {
                                Card card = cardSnapshot.getValue(Card.class);
                                cardList.add(card);
                            }
                            cardsLiveData.setValue(cardList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
        } else {
            // Handle the case where the user is not logged in
        }
    }

    public void insert(Card card) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String cardId = database.getReference("users").child(userId).child("cards").push().getKey();
            Log.d("Card_ViewModel", "Inserting card with ID: " + cardId); // Log before insertion
            database.getReference("users").child(userId).child("cards").child(cardId).setValue(card)
                    .addOnSuccessListener(aVoid -> Log.d("Card_ViewModel", "Card inserted successfully")) // Log on success
                    .addOnFailureListener(e -> Log.e("Card_ViewModel", "Failed to insert card", e)); // Log on failure
        } else {
            Log.w("Card_ViewModel", "User not logged in, cannot insert card"); // Log if user is not logged in
        }
    }
}
