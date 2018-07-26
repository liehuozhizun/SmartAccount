package com.demo.account.event;

public class CountComeTypeEvent {

    private int type; // 收入或支出

    public CountComeTypeEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
