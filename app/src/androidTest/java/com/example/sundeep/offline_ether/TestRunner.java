package com.example.sundeep.offline_ether;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class TestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl,
                                      String className,
                                      Context context) throws InstantiationException,
            IllegalAccessException,
            ClassNotFoundException {


        return super.newApplication(cl, TestApp.class.getName(), context);
    }
}