package com.example.sundeep.offline_ether.di;

import com.example.sundeep.offline_ether.fragments.BalanceCurrencyFragment;
import com.example.sundeep.offline_ether.fragments.BalanceEtherFragment;
import com.example.sundeep.offline_ether.fragments.GasFragment;
import com.example.sundeep.offline_ether.fragments.modules.BalanceCurrencyFragmentModule;
import com.example.sundeep.offline_ether.fragments.modules.BalanceEtherFragmentModule;
import com.example.sundeep.offline_ether.fragments.modules.BalancePresenterModule;
import com.example.sundeep.offline_ether.fragments.modules.EthGasFragmentModule;
import com.example.sundeep.offline_ether.fragments.modules.EthGasPresenterModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @PerChildFragment
    @ContributesAndroidInjector(modules = {BalanceEtherFragmentModule.class, BalancePresenterModule.class})
    abstract BalanceEtherFragment balanceEtherFragmentInjector();

    @PerChildFragment
    @ContributesAndroidInjector(modules = {BalanceCurrencyFragmentModule.class, BalancePresenterModule.class})
    abstract BalanceCurrencyFragment balanceCurrencyFragmentInjector();

    @PerChildFragment
    @ContributesAndroidInjector(modules = {EthGasFragmentModule.class, EthGasPresenterModule.class})
    abstract GasFragment gasFragmentInjector();

}
