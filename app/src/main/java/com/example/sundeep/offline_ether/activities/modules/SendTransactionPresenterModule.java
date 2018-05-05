package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.mvc.presenters.SendTransactionPresenter;
import com.example.sundeep.offline_ether.mvc.views.SendTransactionView;

import dagger.Module;
import dagger.Provides;

@Module
public class SendTransactionPresenterModule {

    @Provides
    public SendTransactionPresenter provideSendTransactionPresenter(SendTransactionView sendTransactionView, EtherApi etherApi) {
        return new SendTransactionPresenter(etherApi, sendTransactionView);
    }

}
