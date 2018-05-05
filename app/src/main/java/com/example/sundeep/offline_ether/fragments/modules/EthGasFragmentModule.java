package com.example.sundeep.offline_ether.fragments.modules;

import com.example.sundeep.offline_ether.fragments.GasFragment;
import com.example.sundeep.offline_ether.mvc.views.EthGasView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class EthGasFragmentModule {

    @Binds
    abstract EthGasView provideMainView(GasFragment gasFragment);

}
