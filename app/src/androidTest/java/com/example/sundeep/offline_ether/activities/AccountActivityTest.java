package com.example.sundeep.offline_ether.activities;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sundeep.offline_ether.TestApp;
import com.example.sundeep.offline_ether.mvc.presenters.AccountPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AccountActivityTest {

    private static final String ADDRESS_1 = "address1";

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
        // verify load account onCreate
        verify(accountPresenter).loadAddress(ADDRESS_1);
    }


}