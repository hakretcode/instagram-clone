package com.hakretcode.instagram.initial.fragment;

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
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.commons.ProgressButton;
import com.hakretcode.instagram.initial.InitialActivity;
import com.hakretcode.instagram.initial.TextButtonColor;
import com.hakretcode.instagram.initial.factory.Presenter;
import com.hakretcode.instagram.initial.factory.PresenterFactory;
import com.hakretcode.instagram.main.MainActivity;

public class LoginFragment extends Fragment implements com.hakretcode.instagram.initial.factory.View, TextWatcher {
    private final Presenter presenter;
    private ProgressButton buttonLogin;
    private TextInputLayout userToggle;
    private TextInputEditText inputUser;
    private TextInputEditText inputPass;
    private MaterialButton textButton;
    private MaterialTextView registerButton;

    public LoginFragment() {
        this.presenter = PresenterFactory.newPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        findViews(view);
        buttonLogin.setEnabled(false);
        setClick();
    }

    private void setClick() {
        inputUser.addTextChangedListener(this);
        inputPass.addTextChangedListener(this);
        textButton.setOnTouchListener(TextButtonColor::colorPress);
        buttonLogin.setOnClickListener(v -> presenter.start(inputUser.getText().toString().toLowerCase(),
                inputPass.getText().toString()));
        registerButton.setOnClickListener(v -> ((InitialActivity) getActivity()).changeFragment(new RegisterEmailFragment()));
    }

    private void findViews(View view) {
        userToggle = view.findViewById(R.id.input_login_toogle);
        inputUser = view.findViewById(R.id.input_login);
        inputPass = view.findViewById(R.id.input_password);
        buttonLogin = view.findViewById(R.id.button_login);
        textButton = view.findViewById(R.id.button_facebook);
        registerButton = view.findViewById(R.id.register);
    }

    @Override
    public void setFailure(String errorName) {
        setProgressVisibility(false, false);
        inputPass.setText(null);
        inputUser.setText(null);
        userToggle.setError(errorName);
        inputUser.setBackgroundResource(R.drawable.bg_input_error);
        inputPass.setBackgroundResource(R.drawable.bg_input_error);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            userToggle.setError(null);
            inputUser.setBackgroundResource(R.drawable.bg_input);
            inputPass.setBackgroundResource(R.drawable.bg_input);
        }, 5000);
    }

    @Override
    public void setProgressVisibility(boolean buttonEnable, boolean progress) {
        buttonLogin.setEnabled(buttonEnable);
        buttonLogin.setProgressEnabled(progress);
    }

    @Override
    public void next() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        buttonLogin.setEnabled(inputUser.length() > 0 && inputPass.length() > 5 &&
                presenter.checkEmail(inputUser.getText().toString()));
    }

    @Override
    public void afterTextChanged(Editable a) {
    }

    @Override
    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }

}
