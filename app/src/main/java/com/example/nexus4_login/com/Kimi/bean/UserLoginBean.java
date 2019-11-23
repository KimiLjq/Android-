package com.example.nexus4_login.com.Kimi.bean;

public class UserLoginBean {
    private String userName;
    private String password;
    private boolean isRemember;
    private boolean isAutoLogin;

    public UserLoginBean(){}

    public UserLoginBean(String userName, String password, boolean isRemember, boolean isAutoLogin) {
        this.userName = userName;
        this.password = password;
        this.isRemember = isRemember;
        this.isAutoLogin = isAutoLogin;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        isAutoLogin = autoLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
