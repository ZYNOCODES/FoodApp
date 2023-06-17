package com.example.foodapp.Models;

public class User {
    private String Email;
    private String FullName;
    private String Phone;

    public User(String email, String fullName, String phone) {
        Email = email;
        FullName = fullName;
        Phone = phone;
    }

    public User() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
