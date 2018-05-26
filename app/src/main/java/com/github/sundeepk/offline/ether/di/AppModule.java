package com.github.sundeepk.offline.ether.di;

import android.content.Context;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.blockies.BlockieFactory;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.objectbox.AddressRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class AppModule {

    @Singleton
    @Provides
    public AddressRepository provideAddressRepository(BoxStore boxStore){
        return new AddressRepository(boxStore.boxFor(EtherAddress.class));
    }

    @Singleton
    @Provides
    public  EtherApi provideEtherApi(Context context) {
        String etherScanHost = context.getResources().getString(R.string.etherScanHost);
        return EtherApi.getEtherApi(etherScanHost);
    }

    @Singleton
    @Provides
    public BlockieFactory provideBlockieFactory(){
        return new BlockieFactory();
    }

}
