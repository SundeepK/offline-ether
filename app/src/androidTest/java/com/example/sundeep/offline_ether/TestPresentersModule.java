package com.example.sundeep.offline_ether;

import com.example.sundeep.offline_ether.mvc.presenters.AccountPresenter;
import com.example.sundeep.offline_ether.mvc.presenters.BalanceCurrencyPresenter;
import com.example.sundeep.offline_ether.mvc.presenters.BalanceEtherPresenter;
import com.example.sundeep.offline_ether.mvc.presenters.MainPresenter;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class TestPresentersModule {

    private AccountPresenter accountPresenter = Mockito.mock(AccountPresenter.class);
    private MainPresenter mainPresenter = Mockito.mock(MainPresenter.class);
    private BalanceEtherPresenter balanceEtherPresenter = Mockito.mock(BalanceEtherPresenter.class);
    private BalanceCurrencyPresenter balanceCurrencyPresenter = Mockito.mock(BalanceCurrencyPresenter.class);

    @Provides
    public MainPresenter provideMainPresenter() {
        return mainPresenter;
    }

    @Provides
    public BalanceEtherPresenter providesBalanceEtherPresenter() {
        return balanceEtherPresenter;
    }

    @Provides
    public BalanceCurrencyPresenter providesBalanceCurrencyPresenter() {
        return balanceCurrencyPresenter;
    }

    @Provides
    public AccountPresenter providesAccountPresenter(){
        return accountPresenter;
    }

}