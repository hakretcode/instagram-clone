package com.igorlb.instagram.initial.register;

public interface Contract {
    interface Register {
        void failure(String error);

        void progressVisibility(boolean visibility);

        void next();
    }

    interface Presenter {
        boolean checkEmail(CharSequence email);

        void validEmail(String email);
    }

    interface Register2 {
        void finish();

    }

    interface Presenter2 {
        void next(String name, String pass);
    }

    interface Welcome {
        void commit();

        void progress(boolean visibility);
    }

    interface WelcomePresenter {
        String getName();

        void load();

        void checkButton();
    }
}
