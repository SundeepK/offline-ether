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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.adapters.GasPricesAdapter;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.GasPrice;
import com.example.sundeep.offline_ether.entities.Nonce;
import com.example.sundeep.offline_ether.mvc.presenters.EthGasPresenter;
import com.example.sundeep.offline_ether.mvc.views.EthGasView;
import com.example.sundeep.offline_ether.recycler.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;


public class GasFragment extends Fragment implements EthGasView {

    private static final String TAG = "GasFragment";
    private List<GasPrice> gasPrices = new ArrayList<>();
    private GasPricesAdapter adapter;
    private int selected = -1;
    private OnGasSelectedListener onGasSelectedListener;
    private Nonce nonce = null;
    private EthGasPresenter ethGasPresenter;

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

        EtherApi etherApi = EtherApi.getEtherApi(getResources().getString(R.string.etherScanHost));
        ethGasPresenter = new EthGasPresenter(etherApi, this);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ethGasPresenter.destroy();
    }

    @NonNull
    private RecyclerItemClickListener getGasPriceOnClick(RecyclerView gasPricesRecyclerView) {
        return new RecyclerItemClickListener(this.getContext(), gasPricesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, MotionEvent e) {
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
        if (!gasPrices.isEmpty()) {
            return;
        }
        String address = getArguments().getString(PUBLIC_ADDRESS);
        ethGasPresenter.loadEthGasData(address);
    }

    @Override
    public void onEthGasPrice(List<GasPrice> prices) {
        gasPrices.clear();
        gasPrices.addAll(prices);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNonce(Nonce loadedNonce) {
        nonce = loadedNonce;
        Log.d(TAG, "nonce" + nonce);
    }

    @Override
    public void onErrorLoadingEthGasAndNonce(Throwable e) {
        Log.e(TAG, "Error fetching transactions", e);
    }

}
