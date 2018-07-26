package com.demo.account.event;

public class UserLoginChangeEvent {
    private boolean login;

    public UserLoginChangeEvent(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
