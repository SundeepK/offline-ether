package com.github.sundeepk.offline.ether.di;

import com.github.sundeepk.offline.ether.App;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = TransactionPollerServiceComponent.class)
public class ApplicationModule{

    private final App myApplication;

    public ApplicationModule(App myApplication){
        this.myApplication = myApplication;
    }

    @Provides
    public App providesMyApplication(){
        return myApplication;
    }
}
