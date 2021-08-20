package com.hakretcode.instagram.initial.register;

import android.os.AsyncTask;

import com.hakretcode.instagram.database.DataBase;

import java.net.ConnectException;
import java.util.regex.Pattern;

public class Presenter implements Contract.EmailPresenter, Contract.NamePassPresenter, Contract.UsernamePresenter, Contract.WelcomePresenter {
    private static Presenter instance;
    private Contract.EmailView registerEmailView;
    private Contract.NamePassView namePassRegisterView;
    private Contract.UsernameView usernameView;
    private Contract.WelcomeView welcomeView;
    private String email;
    private String name;
    private String pass;
    private String username;

    private Presenter() {

    }

    public static Contract.EmailPresenter getInstanceEmailView(Contract.EmailView register) {
        if (instance == null) instance = new Presenter();
        instance.registerEmailView = register;
        return instance;
    }

    public static Contract.NamePassPresenter getInstanceNamePassView(Contract.NamePassView register) {
        instance.namePassRegisterView = register;
        return instance;
    }

    public static Contract.UsernamePresenter getInstanceUsernameView(Contract.UsernameView username) {
        instance.usernameView = username;
        return instance;
    }

    public static Contract.WelcomePresenter getInstanceWelcomeView(Contract.WelcomeView welcome) {
        instance.welcomeView = welcome;
        return instance;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void onFinish() {
        welcomeView.setProgressVisibility(true);
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            try {
                DataBase.add(email, username, name, pass);
                DataBase.auth(email, pass);
                welcomeView.commit();
            } catch (ConnectException e) {
                welcomeView.failure(e.getMessage());
            }
        });
    }

    @Override
    public boolean checkEmail(CharSequence email) {
        return Pattern.compile("\\w+(\\.\\w+)?@(gmail|hotmail|outlook).com")
                .matcher(email).matches();
    }

    @Override
    public void validEmail(String email) {
        registerEmailView.setProgressVisibility(true);
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            String error = null;
            try {
                if (!DataBase.isEmailAvailableForRegister(email)) error = "This email is already being used";
            } catch (ConnectException e) {
                error = e.getMessage();
            }
            final String finalError = error;
            registerEmailView.runOnUiThread(() -> {
                registerEmailView.setProgressVisibility(false);
                if (finalError == null) {
                    this.email = email;
                    registerEmailView.next();
                } else registerEmailView.failure(finalError);
            });
        });
    }

    @Override
    public void onNext(String name, String pass) {
        this.name = name;
        this.pass = pass;
        namePassRegisterView.completeRegistration();
    }

    @Override
    public void onNext(String username) {
        usernameView.setProgressVisibility(true);
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            String error = null;
            try {
                if (!DataBase.isUserAvailable(username)) error = "This username isn't available. Please try another";
            } catch (Exception e) {
                error = e.getMessage();
            }
            String finalError = error;
            usernameView.runOnUiThread(() -> {
                if (finalError == null) {
                    this.username = username;
                    usernameView.nextFragment();
                } else usernameView.failure(finalError);
                usernameView.setProgressVisibility(false);
            });
        });
    }
}
