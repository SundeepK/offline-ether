package com.github.sundeepk.offline.ether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.presenters.AddressAdderPresenter;
import com.github.sundeepk.offline.ether.mvc.views.AddressAdderView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.github.sundeepk.offline.ether.Constants.PUBLIC_ADDRESS;

public class AddressAdderActivity extends AppCompatActivity implements AddressAdderView {

    private final static String TAG = "AddressAdder";

    private TextView status;
    private FancyButton okButton;

    @Inject
    AddressAdderPresenter addressAdderPresenter;

    @Override
    public void onCreate(Bundle state) {
        AndroidInjection.inject(this);
        super.onCreate(state);
        setContentView(R.layout.address_adder);

        status = findViewById(R.id.status_message);
        okButton = findViewById(R.id.ok_button);

        okButton.setOnClickListener(v -> finish());

        String address = getIntent().getStringExtra(PUBLIC_ADDRESS);
        Log.d(TAG, "Started with" + address);

        addressAdderPresenter.saveAddress(address);
    }

    @Override
    public void onAddressUpdate(EtherAddress updatedAdd) {
        finish();
    }

    @Override
    public void onNoAddress() {
        finish();
    }

    @Override
    public void onBalanceFetchError(Throwable e) {
        Log.e(TAG, "Error fetching balances", e);
        status.setVisibility(View.VISIBLE);
        status.setText("Unable to save address. Check network.");
        okButton.setVisibility(View.VISIBLE);
    }
}
