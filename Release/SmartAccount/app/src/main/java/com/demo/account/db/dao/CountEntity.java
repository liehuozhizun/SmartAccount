package com.demo.account.db.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * record
 */
@Entity
public class CountEntity {
    @Id
    private Long id;

    private String userName;
    private Integer type;
    private Integer money;
    private Integer comeType;
    private String note;
    private Date date;
    @Generated(hash = 550987889)
    public CountEntity(Long id, String userName, Integer type, Integer money,
            Integer comeType, String note, Date date) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.money = money;
        this.comeType = comeType;
        this.note = note;
        this.date = date;
    }
    @Generated(hash = 1647433416)
    public CountEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getType() {
        return this.type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getMoney() {
        return this.money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
    public Integer getComeType() {
        return this.comeType;
    }
    public void setComeType(Integer comeType) {
        this.comeType = comeType;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
   


}
