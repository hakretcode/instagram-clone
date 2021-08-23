package com.hakretcode.instagram.initial.factory;

import com.hakretcode.instagram.initial.fragment.LoginFragment;
import com.hakretcode.instagram.initial.fragment.RegisterEmailFragment;
import com.hakretcode.instagram.initial.fragment.RegisterNamePassFragment;
import com.hakretcode.instagram.initial.fragment.UsernameFragment;
import com.hakretcode.instagram.initial.fragment.WelcomeFragment;

public class PresenterFactory {
    public static Presenter newPresenter(View view) {
        Presenter presenter = null;
        if (view instanceof LoginFragment) presenter = new Presenter.Login(view);
        else if (view instanceof RegisterEmailFragment) presenter = new Presenter.Email(view);
        else if (view instanceof RegisterNamePassFragment) presenter = new Presenter.NamePass(view);
        else if (view instanceof UsernameFragment) presenter = new Presenter.Username(view);
        else if (view instanceof WelcomeFragment) presenter = new Presenter.Welcome(view);
        return presenter;
    }
}