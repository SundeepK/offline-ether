package com.github.sundeepk.offline.ether;

import com.github.sundeepk.offline.ether.di.PerChildFragment;
import com.github.sundeepk.offline.ether.fragments.BalanceCurrencyFragment;
import com.github.sundeepk.offline.ether.fragments.BalanceEtherFragment;
import com.github.sundeepk.offline.ether.fragments.GasFragment;
import com.github.sundeepk.offline.ether.fragments.modules.BalanceCurrencyFragmentModule;
import com.github.sundeepk.offline.ether.fragments.modules.BalanceEtherFragmentModule;
import com.github.sundeepk.offline.ether.fragments.modules.EthGasFragmentModule;

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

    @PerChildFragment
    @ContributesAndroidInjector(modules = {EthGasFragmentModule.class, TestPresentersModule.class})
    abstract GasFragment GasFragmentInjector();

}
