package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.activities.SendTransactionActivity;
import com.github.sundeepk.offline.ether.mvc.views.SendTransactionView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SendTransactionActivityModule {

    @Binds
    abstract SendTransactionView provideMainView(SendTransactionActivity mainActivity);

}
