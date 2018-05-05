package com.example.sundeep.offline_ether.fragments.modules;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.fragments.GasFragment;
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

    @Provides
    public DividerItemDecoration provideDividerItemDecoration(LinearLayoutManager layoutManager, GasFragment gasFragment){
        return new DividerItemDecoration(gasFragment.getContext(), layoutManager.getOrientation());
    }

    @Provides
    public LinearLayoutManager provideLinearLayoutManager(GasFragment gasFragment){
        return new LinearLayoutManager(gasFragment.getContext());
    }

}
