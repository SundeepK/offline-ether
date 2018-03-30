package com.example.sundeep.offline_ether;

import com.example.sundeep.offline_ether.di.AppComponent;
import com.example.sundeep.offline_ether.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.objectbox.BoxStore;

public class App extends DaggerApplication {

    public static final String TAG = "App";
    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

//        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
//        Intent serviceIntent = new Intent(getApplicationContext(), TransactionPollerService.class);
//        String etherScanHost = getResources().getString(R.string.etherScanHost);
//        serviceIntent.putExtra("etherScanApi", etherScanHost);
//        startService(serviceIntent);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
