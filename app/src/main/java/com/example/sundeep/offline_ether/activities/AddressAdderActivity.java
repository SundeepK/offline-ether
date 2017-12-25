package com.example.sundeep.offline_ether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherAddress_;
import com.example.sundeep.offline_ether.etherscan.EtherScan;

import java.util.Collections;
import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class AddressAdderActivity extends AppCompatActivity {

    private final static String TAG = "AddressAdder";
    public final static String PUBLIC_ADDRESS = "PUBLIC_ADDRESS";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.address_adder);
        String etherScanHost = getResources().getString(R.string.etherScanHost);
        EtherScan etherScan = new EtherScan(new OkHttpClient(), etherScanHost);
        String address = getIntent().getStringExtra(PUBLIC_ADDRESS);
        Log.d(TAG, "Started with" + address);

        Observable<List<Balance>> balance = etherScan.getBalance(Collections.singletonList(address));

        balance.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(e -> Log.e(TAG, "Error fetching balances", e))
                .subscribe(balances -> saveAddress(balances, address));

    }

    private void saveAddress(List<Balance> balances, String address) {
        if (balances.isEmpty()) {
            Log.d(TAG, "No address found in etherscan for " + address);
            finish();
        }
        Box<EtherAddress> boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        EtherAddress existingAddress = queryAddress(address, boxStore);
        EtherAddress newAddress = new EtherAddress(address, balances.get(0).getBalance().toString());

        if (existingAddress == null) {
            boxStore.put(newAddress);
            Log.d(TAG, "Added new address " + newAddress);
            finish();
        } else {
            existingAddress.setBalance(balances.get(0).getBalance().toString());
            boxStore.put(existingAddress);
            Log.d(TAG, "Address already exists " + existingAddress);
            finish();
        }
    }

    private EtherAddress queryAddress(String address, Box<EtherAddress> boxStore) {
        return boxStore
                    .query()
                    .equal(EtherAddress_.address, address)
                    .build()
                    .findFirst();
    }

}
