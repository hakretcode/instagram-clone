package com.hakretcode.instagram.initial.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.hakretcode.instagram.database.DataBase;

import java.util.regex.Pattern;

public class LoginPresenter implements ViewContract.Presenter {
    private final ViewContract.View view;

    public LoginPresenter(ViewContract.View view) {
        this.view = view;
    }

    @Override
    public void onLogin(Activity activity, String user, String pass) {
        view.progressVisibility(true);
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            Runnable runnable;
            if (DataBase.auth(user, pass)) runnable = view::startMain;
            else runnable = () -> onError("Invalid user or password");
            activity.runOnUiThread(runnable);
        });
    }

    private void onError(String message) {
        view.onFailure(message);
        view.progressVisibility(false);
    }

    @Override
    public boolean onCheckEmail(String email) {
        return Pattern.compile("\\w+(\\.\\w+)?@(gmail|hotmail|outlook|hakretcode).com")
                .matcher(email).matches();
    }

    @Override
    public void init(SharedPreferences prefs) {
    }
}
