package com.example.sundeep.offline_ether.di;

import android.content.Context;

import com.example.sundeep.offline_ether.BuildConfig;
import com.example.sundeep.offline_ether.entities.MyObjectBox;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

@Module
public class BoxStoreModule {

    @Singleton
    @Provides
    public BoxStore provideBoxStore(Context context) {
        BoxStore boxStore = MyObjectBox.builder().androidContext(context).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(context);
        }
        return boxStore;
    }
}
