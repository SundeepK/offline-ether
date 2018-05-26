package com.github.sundeepk.offline.ether.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ContextModule {

    @Binds
    abstract Context provideContext(Application application);

}
