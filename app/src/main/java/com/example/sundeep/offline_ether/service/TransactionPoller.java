package com.example.sundeep.offline_ether.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.objectbox.Box;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.relation.ToMany;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;

public class TransactionPoller extends IntentService {

    private static final String TAG = "TransactionPoller";
    private EtherApi etherApi;
    private DataSubscription addressQuery;
    private Map<String, EtherAddress> addresses = new ConcurrentHashMap<>();
    private Box<EtherAddress> boxStore;

    public TransactionPoller() {
        super("TransactionPoller");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        addressQuery.cancel();
    }

    private void updateAddresses(List<EtherAddress> refreshedData) {
        for (EtherAddress etherAddress : refreshedData) {
            addresses.putIfAbsent(etherAddress.getAddress(), etherAddress);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String etherScanHost = intent.getStringExtra("etherScanApi");
        etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);
        boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        addressQuery = boxStore.query().build()
                .subscribe()
                .observer(this::updateAddresses);

        Collection<Observable<List<EtherTransaction>>> transactions = Collections2.transform(addresses.keySet(), address -> {
            Log.d(TAG, "Searching transactions");
            return etherApi.getTransactions(address, 1);
        });
        Observable.merge(transactions)
                .repeatWhen(completed -> completed.delay(30, TimeUnit.SECONDS))
                .subscribe(this::handleUpdates);
    }

    private void handleUpdates(List<EtherTransaction> etherTransactions) {
        Log.d(TAG, "Found data" + etherTransactions.toString());
        if (!etherTransactions.isEmpty()) {
            EtherTransaction etherTransaction = etherTransactions.get(0);
            String to = etherTransaction.getTo();
            String from = etherTransaction.getFrom();
            EtherAddress etherAddress;
            if (addresses.containsKey(to)) {
                etherAddress = addresses.get(to);
            } else {
                etherAddress = addresses.get(from);
            }
            if (etherAddress != null) {
                etherAddress.getEtherTransactions().clear();
                etherAddress.getEtherTransactions().addAll(etherTransactions);
            }
        }
    }
}
