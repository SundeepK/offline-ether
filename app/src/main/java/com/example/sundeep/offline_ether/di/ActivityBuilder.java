package com.example.sundeep.offline_ether.di;

import com.example.sundeep.offline_ether.activities.AccountActivity;
import com.example.sundeep.offline_ether.activities.MainActivity;
import com.example.sundeep.offline_ether.activities.modules.AccountActivityModule;
import com.example.sundeep.offline_ether.activities.modules.MainActivityModule;
import com.example.sundeep.offline_ether.activities.modules.MainFragmentModule;
import com.example.sundeep.offline_ether.activities.modules.MainPresenterModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = { MainActivityModule.class, MainFragmentModule.class, MainPresenterModule.class })
    abstract MainActivity bindMainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = { AccountActivityModule.class })
    abstract AccountActivity bindAccountActivity();

}
