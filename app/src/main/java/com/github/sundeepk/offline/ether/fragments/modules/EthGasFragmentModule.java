package com.github.sundeepk.offline.ether.fragments.modules;

import com.github.sundeepk.offline.ether.fragments.GasFragment;
import com.github.sundeepk.offline.ether.mvc.views.EthGasView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class EthGasFragmentModule {

    @Binds
    abstract EthGasView provideMainView(GasFragment gasFragment);

}
