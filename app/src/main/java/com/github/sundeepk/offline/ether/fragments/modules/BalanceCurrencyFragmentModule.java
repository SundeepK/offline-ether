package com.github.sundeepk.offline.ether.fragments.modules;

import com.github.sundeepk.offline.ether.fragments.BalanceCurrencyFragment;
import com.github.sundeepk.offline.ether.mvc.views.BalanceView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BalanceCurrencyFragmentModule {


    @Binds
    abstract BalanceView provideMainView(BalanceCurrencyFragment mainActivity);

}
