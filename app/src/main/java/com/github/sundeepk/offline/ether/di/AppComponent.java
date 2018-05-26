package com.github.sundeepk.offline.ether.di;

import android.app.Application;

import com.github.sundeepk.offline.ether.App;

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
        FragmentBuilder.class,
        ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(App app);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    TransactionPollerServiceComponent.Builder transactionPollerServiceBuilder();

}
