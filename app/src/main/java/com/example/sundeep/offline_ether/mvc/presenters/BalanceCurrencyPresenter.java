package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.views.BalanceView;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;
import com.example.sundeep.offline_ether.utils.EtherMath;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.objectbox.reactive.DataSubscription;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BalanceCurrencyPresenter {

    private final DataSubscription observer;
    private final EtherApi etherApi;
    private final Map<String, String> cachedPrices = new HashMap<>();
    private final BalanceView balanceView;
    private final AddressRepository addressRepository;
    private long lastUpdate = 0;

    public BalanceCurrencyPresenter(EtherApi etherApi, AddressRepository addressRepository, BalanceView balanceView) {
        this.etherApi = etherApi;
        this.balanceView = balanceView;
        this.addressRepository = addressRepository;
        this.observer = addressRepository.observeAddressesChanges(this::updateBalance);
    }

    public void destroy(){
        observer.cancel();
    }

    public void refreshBalances(){
        List<EtherAddress> all = addressRepository.findAll();
        updateBalance(all);
    }

    private void updateBalance(List<EtherAddress> newAddresses) {
        BigDecimal balanceEther = EtherMath.sumAddresses(newAddresses);
        updateBalanceInCurrency(balanceEther);
    }

    private void updateBalanceInCurrency(BigDecimal ether) {
        getPrices()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(eth -> convertEtherPriceToFiat(ether, eth), balanceView::onBalanceError);
    }

    private void convertEtherPriceToFiat(BigDecimal ether, Map<String, String> currencyToPrice) {
        cachedPrices.putAll(currencyToPrice);
        Currency currency = Currency.getInstance(Locale.getDefault());
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String currencyCode = currency.getCurrencyCode();

        if (!currencyToPrice.containsKey(currencyCode)){
            currency = Currency.getInstance(Locale.US);
            currencyCode = currency.getCurrencyCode();
            currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        }

        if (currencyToPrice.containsKey(currencyCode)) {
            balanceView.onTotalBalance(currencyFormatter.format(ether.multiply(new BigDecimal(currencyToPrice.get(currencyCode)))));
        }
    }

    private Observable<Map<String, String>> getPrices() {
        if(TimeUnit.SECONDS.convert(System.currentTimeMillis() - lastUpdate, TimeUnit.MILLISECONDS) > 60){
            lastUpdate = System.currentTimeMillis();
            return etherApi.getPrices();
        }
        return Observable.just(cachedPrices);
    }



}


