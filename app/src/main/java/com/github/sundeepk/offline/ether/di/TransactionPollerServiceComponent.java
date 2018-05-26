package com.github.sundeepk.offline.ether.di;

import com.github.sundeepk.offline.ether.service.TransactionPollerService;

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
