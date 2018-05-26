package com.github.sundeepk.offline.ether.di;

import com.github.sundeepk.offline.ether.fragments.BalanceCurrencyFragment;
import com.github.sundeepk.offline.ether.fragments.BalanceEtherFragment;
import com.github.sundeepk.offline.ether.fragments.GasFragment;
import com.github.sundeepk.offline.ether.fragments.modules.BalanceCurrencyFragmentModule;
import com.github.sundeepk.offline.ether.fragments.modules.BalanceEtherFragmentModule;
import com.github.sundeepk.offline.ether.fragments.modules.BalancePresenterModule;
import com.github.sundeepk.offline.ether.fragments.modules.EthGasFragmentModule;
import com.github.sundeepk.offline.ether.fragments.modules.EthGasPresenterModule;

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
