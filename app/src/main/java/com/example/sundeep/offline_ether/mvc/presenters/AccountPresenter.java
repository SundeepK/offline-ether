package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherAddress_;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.mvc.views.AccountView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AccountPresenter {

    private final AccountView accountview;
    private final Box<EtherAddress> addressBoxStore;
    private final String address;
    private final EtherApi etherApi;
    private final EtherAddress etherAddress;
    private final DataSubscription transactionObserver;
    private Disposable transactionDisposable;

    public AccountPresenter(AccountView accountview, Box<EtherAddress> addressBoxStore,
                            String address, EtherApi etherApi) {
        this.accountview = accountview;
        this.addressBoxStore = addressBoxStore;
        this.address = address;
        this.etherApi = etherApi;
        this.etherAddress = loadAddress();
        this.transactionObserver = listenForTransactions();
    }

    public void loadEtherAddress(){
        accountview.onAddressLoad(etherAddress);
    }

    public void loadLast50Transactions() {
        transactionDisposable = etherApi.getTransactions(address, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateTransactions, accountview::addressLoadError);
    }

    public void destroy(){
        if (transactionObserver != null) {
            transactionObserver.cancel();
        }
        if (transactionDisposable != null) {
            transactionDisposable.dispose();
        }
    }

    private DataSubscription listenForTransactions() {
        return addressBoxStore.query()
                .equal(EtherAddress_.address, address)
                .build()
                .subscribe()
                .on(AndroidScheduler.mainThread())
                .onError(accountview::addressLoadError)
                .observer(observeAddress());
    }

    private DataObserver<List<EtherAddress>> observeAddress() {
        return addresses -> {
            // should only be one address
            if (!addresses.isEmpty()) {
                accountview.onTransactions(addresses.get(0).getEtherTransactions());
            }
        };
    }

    private EtherAddress loadAddress() {
        return this.addressBoxStore
                .query()
                .equal(EtherAddress_.address, this.address)
                .build()
                .findFirst();
    }

    private void updateTransactions(List<EtherTransaction> transactions) {
        etherAddress.getEtherTransactions().clear();
        etherAddress.getEtherTransactions().addAll(transactions);
        addressBoxStore.put(etherAddress);
    }

}

