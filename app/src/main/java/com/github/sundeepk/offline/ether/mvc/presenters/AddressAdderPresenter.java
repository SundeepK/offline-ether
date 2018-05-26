package com.github.sundeepk.offline.ether.mvc.presenters;

import android.util.Log;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.blockies.BlockieFactory;
import com.github.sundeepk.offline.ether.entities.Balance;
import com.github.sundeepk.offline.ether.entities.Balances;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.views.AddressAdderView;
import com.github.sundeepk.offline.ether.objectbox.AddressRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddressAdderPresenter {

    private static final String TAG = "AddressAdderPresenter";
    private final AddressRepository addressRepository;
    private final AddressAdderView addressAdderView;
    private final EtherApi etherApi;
    private final BlockieFactory blockieFactory;

    public AddressAdderPresenter(AddressRepository addressRepository,
                                 AddressAdderView addressAdderView,
                                 EtherApi etherApi,
                                 BlockieFactory blockieFactory) {
        this.addressRepository = addressRepository;
        this.addressAdderView = addressAdderView;
        this.etherApi = etherApi;
        this.blockieFactory = blockieFactory;
    }

    public Disposable saveAddress(String address){
        return etherApi.getBalance(Collections.singletonList(address))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(balances -> createOrUpdateBalance(balances, address),
                        addressAdderView::onBalanceFetchError);
    }

    private void createOrUpdateBalance(Balances balance, String address){
        List<Balance> balances = balance.getResult();
        if (balances.isEmpty()) {
            Log.d(TAG, "No address found in etherscan for " + address);
            addressAdderView.onNoAddress();
            return;
        }
        EtherAddress existingAddress = addressRepository.findOne(address);
        EtherAddress updatedAdd = getAddress(address, balances, existingAddress);
        addressRepository.put(updatedAdd);
        addressAdderView.onAddressUpdate(updatedAdd);
    }

    private EtherAddress getAddress(String address, List<Balance> balances, EtherAddress existingAddress) {
        EtherAddress updatedAdd;
        byte[] blockieArray = blockieFactory.getBlockie(address);
        if (existingAddress == null) {
            updatedAdd = EtherAddress.newBuilder()
                    .setAddress(address)
                    .setBalance(balances.get(0).getBalance())
                    .setBlockie(blockieArray)
                    .build();
            Log.d(TAG, "New address found: " + updatedAdd);
        } else {
            updatedAdd = EtherAddress.newBuilder(existingAddress)
                    .setBalance(balances.get(0).getBalance())
                    .setBlockie(blockieArray)
                    .build();
            Log.d(TAG, "Address already exists and will be updated: " + updatedAdd);
        }
        return updatedAdd;
    }


}
