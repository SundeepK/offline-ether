package com.example.sundeep.offline_ether.di;

import com.example.sundeep.offline_ether.App;

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
