package com.demo.account;

import android.app.Application;
import android.content.Context;

import com.demo.account.db.dao.helper.GreenDaoHelper;
import com.demo.account.logger.LoggerInitiator;

/**
 * Created by bill on 2018/7/19.
 */

public class App extends Application {
    private static Context context;

    public static Context getContext() {
        return context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = this;
        new LoggerInitiator().setup();
        GreenDaoHelper.getInstance().setup(this);
    }
}
