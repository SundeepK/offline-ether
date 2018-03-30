package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.views.BalanceEtherView;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;
import com.example.sundeep.offline_ether.utils.EtherMath;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.reactive.DataSubscription;

public class BalanceEtherPresenter {

    private final DataSubscription observer;
    private final BalanceEtherView balanceView;

    @Inject
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


