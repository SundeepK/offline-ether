package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.activities.SendTransactionActivity;
import com.example.sundeep.offline_ether.mvc.views.SendTransactionView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SendTransactionActivityModule {

    @Binds
    abstract SendTransactionView provideMainView(SendTransactionActivity mainActivity);

}
