package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.presenters.AccountPresenter;
import com.github.sundeepk.offline.ether.mvc.views.AccountView;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class AccountPresenterModule {

    @Provides
    static AccountPresenter provideAccountPresenter(AccountView mainView, EtherApi etherApi, BoxStore boxStore) {
        return new AccountPresenter(mainView, boxStore.boxFor(EtherAddress.class), etherApi);
    }

}
