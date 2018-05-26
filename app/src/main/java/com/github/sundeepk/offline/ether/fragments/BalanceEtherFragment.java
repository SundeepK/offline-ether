package com.github.sundeepk.offline.ether.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.mvc.presenters.BalanceEtherPresenter;
import com.github.sundeepk.offline.ether.mvc.views.BalanceEtherView;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class BalanceEtherFragment extends Fragment implements BalanceEtherView {

    @Inject
    BalanceEtherPresenter balanceEtherPresenter;

    private TextView balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.balance, container, false);
        balance = rootView.findViewById(R.id.balance_textView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
