package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.views.MainView;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

    private final EtherApi etherApi;
    private final Box<EtherAddress> boxStore;
    private final MainView mainView;
    private final DataSubscription addressObserver;

    public MainPresenter(EtherApi etherApi, Box<EtherAddress> boxStore, MainView mainView) {
        this.etherApi = etherApi;
        this.boxStore = boxStore;
        this.mainView = mainView;
        addressObserver = observeAddressChanges();
    }

    private DataSubscription observeAddressChanges() {
        return boxStore.query().build()
                .subscribe()
                .on(AndroidScheduler.mainThread())
                .observer(mainView::loadBalances);
    }

    public void destroy(){
        if (addressObserver != null) {
            addressObserver.cancel();
        }
    }

    public void addAccount(){
        mainView.onAddAccount();
    }

    public void deleteAccount(EtherAddress etherAddress){
        boxStore.remove(etherAddress);
    }

    public Disposable loadBalances() {
        List<EtherAddress> etherAddresses = boxStore.query().build().find();
        ImmutableMap<String, EtherAddress> addressToEtherAccount = Maps.uniqueIndex(etherAddresses, EtherAddress::getAddress);
        return etherApi.getBalance(addressToEtherAccount.keySet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balances -> updateBalances(addressToEtherAccount, balances.getResult()), mainView::onAccountLoadError);
    }

    private void updateBalances(Map<String, EtherAddress> addressToEtherAccount, List<Balance> balances) {
        List<EtherAddress> updatedAddresses = new ArrayList<>();
        for (Balance balance : balances) {
            EtherAddress address = addressToEtherAccount.get(balance.getAccount());
            EtherAddress newAccount = EtherAddress.newBuilder(address)
                    .setBalance(balance.getBalance())
                    .build();
            updatedAddresses.add(newAccount);
        }
        boxStore.put(updatedAddresses);
    }

}
