package com.example.sundeep.offline_ether.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.adapters.TransactionsAdapter;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.blockies.Blockies;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherAddress_;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;
import com.example.sundeep.offline_ether.utils.EtherMath;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;

public class AccountActivity extends AppCompatActivity {

    private final static String TAG = "AccountActivity";

    private AddressRepository addressRepository;
    private TransactionsAdapter adapter;
    private Box<EtherAddress> addressboxStore;
    private EtherAddress etherAddress;
    private String address;
    private EtherApi etherApi;
    private List<EtherTransaction> etherTransactionsList = new ArrayList<>();
    private DataSubscription observer;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.account);

        // views
        address = getIntent().getStringExtra(PUBLIC_ADDRESS);
        RecyclerView addressRecyclerView = findViewById(R.id.transactions_recycler_view);
        TextView balanceTextView = findViewById(R.id.balance);
        ImageView addressPhoto = findViewById(R.id.address_photo);
        TextView addressTextView = findViewById(R.id.address_textview);
        FloatingActionButton fab = findViewById(R.id.new_offline_transaction);

        addressPhoto.setImageBitmap(Blockies.createIcon(address, new Blockies.BlockiesOpts(20, 0, 0)));
        addressTextView.setText(address);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(addressRecyclerView.getContext(),
                layoutManager.getOrientation());
        addressRecyclerView.addItemDecoration(dividerItemDecoration);

        String etherScanHost = getResources().getString(R.string.etherScanHost);
        etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);

        addressboxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        addressRepository = new AddressRepository(addressboxStore);

        etherAddress = addressRepository.findOne(address);
        balanceTextView.setText(EtherMath.weiAsEtherStr(etherAddress.getBalance()));
        etherTransactionsList.addAll(etherAddress.getEtherTransactions());

        int paddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        adapter = new TransactionsAdapter(etherTransactionsList, address, drawable(R.drawable.orange_rounded_corner),
                drawable(R.drawable.green_rounded_corner), drawable(R.drawable.dark_rounded_corner), paddingPx);
        addressRecyclerView.setAdapter(adapter);

        fab.setOnClickListener(startOfflineTransaction(address));

        listenForTransactions();
    }

    private void listenForTransactions() {
        observer = addressboxStore.query()
                .equal(EtherAddress_.address, address)
                .build()
                .subscribe()
                .on(AndroidScheduler.mainThread())
                .observer(address -> {
                    // should only be one address
                    if (!address.isEmpty()) {
                        Log.d(TAG, "Update for " + etherAddress.getAddress() + " with transactions " + etherAddress.getEtherTransactions().size());
                        etherTransactionsList.clear();
                        etherTransactionsList.addAll(address.get(0).getEtherTransactions());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private Drawable drawable(int drawable) {
        return getResources().getDrawable(drawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Resume called");
        loadLast50Transactions();
    }

    @NonNull
    private View.OnClickListener startOfflineTransaction(String address) {
        return view -> {
            Intent intent = new Intent(AccountActivity.this, OfflineTransactionActivity.class);
            intent.putExtra(PUBLIC_ADDRESS, address);
            startActivity(intent);
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observer.cancel();
    }

    private void loadLast50Transactions() {
        etherApi.getTransactions(address, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(e -> Log.e(TAG, "Error fetching transactions", e))
                .subscribe(balances -> saveAddress(balances, etherAddress));
    }

    private void saveAddress(List<EtherTransaction> transactions, EtherAddress address) {
        Log.d(TAG, "On data found " + transactions);
        Log.d(TAG, "Existing transaction count" + address.getEtherTransactions().size());
        address.getEtherTransactions().clear();
        address.getEtherTransactions().addAll(transactions);
        addressboxStore.put(address);
        adapter.notifyDataSetChanged();
    }

}
