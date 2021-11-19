package com.prabesh.phonote.Modal;

public class Users {
    String fullName, email, password, userId;

    public Users(String fullName, String email, String password, String userId) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public Users(){}

    // SignUp Constructor
    public Users(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
