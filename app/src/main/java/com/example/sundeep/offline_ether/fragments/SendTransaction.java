package com.example.sundeep.offline_ether.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.activities.SendTransactionActivity;
import com.example.sundeep.offline_ether.activities.TransactionScannerActivity;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.ether.EtherApi;

import okhttp3.OkHttpClient;

import static com.example.sundeep.offline_ether.Constants.SIGNED_TRANSACTION;


public class SendTransaction extends Fragment {

    private static final String TAG = "SignOfflineTransaction";
    private Button scanQr;
    private Button sendTransaction;
    private String transaction;
    EtherApi etherApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String etherScanHost = getResources().getString(R.string.etherScanHost);
        etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.step_three_offline_transaction, container, false);

        scanQr = rootView.findViewById(R.id.scan_qr_code_button);
        sendTransaction = rootView.findViewById(R.id.send_transaction_button);
        sendTransaction.setVisibility(View.GONE);


        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendTransaction.this.getContext(), TransactionScannerActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String transaction = data.getStringExtra(SIGNED_TRANSACTION);
                Log.d(TAG, "found transaction " + transaction);
                sendTransaction.setVisibility(View.VISIBLE);
                Intent intent = new Intent(SendTransaction.this.getContext(), SendTransactionActivity.class);
                intent.putExtra(SIGNED_TRANSACTION, transaction);
                startActivity(intent);
                getActivity().finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "cancelled transaction " + transaction);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
