package com.github.sundeepk.offline.ether;

import com.github.sundeepk.offline.ether.activities.AccountActivity;
import com.github.sundeepk.offline.ether.activities.AddressScannerActivity;
import com.github.sundeepk.offline.ether.activities.MainActivity;
import com.github.sundeepk.offline.ether.activities.OfflineTransactionActivity;
import com.github.sundeepk.offline.ether.activities.SendTransactionActivity;
import com.github.sundeepk.offline.ether.activities.modules.AccountActivityModule;
import com.github.sundeepk.offline.ether.activities.modules.AddressScannerModule;
import com.github.sundeepk.offline.ether.activities.modules.MainActivityModule;
import com.github.sundeepk.offline.ether.activities.modules.MainFragmentModule;
import com.github.sundeepk.offline.ether.activities.modules.SendTransactionActivityModule;
import com.github.sundeepk.offline.ether.di.PerActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class TestActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = { MainActivityModule.class, MainFragmentModule.class })
    abstract MainActivity bindMainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = { AccountActivityModule.class })
    abstract AccountActivity bindAccountActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = { AddressScannerModule.class })
    abstract AddressScannerActivity bindAddressScannerActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = { SendTransactionActivityModule.class })
    abstract SendTransactionActivity bindSendTransactionActivity();

    @PerActivity
    @ContributesAndroidInjector()
    abstract OfflineTransactionActivity bindOfflineTransactionActivity();

}
