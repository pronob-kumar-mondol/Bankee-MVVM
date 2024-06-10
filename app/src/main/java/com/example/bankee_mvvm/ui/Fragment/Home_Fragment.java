package com.example.bankee_mvvm.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bankee_mvvm.Model.Card;
import com.example.bankee_mvvm.R;
import com.example.bankee_mvvm.ViewModel.Shared_ViewModel;
import com.example.bankee_mvvm.ui.Activity.CashOut_Activity;
import com.example.bankee_mvvm.ui.Activity.PayBill_Activity;
import com.example.bankee_mvvm.ui.Activity.Recharge_Activity;
import com.example.bankee_mvvm.ui.Activity.SendMoney_Activity;


public class Home_Fragment extends Fragment {

    private RelativeLayout sendMoney,recharge,cashOut,payBill;

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

        sendMoney = v.findViewById(R.id.sendMoney);
        recharge = v.findViewById(R.id.recharge);
        cashOut = v.findViewById(R.id.cashOut);
        payBill = v.findViewById(R.id.payBill);


        sharedViewModel = new ViewModelProvider(requireActivity()).get(Shared_ViewModel.class);

        sharedViewModel.getSelectedCard().observe(getViewLifecycleOwner(), new Observer<Card>() {
            @Override
            public void onChanged(Card selectedCard) {
                // Update UI with selected card data
                if (sharedViewModel != null && selectedCard != null) {
                    // Update UI elements with card data
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            holder_name.setText(selectedCard.getCardHolderName());
                            balance.setText(String.valueOf(selectedCard.getBalance()));
                            card_no.setText(selectedCard.getCardNumber());
                            expire_date.setText(selectedCard.getExpirationDate());
                        }
                    });
                }
            }
        });

        sendMoney.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), SendMoney_Activity.class));
        });
        recharge.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), Recharge_Activity.class));
        });
        cashOut.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), CashOut_Activity.class));
        });
        payBill.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), PayBill_Activity.class));
        });







        return v;
    }
}