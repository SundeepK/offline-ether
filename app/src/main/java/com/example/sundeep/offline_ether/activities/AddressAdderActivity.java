package com.example.sundeep.offline_ether.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.blockies.Blockies;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.Balances;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;

public class AddressAdderActivity extends AppCompatActivity {

    private final static String TAG = "AddressAdder";

    private AddressRepository addressRepository;
    private TextView status;
    private FancyButton okButton;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.address_adder);

        status = findViewById(R.id.status_message);
        okButton = findViewById(R.id.ok_button);

        okButton.setOnClickListener(v -> finish());

        EtherApi etherApi = EtherApi.getEtherApi(getResources().getString(R.string.etherScanHost));
        String address = getIntent().getStringExtra(PUBLIC_ADDRESS);
        Log.d(TAG, "Started with" + address);

        Box<EtherAddress> boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        addressRepository = new AddressRepository(boxStore);

        Observable<Balances> balance = etherApi.getBalance(Collections.singletonList(address));

        balance.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(balances -> saveAddress(balances.getResult(), address), this::handleBalanceError);

    }

    private void handleBalanceError(Throwable e) {
        Log.e(TAG, "Error fetching balances", e);
        status.setVisibility(View.VISIBLE);
        status.setText("Unable to save address. Check network.");
        okButton.setVisibility(View.VISIBLE);
    }

    private void saveAddress(List<Balance> balances, String address) {
        if (balances.isEmpty()) {
            Log.d(TAG, "No address found in etherscan for " + address);
            finish();
        }
        EtherAddress existingAddress = addressRepository.findOne(address);
        byte[] blockieArray = getBlockie(address);

        if (existingAddress == null) {
            EtherAddress newAddress = EtherAddress.newBuilder()
                    .setAddress(address)
                    .setBalance(balances.get(0).getBalance())
                    .setBlockie(blockieArray)
                    .build();

            addressRepository.put(newAddress);
            Log.d(TAG, "Added new address " + newAddress);
            finish();
        } else {
            EtherAddress addressToSave = EtherAddress.newBuilder(existingAddress)
                    .setBalance(balances.get(0).getBalance().toString())
                    .setBlockie(blockieArray)
                    .build();
            addressRepository.put(addressToSave);
            Log.d(TAG, "Address already exists " + existingAddress);
            finish();
        }
    }

    private byte[] getBlockie(String address) {
        Bitmap blockie = Blockies.createIcon(address, new Blockies.BlockiesOpts(6, 2, 2));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        blockie.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


}
