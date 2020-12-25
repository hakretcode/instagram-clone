package com.igorlb.instagram.initial.login;

import com.igorlb.instagram.initial.login.model.LocalDataSource;
import com.igorlb.instagram.initial.login.model.ModelContract;
import com.igorlb.instagram.initial.login.view.ViewContract;

public class LoginPresenter implements ViewContract.Presenter, ModelContract.Presenter {
    private final ViewContract.View view;
    private final ModelContract.Model dataSource;

    public LoginPresenter(ViewContract.View view) {
        this.view = view;
        this.dataSource = new LocalDataSource(this);
    }

    @Override
    public void onLogin(String user, String pass) {
        dataSource.login(user, pass);
    }

    @Override
    public void onSuccess() {
        view.startMain();
    }

    @Override
    public void onError(InputType inputType, String message) {
        view.onFailure(inputType, message);
        view.progressVisibility(false);
    }

    @Override
    public void onProgress(boolean visibility) {
        view.progressVisibility(visibility);
    }
}
