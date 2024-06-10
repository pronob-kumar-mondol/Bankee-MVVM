package com.example.bankee_mvvm.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.Repository.Card_Repository;

public class Shared_ViewModel extends ViewModel {
    private final Card_Repository cardRepository;
    private MutableLiveData<Card> selectedCard = new MutableLiveData<>();

    public Shared_ViewModel() {
        cardRepository = new Card_Repository();
    }

    public void selectCard(Card card) {
        selectedCard.setValue(card);
    }

    public LiveData<Card> getSelectedCard() {
        return selectedCard;
    }

    public void deleteCard(Card card) {
        cardRepository.delete(card);
    }
}
