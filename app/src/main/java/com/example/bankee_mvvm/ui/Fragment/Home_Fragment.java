package com.example.bankee_mvvm.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Shared_ViewModel;


public class Home_Fragment extends Fragment {

    private Shared_ViewModel sharedViewModel;
    private TextView balance,card_no,holder_name,expire_date;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home_, container, false);


        balance = v.findViewById(R.id.balance);
        card_no = v.findViewById(R.id.card_no);
        holder_name = v.findViewById(R.id.holder_name);
        expire_date = v.findViewById(R.id.expire_date);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(Shared_ViewModel.class);

        sharedViewModel.getSelectedCard().observe(getViewLifecycleOwner(), new Observer<Card>() {
            @Override
            public void onChanged(Card selectedCard) {
                // Update UI with selected card data
                if (selectedCard != null) {
                    // Update UI elements with card data
                    holder_name.setText(selectedCard.getCardHolderName());
                    balance.setText(String.valueOf(selectedCard.getBalance()));
                    card_no.setText(selectedCard.getCardNumber());
                    expire_date.setText(selectedCard.getExpirationDate());

                }
            }
        });








        return v;
    }
}