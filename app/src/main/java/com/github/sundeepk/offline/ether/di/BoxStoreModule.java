package com.github.sundeepk.offline.ether.di;

import android.content.Context;

import com.github.sundeepk.offline.ether.BuildConfig;
import com.github.sundeepk.offline.ether.entities.MyObjectBox;

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
