package com.example.sundeep.offline_ether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import java.util.Collections;
import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;

public class AddressAdderActivity extends AppCompatActivity {

    private final static String TAG = "AddressAdder";

    private AddressRepository addressRepository;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.address_adder);
        String etherScanHost = getResources().getString(R.string.etherScanHost);
        EtherApi etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);
        String address = getIntent().getStringExtra(PUBLIC_ADDRESS);
        Log.d(TAG, "Started with" + address);

        Box<EtherAddress> boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        addressRepository = new AddressRepository(boxStore);

        Observable<List<Balance>> balance = etherApi.getBalance(Collections.singletonList(address));

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
        EtherAddress existingAddress = addressRepository.findOne(address);
        EtherAddress newAddress = new EtherAddress(address, balances.get(0).getBalance().toString());

        if (existingAddress == null) {
            addressRepository.put(newAddress);
            Log.d(TAG, "Added new address " + newAddress);
            finish();
        } else {
            EtherAddress addressToSave = EtherAddress.newBuilder(existingAddress)
                    .setBalance(balances.get(0).getBalance().toString())
                    .build();
            addressRepository.put(addressToSave);
            Log.d(TAG, "Address already exists " + existingAddress);
            finish();
        }
    }


}
