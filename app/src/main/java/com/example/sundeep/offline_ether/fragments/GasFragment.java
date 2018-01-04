package com.example.sundeep.offline_ether.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.activities.RecyclerItemClickListener;
import com.example.sundeep.offline_ether.adapters.GasPricesAdapter;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EthGas;
import com.example.sundeep.offline_ether.entities.EthGasAndNonce;
import com.example.sundeep.offline_ether.entities.GasPrice;
import com.example.sundeep.offline_ether.entities.Nonce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;


public class GasFragment extends Fragment {

    private static final String TAG = "GasFragment";
    private double realGas;
    private BigDecimal curTxCost = new BigDecimal("0.000252");
    private String gasLimit = "21000";
    private List<GasPrice> gasPrices = new ArrayList<>();
    private GasPricesAdapter adapter;
    private int selected = -1;
    private OnGasSelectedListener onGasSelectedListener;
    private Nonce nonce = null;
    private Disposable subscribe;

    public interface OnGasSelectedListener {
        public void onGasSelected(GasPrice gasPrice, Nonce nonce);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.step_one_offline_transaction, container, false);
        RecyclerView gasPricesRecyclerView = rootView.findViewById(R.id.gas_prices_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        gasPricesRecyclerView.setLayoutManager(layoutManager);
        adapter = new GasPricesAdapter(gasPrices);
        gasPricesRecyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(gasPricesRecyclerView.getContext(),
                layoutManager.getOrientation());
        gasPricesRecyclerView.addItemDecoration(dividerItemDecoration);

        gasPricesRecyclerView.addOnItemTouchListener(getGasPriceOnClick(gasPricesRecyclerView));

        return rootView;
    }

    @NonNull
    private RecyclerItemClickListener getGasPriceOnClick(RecyclerView gasPricesRecyclerView) {
        return new RecyclerItemClickListener(this.getContext(), gasPricesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (selected >= 0) {
                    GasPrice gasPrice = gasPrices.get(selected);
                    gasPrices.set(selected, GasPrice.newBuilder(gasPrice).setIsSelected(false).build());
                    adapter.notifyItemChanged(selected);
                }
                selected = position;
                GasPrice gasPrice = gasPrices.get(selected);
                gasPrices.set(selected, GasPrice.newBuilder(gasPrice).setIsSelected(true).build());
                onGasSelectedListener.onGasSelected(gasPrice, nonce);
                adapter.notifyItemChanged(selected);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onGasSelectedListener = (OnGasSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (subscribe != null) {
            return;
        }

        String address = getArguments().getString(PUBLIC_ADDRESS);

        String etherScanHost = getResources().getString(R.string.etherScanHost);
        EtherApi etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);
        Observable<EthGas> ethgas = etherApi.getEthgas();
        Observable<Nonce> nonce = etherApi.getNonce(address);

        subscribe = Observable.zip(ethgas, nonce, EthGasAndNonce::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(e -> Log.e(TAG, "Error fetching transactions", e))
                .subscribe(this::updateGasAndNonce);
    }

    private void updateGasAndNonce(EthGasAndNonce ethGasAndNonce) {
        gasPrices.clear();
        EthGas ethGas = ethGasAndNonce.getEthGas();
        gasPrices.add(new GasPrice("Slow", ethGas.getSafeLow() / 10, ethGas.getSafeLowWait(), false));
        gasPrices.add(new GasPrice("Average", ethGas.getAverage() / 10, ethGas.getAvgWait(), false));
        gasPrices.add(new GasPrice("Fast", ethGas.getFast() / 10, ethGas.getFastWait(), false));
        nonce = ethGasAndNonce.getNonce();
        Log.d(TAG, "nonce" + nonce);
        adapter.notifyDataSetChanged();
    }
}
