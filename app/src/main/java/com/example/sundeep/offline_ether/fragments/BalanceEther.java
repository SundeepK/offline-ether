package com.example.sundeep.offline_ether.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.utils.EtherMath;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;

public class BalanceEther extends Fragment {

    private DataSubscription observer;
    private TextView balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.balance, container, false);
       balance = rootView.findViewById(R.id.balance_textView);

        final Box<EtherAddress> boxStore = ((App) getActivity().getApplication()).getBoxStore().boxFor(EtherAddress.class);
        Query<EtherAddress> addressQuery = boxStore.query().build();
        observer = addressQuery
                .subscribe()
                .on(AndroidScheduler.mainThread())
                .observer(onDataChanged());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        observer.cancel();
    }

    @NonNull
    private DataObserver<List<EtherAddress>> onDataChanged() {
        return newAddresses -> {
            String balanceEther = EtherMath.sumAddresses(newAddresses) + " ETH";
            balance.setText(balanceEther);
        };
    }


}
