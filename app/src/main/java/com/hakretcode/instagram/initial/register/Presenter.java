package com.hakretcode.instagram.initial.register;

import android.os.Handler;
import android.os.Looper;

import com.hakretcode.instagram.database.DataBase;

import java.util.regex.Pattern;

public class Presenter implements Contract.Presenter, Contract.Presenter2, Contract.WelcomePresenter {
    private static Presenter instance;
    private Contract.Register register;
    private Contract.Register2 register2;
    private String email;
    private String name;
    private String pass;
    private Contract.Welcome welcome;
    private boolean finish;
    private boolean pressed;

    private Presenter() {
    }

    public static Contract.Presenter getInstanceFirst(Contract.Register register) {
        if (instance == null) instance = new Presenter();
        instance.register = register;
        return instance;
    }

    public static Contract.Presenter2 getInstanceSecond(Contract.Register2 register) {
        instance.register2 = register;
        return instance;
    }

    public static Contract.WelcomePresenter getInstanceWelcome(Contract.Welcome welcome) {
        instance.welcome = welcome;
        return instance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void load() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            DataBase.add(email, name, pass);
            if (pressed) welcome.commit();
            else finish = true;
        }, 5000);
    }

    @Override
    public void checkButton() {
        if (!finish) {
            welcome.setProgressVisibility(true);
            pressed = true;
        } else welcome.commit();
    }

    @Override
    public boolean checkEmail(CharSequence email) {
        return Pattern.compile("\\w+(\\.\\w+)?@(gmail|hotmail|outlook).com")
                .matcher(email).matches();
    }

    @Override
    public void validEmail(String email) {
        register.progressVisibility(true);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (DataBase.isEmailAvailableForRegister(email)) {
                this.email = email;
                register.next();
            } else register.failure("This email is already being used");
            register.progressVisibility(false);
        }, 1000);
    }

    @Override
    public void next(String name, String pass) {
        this.name = name;
        this.pass = pass;
        register2.completeRegistration();
    }
}
