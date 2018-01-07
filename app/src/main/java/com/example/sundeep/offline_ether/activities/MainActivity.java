package com.example.sundeep.offline_ether.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.adapters.AccountAdapter;
import com.example.sundeep.offline_ether.adapters.BalanceSlidePagerAdapter;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.schedulers.Schedulers;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;

public class MainActivity extends AppCompatActivity {

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

        observeAddressChanges();

        fab.setOnClickListener(view -> launchActivity(AddressScannerActivity.class));

        findAndUpdateBalances(boxStore.query().build());
    }

    @NonNull
    private Box<EtherAddress> observeAddressChanges() {
        Query<EtherAddress> addressQuery = boxStore.query().build();
        observer = addressQuery
                .subscribe()
                .on(AndroidScheduler.mainThread())
                .observer(onDataChanged());
        return boxStore;
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
        return new RecyclerItemClickListener(this.getApplicationContext(), addressRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView img = view.findViewById(R.id.address_photo);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                                img, ViewCompat.getTransitionName(img));
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), AccountActivity.class);
                intent.putExtra(PUBLIC_ADDRESS, addressList.get(position).getAddress());
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        });
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

    private void findAndUpdateBalances(Query<EtherAddress> addressQuery) {
        EtherApi etherApi = EtherApi.getEtherApi(getResources().getString(R.string.etherScanHost));

        List<EtherAddress> etherAddresses = addressQuery.find();
        ImmutableMap<String, EtherAddress> addressToEtherAccount = Maps.uniqueIndex(etherAddresses, EtherAddress::getAddress);
        etherApi.getBalance(addressToEtherAccount.keySet())
                .subscribeOn(Schedulers.io())
                .subscribe(balances -> updateBalances(addressToEtherAccount, balances.getResult()), err -> Log.e(TAG, "Error occurred while updating balance", err));
    }

    private void updateBalances(Map<String, EtherAddress> addressToEtherAccount, List<Balance> balances) {
        final Box<EtherAddress> boxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        for (Balance balance : balances) {
            EtherAddress address = addressToEtherAccount.get(balance.getAccount());
            EtherAddress newAccount = EtherAddress.newBuilder(address)
                    .setBalance(balance.getBalance())
                    .build();
            boxStore.put(newAccount);
        }
    }

    @NonNull
    private DataObserver<List<EtherAddress>> onDataChanged() {
        return new DataObserver<List<EtherAddress>>() {
            @Override
            public void onData(List<EtherAddress> newAddresses) {
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
        };
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

}
