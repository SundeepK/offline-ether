package com.example.sundeep.offline_ether;

import com.example.sundeep.offline_ether.di.PerChildFragment;
import com.example.sundeep.offline_ether.fragments.BalanceCurrencyFragment;
import com.example.sundeep.offline_ether.fragments.BalanceEtherFragment;
import com.example.sundeep.offline_ether.fragments.modules.BalanceCurrencyFragmentModule;
import com.example.sundeep.offline_ether.fragments.modules.BalanceEtherFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class TestFragmentBuilder {

    @PerChildFragment
    @ContributesAndroidInjector(modules = {BalanceEtherFragmentModule.class, TestPresentersModule.class})
    abstract BalanceEtherFragment balanceEtherFragmentInjector();

    @PerChildFragment
    @ContributesAndroidInjector(modules = {BalanceCurrencyFragmentModule.class, TestPresentersModule.class})
    abstract BalanceCurrencyFragment balanceCurrencyFragmentInjector();

}
