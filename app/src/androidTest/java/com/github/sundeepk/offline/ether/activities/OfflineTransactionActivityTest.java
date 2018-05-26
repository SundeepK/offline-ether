package com.github.sundeepk.offline.ether.activities;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.TestApp;
import com.github.sundeepk.offline.ether.entities.GasPrice;
import com.github.sundeepk.offline.ether.entities.Nonce;
import com.github.sundeepk.offline.ether.fragments.GasFragment;
import com.github.sundeepk.offline.ether.mvc.presenters.EthGasPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.sundeepk.offline.ether.Constants.PUBLIC_ADDRESS;
import static com.github.sundeepk.offline.ether.RecyclerViewPositionMatcher.atPosition;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OfflineTransactionActivityTest {

    private static final String ADDRESS_1 = "address1";

    @Inject
    EthGasPresenter ethGasPresenter;
    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Rule
    public ActivityTestRule<OfflineTransactionActivity> activityRule =
            new ActivityTestRule<OfflineTransactionActivity>(OfflineTransactionActivity.class) {
                @Override
                protected Intent getActivityIntent() {
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
    public void testItLoadsGasPrices() throws Exception {
        // given
        GasFragment fragment = (GasFragment) activityRule.getActivity().getSupportFragmentManager().getFragments().get(0);

        GasPrice gp1 = new GasPrice("slow", 1, 5, false);
        GasPrice gp2 = new GasPrice("fast", 2, 4, false);
        GasPrice gp3 = new GasPrice("fastest", 3, 2, false);

        List<GasPrice> prices = Arrays.asList(gp1, gp2, gp3);

        // when
        activityRule.getActivity().runOnUiThread(() -> fragment.onEthGasPrice(prices));

        // then
        verify(ethGasPresenter, atLeastOnce()).loadEthGasData(ADDRESS_1);

        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(0, hasDescendant(withText("slow")))));
        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(1, hasDescendant(withText("fast")))));
        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(2, hasDescendant(withText("fastest")))));

        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(0, hasDescendant(withText("5.0mins")))));
        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(1, hasDescendant(withText("4.0mins")))));
        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(2, hasDescendant(withText("2.0mins")))));

        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(0, hasDescendant(withText("1.0Gwei")))));
        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(1, hasDescendant(withText("2.0Gwei")))));
        onView(withId(R.id.gas_prices_recycler_view))
                .check(matches(atPosition(2, hasDescendant(withText("3.0Gwei")))));
    }

    @Test
    public void testYouCanSkipLoadingGasPrices() throws Exception {
        // when
        onView(withText("Skip")).perform(click());

        // then
        onView(withId(R.id.qr_scanner)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowsNextButtonOnceGasPriceSelected() throws Exception {
        // given
        GasFragment fragment = (GasFragment) activityRule.getActivity().getSupportFragmentManager().getFragments().get(0);

        GasPrice gp1 = new GasPrice("slow", 1, 5, false);
        GasPrice gp2 = new GasPrice("fast", 2, 4, false);
        GasPrice gp3 = new GasPrice("fastest", 3, 2, false);

        List<GasPrice> prices = Arrays.asList(gp1, gp2, gp3);

        // when
        activityRule.getActivity().runOnUiThread(() -> fragment.onEthGasPrice(prices));
        activityRule.getActivity().runOnUiThread(() -> fragment.onNonce(new Nonce("0")));
        onView(withText("slow")).perform(click());
        onView(withText("Next")).perform(click());

        // then
        onView(withText("Step 2 Sign transaction/")).check(matches(isDisplayed()));
    }

    @Test
    public void testYouCanGoBackToGasFragment() throws Exception {
        // given
        GasFragment fragment = (GasFragment) activityRule.getActivity().getSupportFragmentManager().getFragments().get(0);

        GasPrice gp1 = new GasPrice("slow", 1, 5, false);
        GasPrice gp2 = new GasPrice("fast", 2, 4, false);
        GasPrice gp3 = new GasPrice("fastest", 3, 2, false);

        List<GasPrice> prices = Arrays.asList(gp1, gp2, gp3);

        // when
        activityRule.getActivity().runOnUiThread(() -> fragment.onEthGasPrice(prices));
        activityRule.getActivity().runOnUiThread(() -> fragment.onNonce(new Nonce("0")));
        onView(withText("slow")).perform(click());
        onView(withText("Next")).perform(click());
        onView(withText("Back")).perform(click());

        // then
        onView(withText("Step 1 gas prices/")).check(matches(isDisplayed()));
    }



}