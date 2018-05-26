package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.mvc.presenters.SendTransactionPresenter;
import com.github.sundeepk.offline.ether.mvc.views.SendTransactionView;

import dagger.Module;
import dagger.Provides;

@Module
public class SendTransactionPresenterModule {

    @Provides
    public SendTransactionPresenter provideSendTransactionPresenter(SendTransactionView sendTransactionView, EtherApi etherApi) {
        return new SendTransactionPresenter(etherApi, sendTransactionView);
    }

}
