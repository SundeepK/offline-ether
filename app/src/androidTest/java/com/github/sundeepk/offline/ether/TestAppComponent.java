package com.github.sundeepk.offline.ether;

import android.app.Application;

import com.github.sundeepk.offline.ether.activities.AccountActivityTest;
import com.github.sundeepk.offline.ether.activities.MainActivityTest;
import com.github.sundeepk.offline.ether.activities.OfflineTransactionActivityTest;
import com.github.sundeepk.offline.ether.activities.SendTransactionActivityTest;
import com.github.sundeepk.offline.ether.di.AppModule;
import com.github.sundeepk.offline.ether.di.BoxStoreModule;
import com.github.sundeepk.offline.ether.di.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ContextModule.class,
        BoxStoreModule.class,
        AppModule.class,
        TestPresentersModule.class,
        TestFragmentBuilder.class,
        TestActivityBuilder.class})
public interface TestAppComponent extends AndroidInjector<DaggerApplication> {

    void inject(TestApp testApp);

    void inject(MainActivityTest mainActivityTest);

    void inject(AccountActivityTest accountActivityTest);

    void inject(OfflineTransactionActivityTest offlineTransactionActivityTest);

    void inject(SendTransactionActivityTest sendTransactionActivityTest);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        TestAppComponent build();
    }
}
