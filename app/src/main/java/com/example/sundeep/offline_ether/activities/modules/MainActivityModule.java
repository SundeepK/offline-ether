package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.activities.MainActivity;
import com.example.sundeep.offline_ether.di.PerActivity;
import com.example.sundeep.offline_ether.mvc.views.MainView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainActivityModule {

    @Binds
    abstract MainView provideMainView(MainActivity mainActivity);

    @PerActivity
    @Provides
    static android.support.v4.app.FragmentManager providesFragmentManager(MainActivity mainActivity) {
        return mainActivity.getSupportFragmentManager();
    }

}
