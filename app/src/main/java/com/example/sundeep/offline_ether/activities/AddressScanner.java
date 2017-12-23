package com.example.sundeep.offline_ether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.example.sundeep.offline_ether.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AddressScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String TAG = "AddressScanner";
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
        scannerView.stopCamera();
        Intent intent = new Intent(getBaseContext(), AddressAdder.class);
        intent.putExtra(AddressAdder.PUBLIC_ADDRESS, rawResult.getText());
        finish();
        startActivity(intent);
    }
}
