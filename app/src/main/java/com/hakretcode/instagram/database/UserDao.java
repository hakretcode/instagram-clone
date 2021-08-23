package com.hakretcode.instagram.database;

public interface UserDao {
    void auth(String email, String pass);

    void isEmailAvailableForRegister(String user);

    void add(String email, String username, String name, String pass);

    void isUserAvailable(String username);
}
