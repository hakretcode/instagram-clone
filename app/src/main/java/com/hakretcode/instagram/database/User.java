package com.hakretcode.instagram.database;

public class User {
    private final String username;
    private final String name;
    private final String email;

    public User(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
