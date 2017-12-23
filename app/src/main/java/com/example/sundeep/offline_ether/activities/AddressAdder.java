package com.example.sundeep.offline_ether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherAddress_;

import io.objectbox.Box;

public class AddressAdder extends AppCompatActivity {

    private final static String TAG = "AddressAdder";
    public final static String PUBLIC_ADDRESS = "PUBLIC_ADDRESS";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.address_adder);

        String address = getIntent().getStringExtra(PUBLIC_ADDRESS);
        Box<EtherAddress> boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        Log.d(TAG, "Started with" + address);

        EtherAddress existingAddress = boxStore
                .query()
                .equal(EtherAddress_.address, address)
                .build()
                .findFirst();

        if (existingAddress == null) {
            EtherAddress newAddress = new EtherAddress();
            newAddress.setAddress(address);
            boxStore.put(newAddress);
            Log.d(TAG, "Added new address " + newAddress);
            finish();
        } else {
            existingAddress.setBalance("" + Math.random());
            boxStore.put(existingAddress);
            Log.d(TAG, "Address already exists " + existingAddress);
            finish();
        }


    }

}
