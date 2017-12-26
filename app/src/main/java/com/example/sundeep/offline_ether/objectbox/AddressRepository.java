package com.example.sundeep.offline_ether.objectbox;

import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherAddress_;

import io.objectbox.Box;

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

    public void put( EtherAddress address){
        boxStore.put(address);
    }

}
