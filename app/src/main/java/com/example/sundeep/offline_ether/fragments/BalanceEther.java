package com.example.sundeep.offline_ether.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.presenters.BalanceEtherPresenter;
import com.example.sundeep.offline_ether.mvc.views.BalanceEtherView;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import io.objectbox.Box;

public class BalanceEther extends Fragment implements BalanceEtherView {

    BalanceEtherPresenter balanceEtherPresenter;
    private TextView balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.balance, container, false);
        balance = rootView.findViewById(R.id.balance_textView);

        final Box<EtherAddress> boxStore = ((App) getActivity().getApplication()).getBoxStore().boxFor(EtherAddress.class);
        balanceEtherPresenter = new BalanceEtherPresenter(new AddressRepository(boxStore), this);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        balanceEtherPresenter.destroy();
    }

    @Override
    public void onEtherBalanceLoad(String balanceEther) {
        balance.setText(balanceEther);
    }

}
