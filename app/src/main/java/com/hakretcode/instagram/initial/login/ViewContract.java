package com.hakretcode.instagram.initial.login;

import android.app.Activity;
import android.content.SharedPreferences;

public interface ViewContract {

    interface View {

        void failure(String errorName);

        void progressVisibility(boolean visibility);

        void startMain();

        void runOnUiThread(Runnable runnable);
    }

    interface Presenter {
        void onLogin(Activity activity, String user, String pass);

        boolean onCheckEmail(String text);
    }

}
