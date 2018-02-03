package com.example.sundeep.offline_ether.mvc.views;

import com.example.sundeep.offline_ether.entities.EtherAddress;

public interface AddressAdderView {


    public void onAddressUpdate(EtherAddress updatedAdd);

    public void onNoAddress();

    public void onBalanceFetchError(Throwable e);
}
