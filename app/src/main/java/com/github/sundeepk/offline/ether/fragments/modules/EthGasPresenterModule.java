package com.github.sundeepk.offline.ether.fragments.modules;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.mvc.presenters.EthGasPresenter;
import com.github.sundeepk.offline.ether.mvc.views.EthGasView;

import dagger.Module;
import dagger.Provides;

@Module
public class EthGasPresenterModule {

    @Provides
    public EthGasPresenter providesBalanceEtherPresenter(EtherApi etherApi, EthGasView ethGasView) {
        return new EthGasPresenter(etherApi, ethGasView);
    }

}
