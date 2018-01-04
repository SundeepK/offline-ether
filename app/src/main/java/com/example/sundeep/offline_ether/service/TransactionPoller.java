package com.example.sundeep.offline_ether.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.objectbox.Box;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;

public class TransactionPoller extends IntentService {

    private static final String TAG = "TransactionPoller";
    private EtherApi etherApi;
    private DataSubscription addressQuery;
    private Map<String, EtherAddress> addresses = new HashMap<>();
    private Box<EtherAddress> boxStore;

    public TransactionPoller() {
        super("TransactionPoller");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        addressQuery.cancel();
    }

    private synchronized void updateAddresses(List<EtherAddress> refreshedData) {
        Log.d(TAG, "Found addresses " + refreshedData);
        for (EtherAddress etherAddress : refreshedData) {
            addresses.put(etherAddress.getAddress().toLowerCase(), etherAddress);
        }
        Log.d(TAG, "addresses " + addresses);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String etherScanHost = intent.getStringExtra("etherScanApi");
        etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);
        boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        addressQuery = boxStore.query().build()
                .subscribe()
                .observer(this::updateAddresses);

        Collection<Observable<String>> transactions = Collections2.transform(addresses.keySet(), Observable::just);
        Observable.merge(transactions)
                .flatMap(this::getTransactions)
                .repeatWhen(completed -> completed.delay(30, TimeUnit.SECONDS))
                .doOnError(e -> Log.d(TAG, "Error when polling transactions", e))
                .subscribe(this::handleUpdates);
    }

    private Observable<Pair<String, List<EtherTransaction>>> getTransactions(String address) {
        return etherApi
                .getTransactions(address, 1)
                .map(toPair(address));
    }

    @NonNull
    private Function<List<EtherTransaction>, Pair<String, List<EtherTransaction>>> toPair(String address) {
        return etherTransactions -> new Pair<>(address, etherTransactions);
    }

    private synchronized EtherAddress getAddress(String address){
        return addresses.get(address);
    }

    private void handleUpdates(Pair<String, List<EtherTransaction>> stringListPair) {
        String address = stringListPair.first;
        List<EtherTransaction> newTransactions = stringListPair.second;
        if (!newTransactions.isEmpty()) {
            EtherAddress etherAddress = getAddress(address);
            if (etherAddress != null) {
                etherAddress.getEtherTransactions().clear();
                etherAddress.getEtherTransactions().addAll(newTransactions);
                boxStore.put(etherAddress);
            }
        }
    }

}
