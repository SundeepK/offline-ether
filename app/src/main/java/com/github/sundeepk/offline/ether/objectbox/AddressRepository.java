package com.github.sundeepk.offline.ether.objectbox;

import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.entities.EtherAddress_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.reactive.ErrorObserver;

public class AddressRepository {

    private Box<EtherAddress> boxStore;

    public AddressRepository(Box<EtherAddress> boxStore) {
        this.boxStore = boxStore;
    }

    public EtherAddress findOne(String address) {
        return boxStore
                .query()
                .equal(EtherAddress_.address, address)
                .build()
                .findFirst();
    }

    private DataSubscription listenForAddresses(String address,
                                                DataObserver<List<EtherAddress>> observer,
                                                ErrorObserver errorObserver) {
        return boxStore.query()
                .equal(EtherAddress_.address, address)
                .build()
                .subscribe()
                .on(AndroidScheduler.mainThread())
                .onError(errorObserver)
                .observer(observer);
    }

    public List<EtherAddress> findAll(){
        return boxStore.query().build().find();
    }

    public DataSubscription observeAddressesChanges(DataObserver<List<EtherAddress>> observer) {
        return boxStore.query().build()
                .subscribe()
                .on(AndroidScheduler.mainThread())
                .observer(observer);
    }

    public void put(EtherAddress address){
        boxStore.put(address);
    }

    public void put(List<EtherAddress> address){
        boxStore.put(address);
    }

}
