package com.github.sundeepk.offline.ether.fragments.modules;

import com.github.sundeepk.offline.ether.fragments.BalanceEtherFragment;
import com.github.sundeepk.offline.ether.mvc.views.BalanceEtherView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BalanceEtherFragmentModule {

    @Binds
    public abstract BalanceEtherView provideMainView(BalanceEtherFragment mainActivity);


}
