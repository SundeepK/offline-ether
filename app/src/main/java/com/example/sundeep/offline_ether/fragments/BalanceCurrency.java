package com.example.sundeep.offline_ether.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.presenters.BalanceCurrencyPresenter;
import com.example.sundeep.offline_ether.mvc.views.BalanceView;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import io.objectbox.Box;

public class BalanceCurrency extends Fragment implements BalanceView {

    private static final String TAG = "BalanceCurrency";
    private TextView balance;
    private BalanceCurrencyPresenter balanceCurrencyPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.balance, container, false);
        balance = rootView.findViewById(R.id.balance_textView);
        balance.setText("-");

        EtherApi etherApi = EtherApi.getEtherApi(getResources().getString(R.string.etherScanHost));
        Box<EtherAddress> boxStore = ((App) getActivity().getApplication()).getBoxStore().boxFor(EtherAddress.class);

        balanceCurrencyPresenter = new BalanceCurrencyPresenter(etherApi, new AddressRepository(boxStore), this);

        return rootView;
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
