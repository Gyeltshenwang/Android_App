package com.example.validation;

public class userModel {
    String uid;
    String user;
    String email;
    String password;

    public userModel() {
    }

    public userModel(String user, String email, String password,String uid) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
