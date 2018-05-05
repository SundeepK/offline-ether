package com.example.sundeep.offline_ether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.SentTransaction;
import com.example.sundeep.offline_ether.mvc.presenters.SendTransactionPresenter;
import com.example.sundeep.offline_ether.mvc.views.SendTransactionView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.example.sundeep.offline_ether.Constants.SIGNED_TRANSACTION;

public class SendTransactionActivity extends AppCompatActivity implements SendTransactionView {

    private final static String TAG = "SendTransactionActivity";
    private ProgressBar progressBar;
    private FancyButton okButton;
    private FancyButton sendButton;
    private TextView message;

    @Inject SendTransactionPresenter sendTransactionPresenter;

    @Override
    public void onCreate(Bundle state) {
        AndroidInjection.inject(this);
        super.onCreate(state);
        setContentView(R.layout.send_transaction);

        // views
        progressBar = findViewById(R.id.send_transaction_progress);
        okButton = findViewById(R.id.close_button);
        sendButton = findViewById(R.id.send_transaction_button);
        message = findViewById(R.id.transaction_send_msg_textview);
        message.setVisibility(View.VISIBLE);
        message.setText("Send transaction?");
        okButton.setVisibility(View.GONE);

        String transaction = getIntent().getStringExtra(SIGNED_TRANSACTION);

        okButton.setOnClickListener(view -> SendTransactionActivity.this.finish());
        sendButton.setOnClickListener(view -> sendTransactionPresenter.sendTransaction(transaction));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendTransactionPresenter.destroy();
    }

    @Override
    public void beforeSendingTransaction() {
        sendButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        message.setText("Broadcasting transaction...");
    }

    @Override
    public void onTransactionSent(SentTransaction sentTransaction) {
        message.setVisibility(View.VISIBLE);
        if (sentTransaction.getError() != null) {
            sendButton.setText("Retry");
            sendButton.setVisibility(View.VISIBLE);
            message.setText("Error occurred sending transaction. \n" + sentTransaction.getError().getMessage());
        } else {
            okButton.setVisibility(View.VISIBLE);
            message.setText("Transaction successfully sent.");
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTransactionBroadcastError(Throwable e) {
        Log.e(TAG, "Error sending transactions", e);
        progressBar.setVisibility(View.GONE);
        sendButton.setText("Retry");
        sendButton.setVisibility(View.VISIBLE);
        message.setText("Error occurred sending transaction. \nCheck network settings." );
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "Completed transaction");
    }
}
