package com.demo.account.db.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.demo.account.db.dao.green.DaoMaster;
import com.demo.account.db.dao.green.DaoSession;


/**
 * Created by bill on 2017/11/2.
 * GreenDAO initialize
 */

public class GreenDaoHelper {

    private static final String DB_NAME = "crrepa-db";

    private DaoSession session;

    private GreenDaoHelper() {
    }

    private static class GreenDaoHelperHolder{
        private static final GreenDaoHelper INSTANCE = new GreenDaoHelper();
    }

    public static GreenDaoHelper getInstance(){
        return GreenDaoHelperHolder.INSTANCE;
    }

    public void setup(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        this.session = new DaoMaster(sqLiteDatabase).newSession();
    }


    public DaoSession getSession() {
        return this.session;
    }
}
