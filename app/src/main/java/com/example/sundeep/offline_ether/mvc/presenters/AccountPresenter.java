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
    private final EtherApi etherApi;
    private DataSubscription transactionObserver;
    private EtherAddress etherAddress;
    private Disposable transactionDisposable;

    public AccountPresenter(AccountView accountview, Box<EtherAddress> addressBoxStore, EtherApi etherApi) {
        this.accountview = accountview;
        this.addressBoxStore = addressBoxStore;
        this.etherApi = etherApi;
    }

    public void loadAddress(String address) {
        etherAddress =  this.addressBoxStore
                .query()
                .equal(EtherAddress_.address, address)
                .build()
                .findFirst();
        if (etherAddress != null) {
            this.transactionObserver = listenForTransactions();
            accountview.onAddressLoad(etherAddress);
        }
    }

    public void loadLast50Transactions() {
        transactionDisposable = etherApi.getTransactions(etherAddress.getAddress(), 1)
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
                .equal(EtherAddress_.address, etherAddress.getAddress())
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

    private void updateTransactions(List<EtherTransaction> transactions) {
        etherAddress.getEtherTransactions().clear();
        etherAddress.getEtherTransactions().addAll(transactions);
        addressBoxStore.put(etherAddress);
    }

}

