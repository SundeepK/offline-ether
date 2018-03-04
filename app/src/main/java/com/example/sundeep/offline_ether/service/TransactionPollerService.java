package com.example.sundeep.offline_ether.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.objectbox.Box;

public class TransactionPollerService extends IntentService {

    private TransactionPoller transactionPoller;

    public TransactionPollerService() {
        super("TransactionPoller");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        transactionPoller.destroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        EtherApi etherApi = EtherApi.getEtherApi(getResources().getString(R.string.etherScanHost));
        Box<EtherAddress> boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        transactionPoller = new TransactionPoller(etherApi, boxStore, new ConcurrentHashMap<>(), 30,  TimeUnit.SECONDS);
        transactionPoller.pollTransactions();
    }

}
