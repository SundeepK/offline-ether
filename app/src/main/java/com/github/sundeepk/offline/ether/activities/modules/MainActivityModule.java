package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.activities.MainActivity;
import com.github.sundeepk.offline.ether.di.PerActivity;
import com.github.sundeepk.offline.ether.mvc.views.MainView;

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
