package com.hakretcode.instagram.initial.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.commons.ProgressButton;
import com.hakretcode.instagram.initial.InitialActivity;
import com.hakretcode.instagram.initial.factory.Presenter;
import com.hakretcode.instagram.initial.factory.PresenterFactory;

public class UsernameFragment extends Fragment implements com.hakretcode.instagram.initial.factory.View, TextWatcher {
    private final Presenter presenter = PresenterFactory.newPresenter(this);
    private TextInputLayout textInputLayout;
    private TextInputEditText inputUsername;
    private ProgressButton button;
    private MaterialTextView usernameError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_change_username, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        button.setEnabled(false);
        inputUsername.addTextChangedListener(this);
        button.setOnClickListener(v -> presenter.start(this.inputUsername.getText().toString()));
    }

    private void findViews(View view) {
        usernameError = view.findViewById(R.id.tv_error);
        textInputLayout = view.findViewById(R.id.username_toggle);
        inputUsername = view.findViewById(R.id.input_username);
        button = view.findViewById(R.id.button);
    }

    @Override
    public void setProgressVisibility(boolean buttonEnable, boolean progress) {
        button.setEnabled(buttonEnable);
        button.setProgressEnabled(progress);
    }

    @Override
    public void setFailure(String error) {
        MaterialTextView usernameError = this.usernameError;
        usernameError.setText(error);
        usernameError.setVisibility(View.VISIBLE);
        usernameError.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_down));
    }

    @Override
    public void next() {
        ((InitialActivity) getActivity()).changeFragment(new WelcomeFragment());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        var textEditable = inputUsername.getText();
        button.setEnabled(textEditable != null && textEditable.toString().matches("\\w+"));
    }

    public void afterTextChanged(Editable a) {
    }

    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }
}
