package com.example.sundeep.offline_ether.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sundeep.offline_ether.App;

import javax.inject.Inject;

public class TransactionPollerService extends IntentService {

    @Inject
    TransactionPoller transactionPoller;


    public TransactionPollerService() {
        super("TransactionPoller");
    }


    @Override
    public void onCreate() {
        Log.d("TransactionPollerServ", "injecting");
        ((App)getApplicationContext()).getServiceInjector(this).inject(this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("TransactionPollerServ", "onHandleIntent");
        transactionPoller.pollTransactions();
    }

}
