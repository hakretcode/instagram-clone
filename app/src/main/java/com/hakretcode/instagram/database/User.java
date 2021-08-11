package com.hakretcode.instagram.database;

public class User {
    private final String name;
    private final String email;
    private final String uid;

    public User(String uid, String name, String email) {

        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
