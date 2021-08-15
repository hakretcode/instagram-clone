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
    public void onLogin(Activity activity, String email, String pass) {
        view.progressVisibility(true);
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            Runnable runnable;
            if (DataBase.auth(email, pass)) runnable = view::startMain;
            else runnable = () -> {
                view.onFailure("Invalid email or password");
                view.progressVisibility(false);
            };
            activity.runOnUiThread((runnable));
        });
    }

    @Override
    public boolean onCheckEmail(String email) {
        return Pattern.compile("\\w+(\\.\\w+)?@(gmail|hotmail|outlook|hakretcode).com")
                .matcher(email).matches();
    }
}
