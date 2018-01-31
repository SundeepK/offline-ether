package com.example.sundeep.offline_ether.mvc.views;

import com.example.sundeep.offline_ether.entities.EtherAddress;

import java.util.List;

public interface MainView {

    public void loadBalances(List<EtherAddress> addresses);

    public void onAccountLoadError(Throwable t);

    public void onEtherSelected();

    public void onCurrencySelected();

    public void onAddAccount();
}
