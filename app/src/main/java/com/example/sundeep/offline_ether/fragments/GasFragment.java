package com.example.sundeep.offline_ether.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.etherscan.EtherApiScan;
import com.example.sundeep.offline_ether.entities.EthGas;
import com.example.sundeep.offline_ether.entities.EthGasAndNonce;
import com.example.sundeep.offline_ether.entities.Nonce;

import java.math.BigDecimal;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;


public class GasFragment extends Fragment {

    private static final String TAG = "GasFragment";
    private double realGas;
    private BigDecimal curTxCost = new BigDecimal("0.000252");
    private String gasLimit = "21000";
    private SeekBar gasSeekBar;
    TextView slowGasText;
    TextView avgGasText;
    TextView fastGasText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.step_one_offline_transaction, container, false);
        gasSeekBar = rootView.findViewById(R.id.gas_seekbar);
        TextView gasText = rootView.findViewById(R.id.gas_textview);
        TextView gasCostText = rootView.findViewById(R.id.gas_cost_textview);
        slowGasText = rootView.findViewById(R.id.slow_gas);
        avgGasText = rootView.findViewById(R.id.avg_gas);
        fastGasText = rootView.findViewById(R.id.fast_gas);
        gasSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int gasInGwei, boolean b) {
                realGas = gasInGwei;
                gasText.setText(realGas + "");
                curTxCost = (new BigDecimal(gasLimit).multiply(new BigDecimal(realGas + "")));
                gasCostText.setText(curTxCost + "Gwei");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        gasSeekBar.setProgress(0);
        gasSeekBar.setMax(200);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String address = getArguments().getString(PUBLIC_ADDRESS);

        String etherScanHost = getResources().getString(R.string.etherScanHost);
        EtherApiScan etherApiScan = new EtherApiScan(new RestClient(new OkHttpClient()), etherScanHost);
        Observable<EthGas> ethgas = etherApiScan.getEthgas();
        Observable<Nonce> nonce = etherApiScan.getNonce(address);

        Observable.zip(ethgas, nonce, EthGasAndNonce::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(e -> Log.e(TAG, "Error fetching transactions", e))
                .subscribe(this::updateGasAndNonce);
    }

    private void updateGasAndNonce(EthGasAndNonce ethGasAndNonce) {
        EthGas ethGas = ethGasAndNonce.getEthGas();
        gasSeekBar.setMax((int) ethGas.getFastest() / 10);
        slowGasText.setText("Slow \n" + ethGas.getSafeLowWait() + "m");
        avgGasText.setText("Avg \n" + ethGas.getAvgWait() + "m");
        fastGasText.setText("Fast \n" + ethGas.getFastestWait() + "m");
        gasSeekBar.setProgress((int) ethGas.getAverage() / 10);
    }
}
