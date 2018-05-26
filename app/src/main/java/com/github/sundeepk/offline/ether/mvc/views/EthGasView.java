package com.github.sundeepk.offline.ether.mvc.views;

import com.github.sundeepk.offline.ether.entities.GasPrice;
import com.github.sundeepk.offline.ether.entities.Nonce;

import java.util.List;

public interface EthGasView {

    public void onEthGasPrice(List<GasPrice> gasPrices);

    public void onNonce(Nonce nonce);

    public void onErrorLoadingEthGasAndNonce(Throwable e);

}
