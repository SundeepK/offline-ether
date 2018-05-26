package com.github.sundeepk.offline.ether.mvc.views;

import com.github.sundeepk.offline.ether.entities.EtherAddress;

import java.util.List;

public interface MainView {

    public void loadBalances(List<EtherAddress> addresses);

    public void onAccountLoadError(Throwable t);

    public void onEtherSelected();

    public void onCurrencySelected();

    public void onAddAccount();
}
