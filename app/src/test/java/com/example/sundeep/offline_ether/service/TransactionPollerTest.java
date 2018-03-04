package com.example.sundeep.offline_ether.service;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.objectbox.Box;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.reactive.SubscriptionBuilder;
import io.objectbox.relation.ToMany;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionPollerTest {

    private static final String ETHER_ADDRESS = "ether:address";

    @Mock private EtherApi etherApi;
    @Mock private QueryBuilder queryBuilder;
    @Mock private Query query;
    @Mock private SubscriptionBuilder subscriptionBuilder;
    @Mock private ToMany<EtherTransaction> toMany;
    @Mock private Box<EtherAddress> etherAddressBox;
    @Mock private DataSubscription subscription;
    private EtherAddress address;

    private Map<String, Long> cache;
    private TransactionPoller underTest;

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Before
    public void setUp(){
        address = EtherAddress.newBuilder()
                .setAddress(ETHER_ADDRESS)
                .setId(1)
                .setBalance("1")
                .setEtherTransactions(toMany)
                .build();
                cache = new ConcurrentHashMap<>();
    }

    @Test
    public void testItPollsAddressTransactions() throws Exception {
        // given
        givenEtherAddress(this::answerOneAddress);
        EtherTransaction eth1 = EtherTransaction.newBuilder().setHash("1").build();
        EtherTransaction eth2 = EtherTransaction.newBuilder().setHash("2").build();

        List<EtherTransaction> transactions = Arrays.asList(eth1, eth2);
        Observable<List<EtherTransaction>> observable = Observable.just(transactions);
        when(etherApi.getTransactions(ETHER_ADDRESS, 1)).thenReturn(observable);
        when(etherAddressBox.get(1)).thenReturn(address);

        // when
        underTest = new TransactionPoller(etherApi, etherAddressBox, cache, 1, TimeUnit.MINUTES);
        underTest.pollTransactions();

        // then
        verify(etherAddressBox).put(address);
        verify(toMany).addAll(transactions);
    }

    private DataSubscription answerOneAddress(InvocationOnMock invocation) throws Throwable {
        DataObserver observer = (DataObserver) invocation.getArguments()[0];

        observer.onData(Collections.singletonList(address));
        return subscription;
    }

    private void givenEtherAddress(Answer answers) {
        when(subscriptionBuilder.observer(any(DataObserver.class))).then(answers);
        when(query.subscribe()).thenReturn(subscriptionBuilder);
        when(queryBuilder.build()).thenReturn(query);
        when(subscriptionBuilder.on(any())).thenReturn(subscriptionBuilder);
        when(subscriptionBuilder.onError(any())).thenReturn(subscriptionBuilder);
        when(queryBuilder.equal(any(Property.class), eq(ETHER_ADDRESS))).thenReturn(queryBuilder);
        when(etherAddressBox.query()).thenReturn(queryBuilder);
    }

}