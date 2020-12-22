package com.igorlb.instagram.start.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.igorlb.instagram.R;
import com.igorlb.instagram.util.ProgressButton;

public class Login extends Fragment implements TextWatcher, View.OnClickListener {
    private ProgressButton buttonLogin;
    private TextInputLayout loginToogle;
    private TextInputEditText inputLogin;
    private TextInputLayout passwordToogle;
    private TextInputEditText inputPassword;
    private MaterialButton textButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.initial_login_fragment, container, false);
        findViews(view);
        inputLogin.addTextChangedListener(this);
        inputPassword.addTextChangedListener(this);
        buttonLogin.setOnClickListener(this);
        textButton.setOnTouchListener(TextButtonColor::colorPress);
        return view;
    }

    private void findViews(View view) {
        loginToogle = view.findViewById(R.id.input_login_toogle);
        inputLogin = view.findViewById(R.id.input_login);
        passwordToogle = view.findViewById(R.id.input_password_toogle);
        inputPassword = view.findViewById(R.id.input_password);
        buttonLogin = view.findViewById(R.id.button_login);
        textButton = view.findViewById(R.id.button_facebook);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        buttonLogin.setEnabled(inputLogin.length() > 0 && inputPassword.length() > 0);
    }

    @Override
    public void onClick(View v) {
        final Handler handler = new Handler(Looper.getMainLooper());
        buttonLogin.setProgressVisibility(true);
        handler.postDelayed(() -> {
            inputLogin.setText(null);
            inputPassword.setText(null);
            loginToogle.setError("Usuario errado fdp");
            inputLogin.setBackgroundResource(R.drawable.bg_input_error);
            passwordToogle.setError("Se fudeu kappakappa");
            inputPassword.setBackgroundResource(R.drawable.bg_input_error);
            buttonLogin.setProgressVisibility(false);
        }, 2000);
    }

    public void afterTextChanged(Editable a) {
    }

    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }
}
