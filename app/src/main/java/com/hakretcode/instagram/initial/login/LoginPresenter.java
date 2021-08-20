package com.hakretcode.instagram.initial.login;

import android.app.Activity;
import android.os.AsyncTask;

import com.hakretcode.instagram.database.DataBase;

import java.net.ConnectException;
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
            String error = null;
            try {
                if (!DataBase.auth(email, pass)) error = "Invalid email or password";
            } catch (ConnectException e) {
                error = e.getMessage();
            }
                String finalError = error;
                view.runOnUiThread(() -> {
                        if (finalError == null) view.startMain();
                        else {
                            view.progressVisibility(false);
                            view.failure(finalError);
                        }
                });
        });
    }

    @Override
    public boolean onCheckEmail(String email) {
        return Pattern.compile("\\w+(\\.\\w+)?@(gmail|hotmail|outlook|hakretcode).com")
                .matcher(email).matches();
    }
}
