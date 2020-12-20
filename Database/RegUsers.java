package com.example.publishinghouseluminecence.Database;

public class RegUsers {
    private String email, password, repPassword, nickName, phoneNum;

    public RegUsers(){}

    public RegUsers(String email, String password, String repPassword, String nickName, String phoneNum) {
        this.email = email;
        this.password = password;
        this.repPassword = repPassword;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPhoneNum(String phone) {
        this.phoneNum = phone;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
}

