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
import com.hakretcode.instagram.initial.Initial;

public class RegisterEmail extends Fragment implements Contract.EmailRegister, TextWatcher {
    private final Contract.EmailPresenter presenter;
    private TextInputLayout emailToggle;
    private TextInputEditText inputEmail;
    private ProgressButton nextButton;
    private FrameLayout login_button;

    public RegisterEmail() {
        presenter = Presenter.getInstanceFirst(this);
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
        login_button.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        nextButton.setOnClickListener(v -> presenter.validEmail(inputEmail.getText().toString().toLowerCase()));
    }

    private void findViews(View view) {
        emailToggle = view.findViewById(R.id.email_toggle);
        inputEmail = view.findViewById(R.id.input_email);
        nextButton = view.findViewById(R.id.progress_button);
        login_button = view.findViewById(R.id.login_btn);
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
    public void progressVisibility(boolean visibility) {
        nextButton.setEnabled(!visibility, visibility);
    }

    @Override
    public void next() {
        ((Initial) getActivity()).changeFragment(new RegisterNamePass());
    }
}
