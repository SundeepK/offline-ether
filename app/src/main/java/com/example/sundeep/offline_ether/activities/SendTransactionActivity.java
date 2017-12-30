package com.example.sundeep.offline_ether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.api.RestClient;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.SentTransaction;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static com.example.sundeep.offline_ether.Constants.SIGNED_TRANSACTION;

public class SendTransactionActivity extends AppCompatActivity {


    private final static String TAG = "SendTransactionActivty";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.send_transaction);
        ProgressBar progressBar = findViewById(R.id.send_transaction_progress);
        Button okButton = findViewById(R.id.close);

        String transaction = getIntent().getStringExtra(SIGNED_TRANSACTION);
        String etherScanHost = getResources().getString(R.string.etherScanHost);
        EtherApi etherApi = new EtherApi(new RestClient(new OkHttpClient()), etherScanHost);

        etherApi.sendTransaction(transaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SentTransaction>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(SentTransaction sentTransaction) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error sending transactions", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Completed transaction");
                        okButton.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SendTransactionActivity.this.finish();
                            }
                        });
                    }
                });

    }


}
