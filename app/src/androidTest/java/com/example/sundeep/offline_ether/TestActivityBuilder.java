package com.example.sundeep.offline_ether;

import com.example.sundeep.offline_ether.activities.AccountActivity;
import com.example.sundeep.offline_ether.activities.AddressScannerActivity;
import com.example.sundeep.offline_ether.activities.MainActivity;
import com.example.sundeep.offline_ether.activities.OfflineTransactionActivity;
import com.example.sundeep.offline_ether.activities.SendTransactionActivity;
import com.example.sundeep.offline_ether.activities.modules.AccountActivityModule;
import com.example.sundeep.offline_ether.activities.modules.AddressScannerModule;
import com.example.sundeep.offline_ether.activities.modules.MainActivityModule;
import com.example.sundeep.offline_ether.activities.modules.MainFragmentModule;
import com.example.sundeep.offline_ether.activities.modules.SendTransactionActivityModule;
import com.example.sundeep.offline_ether.di.PerActivity;

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
