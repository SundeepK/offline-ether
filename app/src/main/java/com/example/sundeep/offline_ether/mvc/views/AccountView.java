package com.example.sundeep.offline_ether.mvc.views;

import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;

import java.util.List;

public interface AccountView {

    public void onTransactions(List<EtherTransaction> transactions);

    public void addressLoadError(Throwable e);

    public void onAddressLoad(EtherAddress address);

}
