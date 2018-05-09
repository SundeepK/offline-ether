package com.example.sundeep.offline_ether.fragments.modules;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.mvc.presenters.EthGasPresenter;
import com.example.sundeep.offline_ether.mvc.views.EthGasView;

import dagger.Module;
import dagger.Provides;

@Module
public class EthGasPresenterModule {

    @Provides
    public EthGasPresenter providesBalanceEtherPresenter(EtherApi etherApi, EthGasView ethGasView) {
        return new EthGasPresenter(etherApi, ethGasView);
    }

}
