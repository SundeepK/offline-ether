package com.example.sundeep.offline_ether.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.adapters.AccountAdapter;
import com.example.sundeep.offline_ether.adapters.BalanceSlidePagerAdapter;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.presenters.CurrencySelectedPageListener;
import com.example.sundeep.offline_ether.mvc.presenters.MainPresenter;
import com.example.sundeep.offline_ether.mvc.views.MainView;
import com.example.sundeep.offline_ether.recycler.listener.AddressRecyclerItemListener;
import com.example.sundeep.offline_ether.recycler.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainView, AddressRecyclerItemListener.OnAccountDeleteListener {

    private final static String TAG = "MainActivity";
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private List<EtherAddress> addressList = new ArrayList<>();
    private AccountAdapter adapter;
    private RecyclerView addressRecyclerView;
    private ViewPager balanceViewPager;
    private ImageButton nextCurrency;
    private ImageButton previousCurrency;

    @Inject MainPresenter mainPresenter;
    @Inject BalanceSlidePagerAdapter balanceSlidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set views
        FloatingActionButton fab = findViewById(R.id.fab);
        addressRecyclerView = findViewById(R.id.address_recycler_view);
        balanceViewPager = findViewById(R.id.balance);
        nextCurrency = findViewById(R.id.next_currency_button);
        previousCurrency = findViewById(R.id.previous_currency_button);
        previousCurrency.setVisibility(View.GONE);

        balanceViewPager.addOnPageChangeListener(new CurrencySelectedPageListener(this));
        nextCurrency.setOnClickListener(showFiatValue());
        previousCurrency.setOnClickListener(showEtherValue());

        setUpBalanceViewPager();

        setUpAddressRecyclerView();

        fab.setOnClickListener(view -> mainPresenter.addAccount());
        mainPresenter.observeAddressChnage();
        mainPresenter.loadBalances();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.loadBalances();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.destroy();
    }

    private void setUpBalanceViewPager() {
        balanceViewPager.setAdapter(balanceSlidePagerAdapter);
    }

    private void setUpAddressRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressRecyclerView.setLayoutManager(layoutManager);
        adapter = new AccountAdapter(addressList);
        addressRecyclerView.setAdapter(adapter);
        addressRecyclerView.addOnItemTouchListener(showAccountOnClick());
    }

    @NonNull
    private RecyclerItemClickListener showAccountOnClick() {
        return new RecyclerItemClickListener(this.getApplicationContext(),
                addressRecyclerView,
                new AddressRecyclerItemListener(this, addressList, this));
    }

    @NonNull
    private View.OnClickListener showEtherValue() {
        return v -> balanceViewPager.setCurrentItem(0);
    }

    @NonNull
    private View.OnClickListener showFiatValue() {
        return v -> balanceViewPager.setCurrentItem(1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, AddressScannerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void loadBalances(List<EtherAddress> newAddresses) {
        Log.d(TAG, "Found address on update:" + newAddresses.toString());
        View noAddressMessage = findViewById(R.id.no_address_message);
        if (newAddresses.isEmpty()) {
            noAddressMessage.setVisibility(View.VISIBLE);
        } else {
            noAddressMessage.setVisibility(View.GONE);
            addressList.clear();
            addressList.addAll(newAddresses);
            Log.d(TAG, "addressList:" + addressList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAccountLoadError(Throwable t) {
        Log.e(TAG, "Error occurred while updating balance", t);
    }

    @Override
    public void onEtherSelected() {
        nextCurrency.setVisibility(View.VISIBLE);
        previousCurrency.setVisibility(View.GONE);
    }

    @Override
    public void onCurrencySelected() {
        nextCurrency.setVisibility(View.GONE);
        previousCurrency.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddAccount() {
        Class<AddressScannerActivity> clazz = AddressScannerActivity.class;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        }
    }

    @Override
    public void onAccountDelete(EtherAddress etherAddress) {
        mainPresenter.deleteAccount(etherAddress);
    }
}
