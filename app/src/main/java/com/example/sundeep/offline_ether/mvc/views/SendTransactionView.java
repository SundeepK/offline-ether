package com.example.sundeep.offline_ether.mvc.views;

import com.example.sundeep.offline_ether.entities.SentTransaction;

public interface SendTransactionView {

    public void beforeSendingTransaction();

    public void onTransactionSent(SentTransaction sentTransaction);

    public void onTransactionBroadcastError(Throwable e);

    public void onComplete();

}
