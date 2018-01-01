package com.example.sundeep.offline_ether.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.example.sundeep.offline_ether.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.sundeep.offline_ether.Constants.SIGNED_TRANSACTION;

public class TransactionScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String TAG = "OfflineTransactionSc";
    private ZXingScannerView scannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.address_scanner);

        ViewGroup contentFrame = findViewById(R.id.content_frame);
        scannerView = new ZXingScannerView(this);
        contentFrame.addView(scannerView);
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
    public void handleResult(Result rawResult) {
        Log.d(TAG, rawResult.getText());
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SIGNED_TRANSACTION, rawResult.getText());
        returnIntent.putExtra("QR_DATA", rawResult.getRawBytes());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
