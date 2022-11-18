package com.example.quanlisach.User;

public class User {
    private String Avatar;
    private String account;
    private long id;

    public User() {
    }

    public User(String avatar, String account, long id) {
        Avatar = avatar;
        this.account = account;
        this.id = id;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
