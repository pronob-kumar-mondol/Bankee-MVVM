package com.example.bankee_mvvm.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.Model.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Transaction_ViewModel extends ViewModel {
    private final FirebaseDatabase database;
    private final FirebaseAuth auth;

    public Transaction_ViewModel() {
        this.database = FirebaseDatabase.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    public void sendMoney(String receiverEmail, String reciverCardNumber, String amount) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String senderId = currentUser.getUid();



            // Retrieve sender's card number from database or other source
            DatabaseReference senderCardsRef = database.getReference("users").child(senderId).child("cards");
            senderCardsRef.orderByChild("cardNumber").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                            Card senderCard = cardSnapshot.getValue(Card.class);
                            Log.d("TransactionViewModel", "Sender card found: " + senderCard);

                            if (senderCard != null) {
                                // Validate sender and receiver cards
                                validateAndTransfer(senderId, receiverEmail, reciverCardNumber, senderCard, Double.parseDouble(amount));
                                return;
                            }
                        }
                    }
                    Log.w("TransactionViewModel", "Sender card not found");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("TransactionViewModel", "Database error: " + databaseError.getMessage());
                }
            });

        } else {
            Log.w("TransactionViewModel", "User not logged in");
            return;
        }
    }

    private void validateAndTransfer(String senderId, String receiverEmail, String receiverCardNumber, Card senderCard, double amount) {
        DatabaseReference usersRef = database.getReference("users");

        // Find receiver by email
        usersRef.orderByChild("email").equalTo(receiverEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String receiverId = userSnapshot.getKey();

                        // Validate sender and receiver cards
                        DatabaseReference receiverCardsRef = database.getReference("users").child(receiverId).child("cards");

                        receiverCardsRef.orderByChild("cardNumber").equalTo(receiverCardNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Card _receiverCard = null;
                                    for (DataSnapshot cardSnapshot : snapshot.getChildren()) {
                                        _receiverCard = cardSnapshot.getValue(Card.class);
                                        Log.d("TransactionViewModel", "Receiver card found: " + _receiverCard);
                                        break;
                                    }

                                    if (_receiverCard != null) {
                                        // Deduct amount from sender's card
                                        if (senderCard.getBalance() >= amount) {
                                            senderCard.setBalance(senderCard.getBalance() - amount);
                                            DatabaseReference senderCardsRef = database.getReference("users").child(senderId).child("cards");
                                            Log.d("TransactionViewModel", "Sender card found: " + senderCardsRef);
                                            Card final_receiverCard = _receiverCard;
                                            Log.d("TransactionViewModel", "Sender card Id Found: " + senderCard.getId());
                                            Card final_receiverCard1 = _receiverCard;
                                            senderCardsRef.child(senderCard.getId()).setValue(senderCard)
                                                    .addOnSuccessListener(aVoid -> {
                                                        // Add amount to receiver's card
                                                        final_receiverCard.setBalance(final_receiverCard.getBalance() + amount);
                                                        DatabaseReference receiverCardsRef = database.getReference("users").child(receiverId).child("cards");
                                                        receiverCardsRef.child(final_receiverCard1.getId()).setValue(final_receiverCard)
                                                                .addOnSuccessListener(aVoid1 -> {
                                                                    // Record the transaction
                                                                    recordTransaction(senderId, receiverId, amount);
                                                                })
                                                                .addOnFailureListener(e -> Log.e("TransactionViewModel", "Failed to update receiver's card", e));
                                                    })
                                                    .addOnFailureListener(e -> Log.e("TransactionViewModel", "Failed to update sender's card", e));
                                        } else {
                                            Log.w("TransactionViewModel", "Insufficient balance");
                                        }
                                    } else {
                                        Log.w("TransactionViewModel", "Receiver card not found");
                                    }
                                } else {
                                    Log.w("TransactionViewModel", "Receiver card not found");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.e("TransactionViewModel", "Database error: " + error.getMessage());
                            }
                        });
                        break;
                    }
                } else {
                    Log.w("TransactionViewModel", "Receiver not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TransactionViewModel", "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void recordTransaction(String senderId, String receiverId, double amount) {
        DatabaseReference transactionsRef = database.getReference("transactions");

        String transactionId = transactionsRef.push().getKey();
        if (transactionId != null) {
            // Record the transaction
            Transaction transaction = new Transaction(transactionId, senderId, receiverId, amount, System.currentTimeMillis(), "send_money");

            transactionsRef.child(transactionId).setValue(transaction)
                    .addOnSuccessListener(aVoid -> Log.d("TransactionViewModel", "Transaction recorded successfully"))
                    .addOnFailureListener(e -> Log.e("TransactionViewModel", "Failed to record transaction", e));
        }
    }
}
