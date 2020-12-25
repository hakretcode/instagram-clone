package com.igorlb.instagram.initial.login.view;

import com.igorlb.instagram.initial.login.InputType;

public interface ViewContract {

    interface View {

        void onFailure(InputType type, String errorName);

        void progressVisibility(boolean visibility);

        void startMain();
    }

    interface Presenter {
        void onLogin(String user, String pass);

    }

}
