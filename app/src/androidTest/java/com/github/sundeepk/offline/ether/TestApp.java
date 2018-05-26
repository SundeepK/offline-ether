package com.github.sundeepk.offline.ether;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class TestApp extends DaggerApplication {

    public TestAppComponent getAppComponent() {
        return appComponent;
    }

    TestAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerTestAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }



}
