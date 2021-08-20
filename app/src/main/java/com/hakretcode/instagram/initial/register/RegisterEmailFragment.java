package com.hakretcode.instagram.initial.register;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.commons.ProgressButton;
import com.hakretcode.instagram.initial.InitialActivity;

public class RegisterEmailFragment extends Fragment implements Contract.EmailView, TextWatcher {
    private final Contract.EmailPresenter presenter;
    private TextInputLayout emailToggle;
    private TextInputEditText inputEmail;
    private ProgressButton nextButton;
    private FrameLayout loginButton;

    public RegisterEmailFragment() {
        presenter = Presenter.getInstanceEmailView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_register_email_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setClicks();
    }

    private void setClicks() {
        inputEmail.addTextChangedListener(this);
        loginButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        nextButton.setOnClickListener(v -> presenter.validEmail(inputEmail.getText().toString().toLowerCase()));
    }

    private void findViews(View view) {
        emailToggle = view.findViewById(R.id.email_toggle);
        inputEmail = view.findViewById(R.id.input_email);
        nextButton = view.findViewById(R.id.progress_button);
        loginButton = view.findViewById(R.id.login_btn);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        nextButton.setEnabled(inputEmail.length() > 0 && presenter.checkEmail(s));
    }

    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }

    public void afterTextChanged(Editable a) {
    }

    @Override
    public void failure(String error) {
        inputEmail.setText(null);
        emailToggle.setError(error);
        inputEmail.setBackgroundResource(R.drawable.bg_input_error);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            emailToggle.setError(null);
            inputEmail.setBackgroundResource(R.drawable.bg_input);
        }, 5000);
    }

    @Override
    public void setProgressVisibility(boolean enable) {
        nextButton.setProgressEnabled(enable);
    }

    @Override
    public void next() {
        ((InitialActivity) getActivity()).changeFragment(new RegisterNamePassFragment());
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }
}