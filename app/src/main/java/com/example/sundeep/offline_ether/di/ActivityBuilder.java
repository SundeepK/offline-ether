package com.example.sundeep.offline_ether.di;

import com.example.sundeep.offline_ether.activities.AccountActivity;
import com.example.sundeep.offline_ether.activities.AddressAdderActivity;
import com.example.sundeep.offline_ether.activities.AddressScannerActivity;
import com.example.sundeep.offline_ether.activities.MainActivity;
import com.example.sundeep.offline_ether.activities.OfflineTransactionActivity;
import com.example.sundeep.offline_ether.activities.SendTransactionActivity;
import com.example.sundeep.offline_ether.activities.modules.AccountActivityModule;
import com.example.sundeep.offline_ether.activities.modules.AccountPresenterModule;
import com.example.sundeep.offline_ether.activities.modules.AddressAdderActivityModule;
import com.example.sundeep.offline_ether.activities.modules.AddressAdderPresenterModule;
import com.example.sundeep.offline_ether.activities.modules.AddressScannerModule;
import com.example.sundeep.offline_ether.activities.modules.MainActivityModule;
import com.example.sundeep.offline_ether.activities.modules.MainFragmentModule;
import com.example.sundeep.offline_ether.activities.modules.MainPresenterModule;
import com.example.sundeep.offline_ether.activities.modules.SendTransactionActivityModule;
import com.example.sundeep.offline_ether.activities.modules.SendTransactionPresenterModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = { MainActivityModule.class, MainFragmentModule.class, MainPresenterModule.class })
    abstract MainActivity bindMainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = { AccountActivityModule.class, AccountPresenterModule.class})
    abstract AccountActivity bindAccountActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = { AddressScannerModule.class })
    abstract AddressScannerActivity bindAddressScannerActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = { AddressAdderActivityModule.class, AddressAdderPresenterModule.class })
    abstract AddressAdderActivity bindAddressAdderActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {SendTransactionPresenterModule.class, SendTransactionActivityModule.class})
    abstract SendTransactionActivity bindAddressSendTransactionActivity();

    @PerActivity
    @ContributesAndroidInjector()
    abstract OfflineTransactionActivity bindOfflineTransactionActivity();

}
