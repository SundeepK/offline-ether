package com.github.sundeepk.offline.ether.di;

import android.util.Log;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.service.TransactionPoller;
import com.github.sundeepk.offline.ether.service.TransactionPollerService;

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
