package com.example.sundeep.offline_ether.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.activities.SendTransactionActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.sundeep.offline_ether.Constants.SIGNED_TRANSACTION;


public class SendTransaction extends Fragment implements ZXingScannerView.ResultHandler {

    private static final String TAG = "SignOfflineTransaction";
    private ZXingScannerView scannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.step_three_offline_transaction, container, false);
        scannerView = rootView.findViewById(R.id.qr_scanner);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void handleResult(Result result) {
        String transaction = result.getText();
        Log.d(TAG, "found transaction " + transaction);
        Intent intent = new Intent(SendTransaction.this.getContext(), SendTransactionActivity.class);
        intent.putExtra(SIGNED_TRANSACTION, transaction);
        startActivity(intent);
        getActivity().finish();
    }
}
