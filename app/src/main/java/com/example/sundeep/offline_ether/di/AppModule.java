package com.example.sundeep.offline_ether.di;

import android.content.Context;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.blockies.BlockieFactory;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

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
