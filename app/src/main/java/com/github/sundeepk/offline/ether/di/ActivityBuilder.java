package com.github.sundeepk.offline.ether.di;

import com.github.sundeepk.offline.ether.activities.AccountActivity;
import com.github.sundeepk.offline.ether.activities.AddressAdderActivity;
import com.github.sundeepk.offline.ether.activities.AddressScannerActivity;
import com.github.sundeepk.offline.ether.activities.MainActivity;
import com.github.sundeepk.offline.ether.activities.OfflineTransactionActivity;
import com.github.sundeepk.offline.ether.activities.SendTransactionActivity;
import com.github.sundeepk.offline.ether.activities.modules.AccountActivityModule;
import com.github.sundeepk.offline.ether.activities.modules.AccountPresenterModule;
import com.github.sundeepk.offline.ether.activities.modules.AddressAdderActivityModule;
import com.github.sundeepk.offline.ether.activities.modules.AddressAdderPresenterModule;
import com.github.sundeepk.offline.ether.activities.modules.AddressScannerModule;
import com.github.sundeepk.offline.ether.activities.modules.MainActivityModule;
import com.github.sundeepk.offline.ether.activities.modules.MainFragmentModule;
import com.github.sundeepk.offline.ether.activities.modules.MainPresenterModule;
import com.github.sundeepk.offline.ether.activities.modules.SendTransactionActivityModule;
import com.github.sundeepk.offline.ether.activities.modules.SendTransactionPresenterModule;

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
