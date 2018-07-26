package com.demo.account.db.dao.helper;

import com.demo.account.db.dao.UserEntity;
import com.demo.account.db.dao.green.UserEntityDao;

import java.util.Date;
import java.util.List;

public class UserDaoHelper {
    private UserEntityDao userEntityDao;

    public UserDaoHelper() {
        this.userEntityDao = GreenDaoHelper.getInstance().getSession().getUserEntityDao();
    }

    public UserEntity getUserOfName(String name){
        List<UserEntity> list = this.userEntityDao.queryBuilder()
                .where(UserEntityDao.Properties.Name.eq(name))
                .build()
                .list();

        if (list == null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }


    public void addUser(UserEntity entity){
        this.userEntityDao.insert(entity);
    }




}
