package com.github.sundeepk.offline.ether.mvc.presenters;

import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.views.BalanceEtherView;
import com.github.sundeepk.offline.ether.objectbox.AddressRepository;
import com.github.sundeepk.offline.ether.utils.EtherMath;

import java.util.List;

import io.objectbox.reactive.DataSubscription;

public class BalanceEtherPresenter {

    private final DataSubscription observer;
    private final BalanceEtherView balanceView;

    public BalanceEtherPresenter(AddressRepository addressRepository, BalanceEtherView balanceView) {
        this.balanceView = balanceView;
        this.observer = addressRepository.observeAddressesChanges(this::updateBalance);
    }

    private void updateBalance(List<EtherAddress> addresses) {
        String balanceEther = EtherMath.sumAddresses(addresses) + " ETH";
        balanceView.onEtherBalanceLoad(balanceEther);
    }

    public void destroy(){
        observer.cancel();
    }


}


