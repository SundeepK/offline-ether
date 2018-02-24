package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.SentTransaction;
import com.example.sundeep.offline_ether.mvc.views.SendTransactionView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SendTransactionPresenter {

    private EtherApi etherApi;
    private Disposable disposable;
    private SendTransactionView sendTransactionView;

    public SendTransactionPresenter(EtherApi etherApi, SendTransactionView sendTransactionView) {
        this.etherApi = etherApi;
        this.sendTransactionView = sendTransactionView;
    }

    public void sendTransaction(String transaction) {
        etherApi.sendTransaction(transaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SentTransaction>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        sendTransactionView.beforeSendingTransaction();
                    }

                    @Override
                    public void onNext(SentTransaction sentTransaction) {
                        sendTransactionView.onTransactionSent(sentTransaction);
                    }

                    @Override
                    public void onError(Throwable e) {
                        sendTransactionView.onTransactionBroadcastError(e);
                    }

                    @Override
                    public void onComplete() {
                        sendTransactionView.onComplete();
                    }
                });
    }

    public void destroy(){
        if (disposable != null) {
            disposable.dispose();
        }
    }


}
