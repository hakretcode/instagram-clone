package com.igorlb.instagram.initial.login.model;

import com.igorlb.instagram.initial.login.InputType;

public interface ModelContract {

    interface Model {
        void login(String user, String pass);

    }

    interface Presenter {
        void onError(InputType inputType, String message);

        void onProgress(boolean visibility);

        void onSuccess();
    }
}
