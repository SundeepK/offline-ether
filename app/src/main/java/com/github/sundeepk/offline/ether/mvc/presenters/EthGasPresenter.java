package com.github.sundeepk.offline.ether.mvc.presenters;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.entities.EthGas;
import com.github.sundeepk.offline.ether.entities.EthGasAndNonce;
import com.github.sundeepk.offline.ether.entities.GasPrice;
import com.github.sundeepk.offline.ether.entities.Nonce;
import com.github.sundeepk.offline.ether.mvc.views.EthGasView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EthGasPresenter {

    private final EtherApi etherApi;
    private final EthGasView ethGasView;
    private Disposable subscribe;

    public EthGasPresenter(EtherApi etherApi, EthGasView ethGasView) {
        this.etherApi = etherApi;
        this.ethGasView = ethGasView;
    }

    public void loadEthGasData(String address){
        if (subscribe != null) {
            subscribe.dispose();
        }
        Observable<EthGas> ethgas = etherApi.getEthgas();
        Observable<Nonce> nonce = etherApi.getNonce(address);

        subscribe = Observable.zip(ethgas, nonce, EthGasAndNonce::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::updateGasAndNonce, ethGasView::onErrorLoadingEthGasAndNonce);
    }

    public void destroy(){
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    private void updateGasAndNonce(EthGasAndNonce ethGasAndNonce) {
        List<GasPrice> gasPrices = new ArrayList<>();
        EthGas ethGas = ethGasAndNonce.getEthGas();
        gasPrices.add(new GasPrice("Slow", ethGas.getSafeLow() / 10, ethGas.getSafeLowWait(), false));
        gasPrices.add(new GasPrice("Average", ethGas.getAverage() / 10, ethGas.getAvgWait(), false));
        gasPrices.add(new GasPrice("Fast", ethGas.getFast() / 10, ethGas.getFastWait(), false));
        ethGasView.onEthGasPrice(gasPrices);
        ethGasView.onNonce(ethGasAndNonce.getNonce());
    }

}
