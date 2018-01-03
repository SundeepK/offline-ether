package com.example.sundeep.offline_ether;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.sundeep.offline_ether.entities.MyObjectBox;
import com.example.sundeep.offline_ether.service.TransactionPoller;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {

    public static final String TAG = "App";
    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }

        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
        Intent serviceIntent = new Intent(getApplicationContext(), TransactionPoller.class);
        String etherScanHost = getResources().getString(R.string.etherScanHost);
        serviceIntent.putExtra("etherScanApi", etherScanHost);
        startService(serviceIntent);
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
