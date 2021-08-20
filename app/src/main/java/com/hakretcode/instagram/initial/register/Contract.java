package com.hakretcode.instagram.initial.register;

public interface Contract {
    interface ViewBase {
        void runOnUiThread(Runnable runnable);

        void setProgressVisibility(boolean enable);

        void failure(String error);
    }

    interface EmailView extends ViewBase {
        void next();
    }

    interface EmailPresenter {
        boolean checkEmail(CharSequence email);

        void validEmail(String email);
    }

    interface NamePassView {
        void completeRegistration();

    }

    interface NamePassPresenter {
        void onNext(String name, String pass);
    }

    interface UsernameView extends ViewBase {

        void nextFragment();
    }

    interface UsernamePresenter {
        void onNext(String username);
    }

    interface WelcomeView extends ViewBase {
        void commit();
    }

    interface WelcomePresenter {
        String getUsername();

        void onFinish();
    }
}
