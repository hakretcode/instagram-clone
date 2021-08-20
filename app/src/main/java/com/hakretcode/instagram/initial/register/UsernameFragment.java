package com.hakretcode.instagram.initial.register;

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

public class UsernameFragment extends Fragment implements Contract.UsernameView, TextWatcher {
    private TextInputLayout textInputLayout;
    private TextInputEditText inputUsername;
    private ProgressButton button;
    private final Contract.UsernamePresenter presenter = Presenter.getInstanceUsernameView(this);
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
        button.setOnClickListener(v -> presenter.onNext(this.inputUsername.getText().toString()));
    }

    private void findViews(View view) {
        usernameError = view.findViewById(R.id.tv_error);
        textInputLayout = view.findViewById(R.id.username_toggle);
        inputUsername = view.findViewById(R.id.input_username);
        button = view.findViewById(R.id.button);
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }

    @Override
    public void setProgressVisibility(boolean enable) {
        button.setProgressEnabled(enable);
    }

    @Override
    public void failure(String error) {
        MaterialTextView usernameError = this.usernameError;
        usernameError.setText(error);
        usernameError.setVisibility(View.VISIBLE);
        usernameError.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_down));
    }

    @Override
    public void nextFragment() {
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
