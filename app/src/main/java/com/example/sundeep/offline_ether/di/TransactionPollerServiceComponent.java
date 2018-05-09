package com.example.sundeep.offline_ether.di;

import com.example.sundeep.offline_ether.service.TransactionPollerService;

import dagger.Subcomponent;

@Subcomponent(modules = TransactionPollerServiceModule.class)
public interface TransactionPollerServiceComponent {

    @Subcomponent.Builder
    public interface Builder {
        Builder withServiceModule(TransactionPollerServiceModule serviceModule);
        TransactionPollerServiceComponent build();
    }

    void inject(TransactionPollerService transactionPollerService);

}
