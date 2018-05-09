package com.example.sundeep.offline_ether.fragments.modules;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.mvc.presenters.BalanceCurrencyPresenter;
import com.example.sundeep.offline_ether.mvc.presenters.BalanceEtherPresenter;
import com.example.sundeep.offline_ether.mvc.views.BalanceEtherView;
import com.example.sundeep.offline_ether.mvc.views.BalanceView;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class BalancePresenterModule {

    @Provides
    public BalanceEtherPresenter providesBalanceEtherPresenter(AddressRepository addressRepository, BalanceEtherView balanceView) {
        return new BalanceEtherPresenter(addressRepository, balanceView);
    }

    @Provides
    public BalanceCurrencyPresenter providesBalanceCurrencyPresenter(AddressRepository addressRepository, BalanceView balanceView, EtherApi etherApi) {
        return new BalanceCurrencyPresenter(etherApi, addressRepository, balanceView);
    }

}
