package com.igorlb.instagram.database;

import com.igorlb.instagram.initial.login.InputType;

import java.util.Objects;

public class UserAuth {
    private final String user;
    private final String password;

    public UserAuth(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public boolean equals(InputType inputType, String text) {
        if (InputType.USER == inputType) return Objects.equals(user, text);
        else return Objects.equals(password, text);
    }
}
