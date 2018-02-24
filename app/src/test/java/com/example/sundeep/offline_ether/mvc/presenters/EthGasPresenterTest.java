package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EthGas;
import com.example.sundeep.offline_ether.entities.GasPrice;
import com.example.sundeep.offline_ether.entities.Nonce;
import com.example.sundeep.offline_ether.mvc.views.EthGasView;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EthGasPresenterTest {

    private static final String ETHER_ADDRESS = "ether:address";

    @Mock private EtherApi etherApi;
    @Mock private EthGasView ethGasView;

    private EthGasPresenter underTest;

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
        underTest = new EthGasPresenter(etherApi, ethGasView);
    }

    @Test
    public void testItLoadsEthGasData(){
        EthGas ethGas = EthGas.newBuilder()
                .setAvgWait(2)
                .setAverage(2)
                .setFast(3)
                .setFastest(4)
                .setFastWait(1)
                .setFastestWait(1)
                .setSafeLow(1)
                .setSafeLowWait(5)
                .setSpeed(1)
                .setBlockNum(1)
                .build();
        Observable<EthGas> observable = Observable.just(ethGas);
        Observable<Nonce> nonceObservable = Observable.just(new Nonce("1"));
        when(etherApi.getEthgas()).thenReturn(observable);
        when(etherApi.getNonce(ETHER_ADDRESS)).thenReturn(nonceObservable);

        underTest.loadEthGasData(ETHER_ADDRESS);


        List<GasPrice> gasPrices = new ArrayList<>();
        gasPrices.add(new GasPrice("Slow", 1f / 10, 5, false));
        gasPrices.add(new GasPrice("Average", 2f / 10, 2, false));
        gasPrices.add(new GasPrice("Fast", 3f / 10, 1, false));

        verify(ethGasView).onEthGasPrice(gasPrices);
        verify(ethGasView).onNonce(new Nonce("1"));
    }

    @Test
    public void testCallsOnError(){
        Observable<EthGas> observable = Observable.just(EthGas.newBuilder().build());
        IOException ioException = new IOException("Error fetching nonce");
        Observable<Nonce> nonceObservable = Observable.fromCallable(() -> { throw ioException;});
        when(etherApi.getEthgas()).thenReturn(observable);
        when(etherApi.getNonce(ETHER_ADDRESS)).thenReturn(nonceObservable);

        underTest.loadEthGasData(ETHER_ADDRESS);

        verify(ethGasView).onErrorLoadingEthGasAndNonce(ioException);
        verifyNoMoreInteractions(ethGasView);
    }



}