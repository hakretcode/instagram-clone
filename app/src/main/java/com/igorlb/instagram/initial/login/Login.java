package com.igorlb.instagram.initial.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;
import com.igorlb.instagram.initial.Initial;
import com.igorlb.instagram.initial.TextButtonColor;
import com.igorlb.instagram.initial.register.RegisterFirst;
import com.igorlb.instagram.main.Main;
import com.igorlb.instagram.util.ProgressButton;

public class Login extends Fragment implements ViewContract.View, TextWatcher {
    private final ViewContract.Presenter presenter;
    private ProgressButton buttonLogin;
    private TextInputLayout userToogle;
    private TextInputEditText inputUser;
    private TextInputLayout passToogle;
    private TextInputEditText inputPass;
    private MaterialButton textButton;
    private MaterialTextView registerButton;

    public Login() {
        this.presenter = new LoginPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        findViews(view);
        setClick();
    }

    private void setClick() {
        inputUser.addTextChangedListener(this);
        inputPass.addTextChangedListener(this);
        textButton.setOnTouchListener(TextButtonColor::colorPress);
        buttonLogin.setOnClickListener(v -> presenter.onLogin(inputUser.getText().toString(),
                inputPass.getText().toString()));
        registerButton.setOnClickListener(v -> ((Initial) getActivity()).changeFragment(new RegisterFirst()));
    }

    private void findViews(View view) {
        userToogle = view.findViewById(R.id.input_login_toogle);
        inputUser = view.findViewById(R.id.input_login);
        passToogle = view.findViewById(R.id.input_password_toogle);
        inputPass = view.findViewById(R.id.input_password);
        buttonLogin = view.findViewById(R.id.button_login);
        textButton = view.findViewById(R.id.button_facebook);
        registerButton = view.findViewById(R.id.register);
    }

    @Override
    public void onFailure(InputType type, String errorName) {
        inputPass.setText(null);

        if (type == InputType.USER) {
            inputUser.setText(null);
            userToogle.setError(errorName);
            inputUser.setBackgroundResource(R.drawable.bg_input_error);
        } else {
            passToogle.setError(errorName);
            inputPass.setBackgroundResource(R.drawable.bg_input_error);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            userToogle.setError(null);
            inputUser.setBackgroundResource(R.drawable.bg_input);
            passToogle.setError(null);
            inputPass.setBackgroundResource(R.drawable.bg_input);
        }, 5000);
    }

    @Override
    public void progressVisibility(boolean visibility) {
        buttonLogin.setProgressVisibility(visibility);
    }

    @Override
    public void startMain() {
        startActivity(new Intent(getContext(), Main.class));
        getActivity().finish();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        buttonLogin.setEnabled(inputUser.length() > 0 && inputPass.length() > 5 &&
                presenter.onCheckEmail(inputUser.getText().toString()));
    }

    @Override
    public void afterTextChanged(Editable a) {
    }

    @Override
    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }

}
