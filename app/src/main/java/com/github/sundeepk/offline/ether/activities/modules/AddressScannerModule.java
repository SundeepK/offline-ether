package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.activities.AddressScannerActivity;
import com.github.sundeepk.offline.ether.di.PerActivity;

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
