package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.SentTransaction;
import com.example.sundeep.offline_ether.mvc.views.SendTransactionView;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendTransactionPresenterTest {

    private static final String TRANSACTION = "1";

    @Mock private EtherApi etherApi;
    @Mock private SendTransactionView sendTransactionView;

    private SendTransactionPresenter underTest;

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }

    @Before
    public void setUp(){
        underTest = new SendTransactionPresenter(etherApi, sendTransactionView);
    }

    @Test
    public void testItLoadsTransactions(){
        Observable<SentTransaction> transactionResp = Observable.just(new SentTransaction(null, "success"));
        when(etherApi.sendTransaction(TRANSACTION)).thenReturn(transactionResp);

        underTest.sendTransaction(TRANSACTION);

        verify(sendTransactionView).beforeSendingTransaction();
        verify(sendTransactionView).onTransactionSent(new SentTransaction(null, "success"));
        verify(sendTransactionView).onComplete();
    }

    @Test
    public void testItCallsOnError(){
        IOException error = new IOException("Error");
        Observable<SentTransaction> transactionResp = Observable.fromCallable(() -> { throw error;});
        when(etherApi.sendTransaction(TRANSACTION)).thenReturn(transactionResp);

        underTest.sendTransaction(TRANSACTION);

        verify(sendTransactionView).onTransactionBroadcastError(error);
        verify(sendTransactionView).beforeSendingTransaction();
        verifyNoMoreInteractions(sendTransactionView);
    }

}
