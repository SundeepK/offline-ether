package com.github.sundeepk.offline.ether.mvc.presenters;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.entities.Balance;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.views.MainView;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class MainPresenter {

    private final EtherApi etherApi;
    private final Box<EtherAddress> boxStore;
    private final MainView mainView;
    private DataSubscription addressObserver;

    public MainPresenter(EtherApi etherApi, Box<EtherAddress> boxStore, MainView mainView) {
        this.etherApi = etherApi;
        this.boxStore = boxStore;
        this.mainView = mainView;
    }

    public void observeAddressChange(){
        if (addressObserver != null) {
            addressObserver.cancel();
        }
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
