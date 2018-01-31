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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sundeep.offline_ether.AddressRecyclerItemListener;
import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.adapters.AccountAdapter;
import com.example.sundeep.offline_ether.adapters.BalanceSlidePagerAdapter;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.presenters.MainPresenter;
import com.example.sundeep.offline_ether.mvc.views.MainView;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.reactive.DataSubscription;

public class MainActivity extends AppCompatActivity implements MainView {

    private final static String TAG = "MainActivity";
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> clazz;
    private DataSubscription observer;
    private List<EtherAddress> addressList = new ArrayList<>();
    private AccountAdapter adapter;
    private FloatingActionButton fab;
    private RecyclerView addressRecyclerView;
    private ViewPager balanceViewPager;
    private ImageButton nextCurrency;
    private ImageButton previousCurrency;
    private Box<EtherAddress> boxStore;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set views
        fab = findViewById(R.id.fab);
        addressRecyclerView = findViewById(R.id.address_recycler_view);
        balanceViewPager = findViewById(R.id.balance);
        nextCurrency = findViewById(R.id.next_currency_button);
        previousCurrency = findViewById(R.id.previous_currency_button);
        previousCurrency.setVisibility(View.GONE);
        boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);

        balanceViewPager.addOnPageChangeListener(onPageScrolled());
        nextCurrency.setOnClickListener(showFiatValue());
        previousCurrency.setOnClickListener(showEtherValue());

        setUpBalanceViewPager();

        setUpAddressRecyclerView();

        fab.setOnClickListener(view -> launchActivity(AddressScannerActivity.class));

        EtherApi etherApi = EtherApi.getEtherApi(getResources().getString(R.string.etherScanHost));
        mainPresenter = new MainPresenter(etherApi, boxStore, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.loadBalances();
    }

    private void setUpBalanceViewPager() {
        BalanceSlidePagerAdapter screenSlidePagerAdapter = new BalanceSlidePagerAdapter(getSupportFragmentManager());
        balanceViewPager.setAdapter(screenSlidePagerAdapter);
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
                new AddressRecyclerItemListener(this, addressList));
    }

    @NonNull
    private ViewPager.OnPageChangeListener onPageScrolled() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    nextCurrency.setVisibility(View.VISIBLE);
                    previousCurrency.setVisibility(View.GONE);
                } else {
                    nextCurrency.setVisibility(View.GONE);
                    previousCurrency.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    @NonNull
    private View.OnClickListener showEtherValue() {
        return v -> balanceViewPager.setCurrentItem(0);
    }

    @NonNull
    private View.OnClickListener showFiatValue() {
        return v -> balanceViewPager.setCurrentItem(1);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            clazz = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(clazz != null) {
                        Intent intent = new Intent(this, clazz);
                        startActivity(intent);
                    }
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
}
