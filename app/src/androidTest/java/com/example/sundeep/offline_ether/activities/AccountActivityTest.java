package com.example.sundeep.offline_ether.activities;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.TestApp;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.mvc.presenters.AccountPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AccountActivityTest {

    private static final String ADDRESS_1 = "address1";
    private static final String ADDRESS_2 = "address2";

    @Inject
    AccountPresenter accountPresenter;

    @Rule
    public ActivityTestRule<AccountActivity> activityRule =
            new ActivityTestRule<AccountActivity>(AccountActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent();
                    result.putExtra(PUBLIC_ADDRESS, ADDRESS_1);
                    return result;
                }
            };

    @Before
    public void setUp(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        TestApp app
                = (TestApp) instrumentation.getTargetContext().getApplicationContext();
        app.getAppComponent().inject(this);
    }

    @Test
    public void testItLoadsAddress() throws Exception {
        AccountActivity activity = activityRule.getActivity();
        byte[] blockie = new byte[0];

                EtherAddress etherAddress = EtherAddress.newBuilder()
                .setBlockie(blockie)
                .setAddress(ADDRESS_1)
                .setBalance("6100000000000000000")
                .build();
        activity.runOnUiThread(() -> activity.onAddressLoad(etherAddress));

        // verify load account onCreate
        verify(accountPresenter).loadAddress(ADDRESS_1);
        onView(withId(R.id.address_textview)).check(matches(withText(ADDRESS_1)));
        onView(withId(R.id.balance)).check(matches(withText("6.1000 ETH")));
    }

    @Test
    public void testItLoadsTransactions() throws Exception {
        AccountActivity activity = activityRule.getActivity();

        List<EtherTransaction> transactions = new ArrayList<>();
        EtherTransaction ethT1 = EtherTransaction.newBuilder()
                .setBlockHash("1")
                .setBlockNumber("1")
                .setValue("4000000000000000000")
                .setConfirmations(1)
                .setTimeStamp(0L)
                .build();
        EtherTransaction ethT2 = EtherTransaction.newBuilder()
                .setBlockHash("2")
                .setBlockNumber("2")
                .setValue("2200000000000000000")
                .setConfirmations(2)
                .setTimeStamp(1L)
                .build();
        transactions.add(ethT1);
        transactions.add(ethT2);

        activity.runOnUiThread(() -> activity.onTransactions(transactions));

        //  item 1
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("4.0000 ETH"))));
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("Confirmations: 1"))));
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("PEND"))));

        // items 2
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("2.2000 ETH"))));
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("Confirmations: 2"))));
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("PEND"))));
        RecyclerView recyclerView = activity.findViewById(R.id.transactions_recycler_view);
        assertEquals(2, recyclerView.getAdapter().getItemCount());
    }

    @Test
    public void testItLoadsShowsStatusOfTransactions() throws Exception {
        AccountActivity activity = activityRule.getActivity();

        List<EtherTransaction> transactions = new ArrayList<>();
        EtherTransaction ethT1 = EtherTransaction.newBuilder()
                .setFrom(ADDRESS_1)
                .setValue("4000000000000000000")
                .setConfirmations(20)
                .build();
        EtherTransaction ethT2 = EtherTransaction.newBuilder()
                .setFrom(ADDRESS_2)
                .setValue("2000000000000000000")
                .setConfirmations(40)
                .build();
        transactions.add(ethT1);
        transactions.add(ethT2);

        activity.runOnUiThread(() -> activity.onTransactions(transactions));

        //  item 1
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("OUT"))));

        // items 2
        onView(withId(R.id.transactions_recycler_view))
                .check(matches(hasDescendant(withText("IN"))));

        RecyclerView recyclerView = activity.findViewById(R.id.transactions_recycler_view);
        assertEquals(2, recyclerView.getAdapter().getItemCount());
    }




}