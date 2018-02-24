package com.example.sundeep.offline_ether.mvc.views;

import com.example.sundeep.offline_ether.entities.GasPrice;
import com.example.sundeep.offline_ether.entities.Nonce;

import java.util.List;

public interface EthGasView {

    public void onEthGasPrice(List<GasPrice> gasPrices);

    public void onNonce(Nonce nonce);

    public void onErrorLoadingEthGasAndNonce(Throwable e);

}
