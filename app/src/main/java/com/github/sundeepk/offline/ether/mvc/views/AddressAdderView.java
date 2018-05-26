package com.github.sundeepk.offline.ether.mvc.views;

import com.github.sundeepk.offline.ether.entities.EtherAddress;

public interface AddressAdderView {


    public void onAddressUpdate(EtherAddress updatedAdd);

    public void onNoAddress();

    public void onBalanceFetchError(Throwable e);
}
