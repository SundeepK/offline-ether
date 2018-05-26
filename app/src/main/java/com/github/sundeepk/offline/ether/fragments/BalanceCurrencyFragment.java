package com.github.sundeepk.offline.ether.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.mvc.presenters.BalanceCurrencyPresenter;
import com.github.sundeepk.offline.ether.mvc.views.BalanceView;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class BalanceCurrencyFragment extends Fragment implements BalanceView {

    private static final String TAG = "BalanceCurrency";
    private TextView balance;

    @Inject BalanceCurrencyPresenter balanceCurrencyPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.balance, container, false);
        balance = rootView.findViewById(R.id.balance_textView);
        balance.setText("-");
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        balanceCurrencyPresenter.observeAddressChanges();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume");
        balanceCurrencyPresenter.refreshBalances();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        balanceCurrencyPresenter.destroy();
    }

    @Override
    public void onTotalBalance(String balanceInCurrency) {
        balance.setText(balanceInCurrency);
    }

    @Override
    public void onBalanceError(Throwable throwable) {
        View viewById = getActivity().findViewById(R.id.main_container);
        Snackbar.make(viewById, "Unable to fetch price updates. Check Network.", Snackbar.LENGTH_LONG).show();
        Log.e(TAG, "Error fetching prices ", throwable);
    }
}
