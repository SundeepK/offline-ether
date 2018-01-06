package com.example.sundeep.offline_ether.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.Price;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.objectbox.Box;
import okhttp3.OkHttpClient;

public class PricePoller extends IntentService {

    private static final String TAG = "PricePoller";
    private EtherApi etherApi;
    private Box<Price> priceBoxStore;

    public PricePoller() {
        super("PricePoller");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String etherScanHost = intent.getStringExtra("etherScanApi");
        etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);
        priceBoxStore = ((App) getApplication()).getBoxStore().boxFor(Price.class);

        etherApi.getPrices()
                .repeatWhen(completed -> completed.delay(40, TimeUnit.SECONDS))
                .subscribe(this::handleUpdates, e -> Log.e(TAG, "Error fetching transactions", e));
    }

    private void handleUpdates(Map<String, String> pricesMap) {
        List<Price> prices = priceBoxStore.query().build().find();
        ImmutableMap<String, Price> currCodeToPrice = Maps.uniqueIndex(prices, Price::getCode);
        List<Price> pricesToSave = new ArrayList<>();
        for (Map.Entry<String, String> entry: pricesMap.entrySet()) {
            if (currCodeToPrice.containsKey(entry.getKey())) {
                Price oldPrice = currCodeToPrice.get(entry.getKey());
                Price newPrice = Price.newBuilder(oldPrice)
                        .setPrice(Float.parseFloat(entry.getValue()))
                        .build();
                pricesToSave.add(newPrice);
            }
        }
        priceBoxStore.put(pricesToSave);
    }
}
