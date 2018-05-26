package com.github.sundeepk.offline.ether.mvc.views;

import com.github.sundeepk.offline.ether.entities.SentTransaction;

public interface SendTransactionView {

    public void beforeSendingTransaction();

    public void onTransactionSent(SentTransaction sentTransaction);

    public void onTransactionBroadcastError(Throwable e);

    public void onComplete();

}
