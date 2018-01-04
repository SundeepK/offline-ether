package com.example.sundeep.offline_ether.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sundeep.offline_ether.R;

import static com.example.sundeep.offline_ether.Constants.GAS_PRICE;
import static com.example.sundeep.offline_ether.Constants.NONCE;
import static com.example.sundeep.offline_ether.Constants.TYPE;
import static com.example.sundeep.offline_ether.Constants.WAIT_TIME;


public class OfflineTransaction extends Fragment {

    private static final String TAG = "SignOfflineTransaction";
    TextView typeTextView;
    TextView gasPriceTextView;
    TextView limitTextView;
    TextView gasWaitTextView;
    TextView nonceTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.step_two_offline_transaction, container, false);

        // views
        typeTextView = rootView.findViewById(R.id.type_textview);
        gasPriceTextView = rootView.findViewById(R.id.gas_price_textview);
        limitTextView = rootView.findViewById(R.id.gas_limit_textview);
        gasWaitTextView = rootView.findViewById(R.id.gas_wait_textview);
        nonceTextView = rootView.findViewById(R.id.nonce_textview);

        getUi();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUi();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getUi();
        }
    }

    private void getUi() {
        Bundle sharedBundle = getArguments();
        float price = sharedBundle.getFloat(GAS_PRICE);
        float wait = sharedBundle.getFloat(WAIT_TIME);
        String type = sharedBundle.getString(TYPE);
        String nonce = sharedBundle.getString(NONCE);
        typeTextView.setText(type);
        gasWaitTextView.setText(wait + "mins");
        gasPriceTextView.setText(price + "Gwei");
        if (nonce != null){
            nonceTextView.setText(Long.decode(nonce) + "");
        }
        limitTextView.setText("21000");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
