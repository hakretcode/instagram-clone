package com.igorlb.instagram.database;

import com.igorlb.instagram.initial.login.InputType;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class DataBase {

    private static final List<UserAuth> userAuths = new ArrayList<>(asList(new UserAuth("user1@gmail.com", "123456"),
            new UserAuth("user2@gmail.com", "123427"),
            new UserAuth("user3@gmail.com", "123478"),
            new UserAuth("user4@gmail.com", "123499"),
            new UserAuth("user5@gmail.com", "123414"),
            new UserAuth("igor@hakretcode.com", "english")));
    private static final List<User> users = new ArrayList<>();

    public static boolean[] auth(String user, String pass) {
        boolean[] valid = new boolean[2];
        for (UserAuth userObject : userAuths) {
            if (userObject.equals(InputType.USER, user)) {
                valid[0] = true;
                if (userObject.equals(InputType.PASSWORD, pass)) valid[1] = true;
            }
        }
        return valid;
    }

    public static boolean isEmailAvailableForRegister(String user) {
        for (UserAuth userObject : userAuths) {
            if (userObject.equals(InputType.USER, user)) {
                return false;
            }
        }
        return true;
    }

    public static void add(String email, String name, String pass) {
        userAuths.add(new UserAuth(email, pass));
        users.add(new User(name, email));
    }

}
