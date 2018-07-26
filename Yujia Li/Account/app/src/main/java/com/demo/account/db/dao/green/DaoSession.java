package com.demo.account.db.dao.green;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.demo.account.db.dao.CountEntity;
import com.demo.account.db.dao.UserEntity;

import com.demo.account.db.dao.green.CountEntityDao;
import com.demo.account.db.dao.green.UserEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig countEntityDaoConfig;
    private final DaoConfig userEntityDaoConfig;

    private final CountEntityDao countEntityDao;
    private final UserEntityDao userEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        countEntityDaoConfig = daoConfigMap.get(CountEntityDao.class).clone();
        countEntityDaoConfig.initIdentityScope(type);

        userEntityDaoConfig = daoConfigMap.get(UserEntityDao.class).clone();
        userEntityDaoConfig.initIdentityScope(type);

        countEntityDao = new CountEntityDao(countEntityDaoConfig, this);
        userEntityDao = new UserEntityDao(userEntityDaoConfig, this);

        registerDao(CountEntity.class, countEntityDao);
        registerDao(UserEntity.class, userEntityDao);
    }
    
    public void clear() {
        countEntityDaoConfig.clearIdentityScope();
        userEntityDaoConfig.clearIdentityScope();
    }

    public CountEntityDao getCountEntityDao() {
        return countEntityDao;
    }

    public UserEntityDao getUserEntityDao() {
        return userEntityDao;
    }

}