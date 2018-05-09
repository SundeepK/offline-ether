package com.example.sundeep.offline_ether.fragments.modules;

import com.example.sundeep.offline_ether.fragments.BalanceCurrencyFragment;
import com.example.sundeep.offline_ether.mvc.views.BalanceView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BalanceCurrencyFragmentModule {


    @Binds
    abstract BalanceView provideMainView(BalanceCurrencyFragment mainActivity);

}
