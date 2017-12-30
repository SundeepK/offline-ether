package com.example.sundeep.offline_ether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sundeep.offline_ether.App;
import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.adapters.TransactionsAdapter;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.relation.ToMany;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;

public class AccountActivity extends AppCompatActivity {

    private AddressRepository addressRepository;
    private TransactionsAdapter adapter;
    private final static String TAG = "AccountActivity";
    private Box<EtherAddress> addressboxStore;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.account);

        RecyclerView addressRecyclerView = findViewById(R.id.transactions_recycler_view);
        TextView balanceTextView = findViewById(R.id.balance);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(addressRecyclerView.getContext(),
                layoutManager.getOrientation());
        addressRecyclerView.addItemDecoration(dividerItemDecoration);

        String etherScanHost = getResources().getString(R.string.etherScanHost);
        EtherApi etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);
        String address = getIntent().getStringExtra(PUBLIC_ADDRESS);

        addressboxStore = ((App) getApplication()).getBoxStore().boxFor(EtherAddress.class);
        addressRepository = new AddressRepository(addressboxStore);

        EtherAddress etherAddress = addressRepository.findOne(address);
        ToMany<EtherTransaction> etherTransactions = etherAddress.getEtherTransactions();
        adapter = new TransactionsAdapter(etherTransactions);
        addressRecyclerView.setAdapter(adapter);

        etherApi.getTransactions(address, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(e -> Log.e(TAG, "Error fetching transactions", e))
                .subscribe(balances -> saveAddress(balances, etherAddress));


        FloatingActionButton fab = findViewById(R.id.new_offline_transaction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, OfflineTransactionActivity.class);
                intent.putExtra(PUBLIC_ADDRESS, address);
                startActivity(intent);
            }
        });
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
