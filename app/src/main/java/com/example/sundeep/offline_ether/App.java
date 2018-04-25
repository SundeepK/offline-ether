package com.example.sundeep.offline_ether;

import android.content.Intent;

import com.example.sundeep.offline_ether.di.AppComponent;
import com.example.sundeep.offline_ether.di.DaggerAppComponent;
import com.example.sundeep.offline_ether.di.TransactionPollerServiceComponent;
import com.example.sundeep.offline_ether.di.TransactionPollerServiceModule;
import com.example.sundeep.offline_ether.service.TransactionPollerService;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class App extends DaggerApplication {

    public static final String TAG = "App";
    public static final boolean EXTERNAL_DIR = false;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent serviceIntent = new Intent(getApplicationContext(), TransactionPollerService.class);
        String etherScanHost = getResources().getString(R.string.etherScanHost);
        serviceIntent.putExtra("etherScanApi", etherScanHost);
        startService(serviceIntent);

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    public TransactionPollerServiceComponent getServiceInjector(TransactionPollerService transactionPollerService) {
        return appComponent.transactionPollerServiceBuilder().withServiceModule(new TransactionPollerServiceModule(transactionPollerService)).build();
    }

}
