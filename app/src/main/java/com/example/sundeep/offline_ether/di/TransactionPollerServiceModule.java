package com.example.sundeep.offline_ether.di;

import android.util.Log;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.service.TransactionPoller;
import com.example.sundeep.offline_ether.service.TransactionPollerService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class TransactionPollerServiceModule {

    TransactionPollerService transactionPollerService;

    public TransactionPollerServiceModule(TransactionPollerService transactionPollerService) {
        this.transactionPollerService = transactionPollerService;
    }


    @Provides
    public TransactionPollerService provideTransactionPollerService(){
        return transactionPollerService;
    }

    @Provides
    public TransactionPoller providesTransactionPoller(EtherApi etherApi, BoxStore boxStore){
        Log.d("TransactionPollerServiceModule", "creating TransactionPoller");
        return new TransactionPoller(etherApi, boxStore.boxFor(EtherAddress.class),
                new ConcurrentHashMap<>(), 15,  TimeUnit.SECONDS);
    }

}
