package com.demo.account.db.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user chart
 */
@Entity
public class UserEntity {

    @Id
    private Long id;
    private String name;    // 用户名
    private String psw;     // 密码
    @Generated(hash = 740424473)
    public UserEntity(Long id, String name, String psw) {
        this.id = id;
        this.name = name;
        this.psw = psw;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPsw() {
        return this.psw;
    }
    public void setPsw(String psw) {
        this.psw = psw;
    }
   

}
