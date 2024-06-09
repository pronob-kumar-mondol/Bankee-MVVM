package com.example.bankee_mvvm.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bankee_mvvm.Model.Card;

public class Shared_ViewModel extends ViewModel {
    private MutableLiveData<Card> selectedCard = new MutableLiveData<>();

    public void selectCard(Card card) {
        selectedCard.setValue(card);
    }

    public LiveData<Card> getSelectedCard() {
        return selectedCard;
    }
}
