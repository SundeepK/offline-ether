package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.activities.AddressScannerActivity;
import com.example.sundeep.offline_ether.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

@Module
public abstract class AddressScannerModule {

    @PerActivity
    @Named("addressZXingScannerView")
    @Provides
    static ZXingScannerView provideAddressScannerZXingScannerView(AddressScannerActivity addressScannerActivity) {
        return new ZXingScannerView(addressScannerActivity);
    }


}
