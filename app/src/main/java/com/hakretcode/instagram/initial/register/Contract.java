package com.hakretcode.instagram.initial.register;

import android.app.Activity;

public interface Contract {
    interface EmailRegister {
        void failure(String error);

        void progressVisibility(boolean progressVisibility);

        void next();

        void runOnUiThread(Runnable runnable);
    }

    interface EmailPresenter {
        boolean checkEmail(CharSequence email);

        void validEmail(String email);
    }

    interface NamePassRegister {
        void completeRegistration();

    }

    interface NamePassPresenter {
        void next(String name, String pass);
    }

    interface Welcome {
        void commit();

        void setProgressVisibility(boolean visibility);
    }

    interface WelcomePresenter {
        String getName();

        void load();

        void checkButton();
    }
}
