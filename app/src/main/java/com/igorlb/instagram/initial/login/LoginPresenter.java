package com.igorlb.instagram.initial.login;

import android.os.Handler;
import android.os.Looper;

import com.igorlb.instagram.database.DataBase;

import java.util.regex.Pattern;

public class LoginPresenter implements ViewContract.Presenter {
    private final ViewContract.View view;

    public LoginPresenter(ViewContract.View view) {
        this.view = view;
    }

    @Override
    public void onLogin(String user, String pass) {
        view.progressVisibility(true);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean[] valids = DataBase.auth(user, pass);
            if (!valids[0]) {
                onError(InputType.USER, "Invalid user");
                return;
            } else if (!valids[1]) {
                onError(InputType.PASSWORD, "Invalid password");
                return;
            }
            view.startMain();
        }, 2000);
    }

    private void onError(InputType inputType, String message) {
        view.onFailure(inputType, message);
        view.progressVisibility(false);
    }

    @Override
    public boolean onCheckEmail(String email) {
        return Pattern.compile("\\w+(\\.\\w+)?@(gmail|hotmail|outlook|hakretcode).com")
                .matcher(email).matches();
    }
}
