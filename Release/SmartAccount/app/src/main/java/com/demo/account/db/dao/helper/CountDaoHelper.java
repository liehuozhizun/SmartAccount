package com.demo.account.db.dao.helper;

import android.text.TextUtils;

import com.demo.account.db.dao.CountEntity;
import com.demo.account.db.dao.green.CountEntityDao;
import com.demo.account.storage.BaseParamNames;
import com.demo.account.storage.SharedPreferencesHelper;
import com.demo.account.util.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CountDaoHelper {
    private CountEntityDao countEntityDao;

    public CountDaoHelper() {
        this.countEntityDao = GreenDaoHelper.getInstance().getSession().getCountEntityDao();
    }

    public void insertCount(CountEntity entity){
        this.countEntityDao.insert(entity);
    }

    public List<CountEntity> getAllMonthCount(Date date){
        String name = SharedPreferencesHelper.getInstance()
                .getString(BaseParamNames.USER_NAME, "");
        if (TextUtils.isEmpty(name)){
            return null;
        }
        Date startTime = DateFormat.getSameMonthStartTime(date);
        Date endTime = DateFormat.getSameMonthEndTime(date);
        List<CountEntity> list = this.countEntityDao.queryBuilder()
                .where(CountEntityDao.Properties.Date.ge(startTime),
                        CountEntityDao.Properties.Date.le(endTime),
                        CountEntityDao.Properties.UserName.eq(name))
                .orderDesc(CountEntityDao.Properties.Date)
                .build()
                .list();
        if (list == null || list.isEmpty()){
            return null;
        }

        return list;
    }

    public List<CountEntity> getAllMonthCountOfType(Date date, int type){
        List<CountEntity> list = new ArrayList<>();
        List<CountEntity> allMonthCount = getAllMonthCount(date);
        if (allMonthCount == null){
            return list;
        }
        for (CountEntity entity : allMonthCount) {
            if (entity.getComeType() == type){
                list.add(entity);
            }
        }
        return list;
    }

}
