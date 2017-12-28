package com.example.sundeep.offline_ether.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sundeep.offline_ether.R;

import java.math.BigDecimal;


public class GasFragment extends Fragment {

    private double realGas;
    private BigDecimal curTxCost = new BigDecimal("0.000252");
    private String gasLimit = "21000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.step_one_offline_transaction, container, false);
        SeekBar gas = rootView.findViewById(R.id.gas_seekbar);
        TextView gasText = rootView.findViewById(R.id.gas_textview);
        gas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                realGas = 0;
                realGas = (i - 8);
                if (i < 10)
                    realGas = (double) (i + 1) / 10d;

                gasText.setText((realGas + "").replaceAll(".0", ""));
                curTxCost = (new BigDecimal(gasLimit).multiply(new BigDecimal(realGas + ""))).divide(new BigDecimal("1000000000"), 6, BigDecimal.ROUND_DOWN);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        gas.setProgress(0);

        return rootView;
    }
}
