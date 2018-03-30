package com.example.sundeep.offline_ether.di;

import android.app.Application;
import android.content.Context;

import com.example.sundeep.offline_ether.BuildConfig;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.MyObjectBox;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

@Module
public abstract class AppModule {

    @Binds
    abstract Context provideContext(Application application);

    @Singleton
    @Provides
    static BoxStore provideBoxStore(Context context) {
        BoxStore boxStore = MyObjectBox.builder().androidContext(context).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(context);
        }
        return boxStore;
    }

    @Singleton
    @Provides
    static AddressRepository provideAddressRepository(BoxStore boxStore){
        return new AddressRepository(boxStore.boxFor(EtherAddress.class));
    }

    @Singleton
    @Provides
    static EtherApi provideEtherApi(Context context) {
        String etherScanHost = context.getResources().getString(R.string.etherScanHost);
        return EtherApi.getEtherApi(etherScanHost);
    }
}
