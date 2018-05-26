package com.github.sundeepk.offline.ether.fragments.modules;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.mvc.presenters.BalanceCurrencyPresenter;
import com.github.sundeepk.offline.ether.mvc.presenters.BalanceEtherPresenter;
import com.github.sundeepk.offline.ether.mvc.views.BalanceEtherView;
import com.github.sundeepk.offline.ether.mvc.views.BalanceView;
import com.github.sundeepk.offline.ether.objectbox.AddressRepository;

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
