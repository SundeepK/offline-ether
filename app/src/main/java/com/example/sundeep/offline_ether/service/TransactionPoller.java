package com.example.sundeep.offline_ether.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.objectbox.Box;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.relation.ToMany;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class TransactionPoller {

    private static final String TAG = "TransactionPoller";
    private final EtherApi etherApi;
    private final Box<EtherAddress> boxStore;
    private final Map<String, Long> addresses;
    private final int delay;
    private final TimeUnit timeUnit;
    private DataSubscription addressQuery;
    private Disposable repeat;

    public TransactionPoller(EtherApi etherApi,
                             Box<EtherAddress> boxStore,
                             Map<String, Long> addresses,
                             int delay,
                             TimeUnit timeUnit) {
        this.etherApi = etherApi;
        this.boxStore = boxStore;
        this.addresses = addresses;
        this.delay = delay;
        this.timeUnit = timeUnit;
    }

    public void pollTransactions() {
        Log.d(TAG, "polling");
        addressQuery = boxStore.query().build()
                .subscribe()
                .observer(this::updateAddresses);

        addressQuery = boxStore.query().build()
                .subscribe()
                .observer(this::updateAddresses);

        Collection<Observable<String>> addressesToSearch = Collections2.transform(addresses.keySet(), Observable::just);
        repeat = Observable.merge(addressesToSearch)
                .flatMap(this::getTransactions)
                .repeatWhen(completed -> completed.delay(delay, timeUnit))
                .retryWhen(err -> err.flatMap(this::handleRetry))
                .subscribe(this::handleUpdates);
    }

    private ObservableSource<? extends Long> handleRetry(Throwable e) {
        Log.e(TAG, "Error fetching transactions. Retrying polling.", e);
        return Observable.timer(delay, timeUnit);
    }

    private void handleError(Throwable throwable) {
        Log.e(TAG, "Error fetching transactions.", throwable);
    }

    public void destroy() {
        if (addressQuery != null) {
            addressQuery.cancel();
        }
        if (repeat != null) {
            repeat.dispose();
        }
    }

    private void updateAddresses(List<EtherAddress> refreshedData) {
        Log.d(TAG, "Found addresses " + refreshedData);
        for (EtherAddress etherAddress : refreshedData) {
            String key = etherAddress.getAddress().toLowerCase();
            if (!addresses.containsKey(key)) {
                addresses.put(key, etherAddress.getId());
            }
        }
    }

    private Observable<AddressToTransactions> getTransactions(String address) {
        return etherApi
                .getTransactions(address, 1)
                .map(toPair(address));
    }

    @NonNull
    private Function<List<EtherTransaction>, AddressToTransactions> toPair(String address) {
        return (List<EtherTransaction> transactions) -> new AddressToTransactions(address, transactions);
    }

    private void handleUpdates(AddressToTransactions addressToTransactions) {
        String address = addressToTransactions.address;
        List<EtherTransaction> newTransactions = addressToTransactions.transactions;
        if (address != null && newTransactions != null && !newTransactions.isEmpty()) {
            Long id = addresses.get(address);
            if (id != null) {
                Log.d(TAG, "Updating transactions " + newTransactions);
                EtherAddress etherAddress = boxStore.get(id);
                if (etherAddress == null ) {
                    addresses.remove(address);
                } else {
                    ToMany<EtherTransaction> etherTransactions = etherAddress.getEtherTransactions();
                    etherTransactions.clear();
                    etherTransactions.addAll(newTransactions);
                    boxStore.put(etherAddress);
                }
            }
        }
    }

    private static class AddressToTransactions {
        String address;
        List<EtherTransaction> transactions;

        public AddressToTransactions(String address, List<EtherTransaction> transactions) {
            this.address = address;
            this.transactions = transactions;
        }
    }

}
