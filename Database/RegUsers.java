package com.example.publishinghouseluminecence.Database;

public class RegUsers {
    private String email, password, repPassword, nickName;

    public RegUsers(){}

    public RegUsers(String email, String password, String repPassword, String nickName) {
        this.email = email;
        this.password = password;
        this.repPassword = repPassword;
        this.nickName = nickName;
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
}

