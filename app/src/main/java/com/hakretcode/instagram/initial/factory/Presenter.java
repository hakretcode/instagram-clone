package com.hakretcode.instagram.initial.factory;

import com.hakretcode.instagram.database.UserCrud;
import com.hakretcode.instagram.database.UserDao;

import java.net.ConnectException;
import java.util.regex.Pattern;

public abstract class Presenter {
    private static String email;
    private static String name;
    private static String pass;
    private static String username;
    final UserDao model = new UserCrud(this);
    View view;

    public Presenter(View view) {
        this.view = view;
    }

    public boolean checkEmail(CharSequence email) {
        return Pattern.compile("\\w+(\\.\\w+)?@(gmail|hotmail|outlook|hakretcode).com")
                .matcher(email).matches();
    }

    public String getUsername() {
        return username;
    }

    public abstract void start(String... args);

    public void onComplete() {
        view.setProgressVisibility(true, false);
    }

    public void onFinish(boolean result) {
        if (result) view.next();
        else onFailure(new ConnectException("Unknown error"));
    }

    public void onFailure(Exception e) {
        if (e.getMessage().contains("network error")) view.setFailure("Connection error");
        else view.setFailure(e.getMessage());
    }

    public static class Login extends Presenter {

        public Login(View view) {
            super(view);
        }

        @Override
        public void onFailure(Exception e) {
            super.onFailure(e);
            String error = e.getMessage();
            if (error.contains("There is no user")) view.setFailure("Invalid email");
            else if (error.contains("The password is invalid")) view.setFailure("Invalid password");
        }

        @Override
        public void start(String... args) {
            view.setProgressVisibility(false, true);
            model.auth(args[0], args[1]);
        }
    }

    public static class Email extends Presenter {

        public Email(View view) {
            super(view);
        }

        @Override
        public void start(String... args) {
            view.setProgressVisibility(false, true);
            String email = args[0];
            Presenter.email = email;
            model.isEmailAvailableForRegister(email);
        }

        @Override
        public void onFinish(boolean result) {
            super.onFinish(result);
            if (!result) view.setFailure("This email already is in use");
        }
    }

    public static class NamePass extends Presenter {
        public NamePass(View view) {
            super(view);
        }

        @Override
        public void start(String... args) {
            Presenter.name = args[0];
            Presenter.pass = args[1];
            onFinish(true);
        }
    }

    public static class Username extends Presenter {
        public Username(View view) {
            super(view);
        }

        @Override
        public void start(String... args) {
            view.setProgressVisibility(false, true);
            final String username = args[0];
            Presenter.username = username;
            model.isUserAvailable(username);
        }
    }

    public static class Welcome extends Presenter {

        public Welcome(View view) {
            super(view);
        }

        @Override
        public void start(String... args) {
            view.setProgressVisibility(false, true);
            model.add(email, username, name, pass);
        }
    }
}
