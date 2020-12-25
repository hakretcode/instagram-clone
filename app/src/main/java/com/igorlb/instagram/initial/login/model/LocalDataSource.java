package com.igorlb.instagram.initial.login.model;

import android.os.Handler;
import android.os.Looper;

import com.igorlb.instagram.database.DataBase;
import com.igorlb.instagram.initial.login.InputType;

public class LocalDataSource implements ModelContract.Model {

    private final ModelContract.Presenter presenter;

    public LocalDataSource(ModelContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void login(String user, String pass) {
        presenter.onProgress(true);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean[] valids = DataBase.auth(user, pass);
            if (!valids[0]) {
                presenter.onError(InputType.USER, "Invalid user");
                return;
            } else if (!valids[1]) {
                presenter.onError(InputType.PASSWORD, "Invalid password");
                return;
            }
            presenter.onSuccess();
        }, 2000);
    }
}
