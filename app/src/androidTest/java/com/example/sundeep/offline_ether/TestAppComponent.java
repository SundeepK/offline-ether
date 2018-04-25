package com.example.sundeep.offline_ether;

import android.app.Application;

import com.example.sundeep.offline_ether.activities.AccountActivityTest;
import com.example.sundeep.offline_ether.activities.MainActivityTest;
import com.example.sundeep.offline_ether.di.AppModule;
import com.example.sundeep.offline_ether.di.BoxStoreModule;
import com.example.sundeep.offline_ether.di.ContextModule;

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

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        TestAppComponent build();
    }
}
