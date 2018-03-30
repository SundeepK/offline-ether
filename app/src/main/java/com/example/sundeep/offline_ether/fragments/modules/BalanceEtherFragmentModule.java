package com.example.sundeep.offline_ether.fragments.modules;

import com.example.sundeep.offline_ether.fragments.BalanceEtherFragment;
import com.example.sundeep.offline_ether.mvc.views.BalanceEtherView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BalanceEtherFragmentModule {


    @Binds
    abstract BalanceEtherView provideMainView(BalanceEtherFragment mainActivity);

}
