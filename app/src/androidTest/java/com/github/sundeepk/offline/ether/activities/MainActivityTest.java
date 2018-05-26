package com.github.sundeepk.offline.ether.activities;

import android.Manifest;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.TestApp;
import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.fragments.BalanceCurrencyFragment;
import com.github.sundeepk.offline.ether.fragments.BalanceEtherFragment;
import com.github.sundeepk.offline.ether.mvc.presenters.BalanceEtherPresenter;
import com.github.sundeepk.offline.ether.mvc.presenters.MainPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.sundeepk.offline.ether.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Inject
    MainPresenter mainPresenter;

    @Inject
    BalanceEtherPresenter balanceEtherPresenter;

    @Inject
    EtherApi etherApi;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Before
    public void setUp(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        TestApp app
                = (TestApp) instrumentation.getTargetContext().getApplicationContext();
        app.getAppComponent().inject(this);
    }

    @Test
    public void testItShowsAddresses() throws Exception {
        EtherAddress add1 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("1").setBalance("1").build();
        EtherAddress add2 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("2").setBalance("2").build();
        List<EtherAddress> etherAddresses = Arrays.asList(add1, add2);

        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(etherAddresses));
        onView(withId(R.id.address_recycler_view)).check(withItemCount(2));
        onView(withId(R.id.address_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testItRemovesAddressThrowMenu() throws Exception {
        EtherAddress add1 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("1").setBalance("1").build();
        List<EtherAddress> etherAddresses = Arrays.asList(add1);

        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(etherAddresses));
        onView(withId(R.id.address_recycler_view)).check(withItemCount(1));

        onView(withId(R.id.more_options)).perform(click());
        onView(withText("Delete")).perform(click());

        onView(withId(R.id.address_recycler_view)).check(withItemCount(0));
    }

    @Test
    public void testItRemovesAddressWhenListHasMultipleAddresses() throws Exception {
        EtherAddress add1 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("1").setBalance("1").build();
        EtherAddress add2 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("2").setBalance("2").build();
        EtherAddress add3 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("3").setBalance("3").build();
        List<EtherAddress> etherAddresses = Arrays.asList(add1, add2, add3);

        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(etherAddresses));
        onView(withId(R.id.address_recycler_view)).check(withItemCount(3));

        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().onAccountDelete(add2));

        onView(withId(R.id.address_recycler_view)).check(withItemCount(2));
    }

    @Test
    public void testItShowsAddAddressActivity() throws InterruptedException {
        Intents.init();
        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().onAddAccount());

        intended(hasComponent(AddressScannerActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testItShowsNoAddressMessageIfNoAddressesAreRegistered() throws InterruptedException {
        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(new ArrayList<>()));
        onView(withId(R.id.no_address_message)).check(matches(isDisplayed()));
    }

    @Test
    public void testItShowsEtherBalance() throws InterruptedException {
        BalanceEtherFragment fragment = (BalanceEtherFragment) activityRule.getActivity().getSupportFragmentManager().getFragments().get(0);
        activityRule.getActivity().runOnUiThread(() -> fragment.onEtherBalanceLoad("17Eth"));
        onView(withText("17Eth")).check(matches(isDisplayed()));
    }

    @Test
    public void testItShowsCurrencyBalance() throws InterruptedException {
        BalanceCurrencyFragment fragment = (BalanceCurrencyFragment) activityRule.getActivity().getSupportFragmentManager().getFragments().get(1);
        activityRule.getActivity().runOnUiThread(() -> fragment.onTotalBalance("£12,000"));
        onView(withId(R.id.next_currency_button)).perform(click());
        onView(withText("£12,000")).check(matches(isDisplayed()));
    }

}