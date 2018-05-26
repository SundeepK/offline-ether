package com.github.sundeepk.offline.ether.mvc.views;

import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.entities.EtherTransaction;

import java.util.List;

public interface AccountView {

    public void onTransactions(List<EtherTransaction> transactions);

    public void addressLoadError(Throwable e);

    public void onAddressLoad(EtherAddress address);

}
