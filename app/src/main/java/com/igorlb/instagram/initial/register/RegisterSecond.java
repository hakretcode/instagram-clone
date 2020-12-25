package com.igorlb.instagram.initial.register;

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

import com.google.android.material.textfield.TextInputEditText;
import com.igorlb.instagram.R;
import com.igorlb.instagram.util.ProgressButton;

public class RegisterSecond extends Fragment implements TextWatcher, View.OnClickListener {
    private ProgressButton button;
    private TextInputEditText inputName;
    private TextInputEditText inputPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.initial_register_second_fragment, container, false);
        findViews(view);
        inputName.addTextChangedListener(this);
        inputPassword.addTextChangedListener(this);
        button.setOnClickListener(this);
        return view;
    }

    private void findViews(View view) {
        inputName = view.findViewById(R.id.input_name);
        inputPassword = view.findViewById(R.id.input_password);
        button = view.findViewById(R.id.button);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        button.setEnabled(inputName.length() > 0 && inputPassword.length() > 0);
    }

    @Override
    public void onClick(View v) {
        final Handler handler = new Handler(Looper.getMainLooper());
        button.setProgressVisibility(true);
        handler.postDelayed(() -> button.setProgressVisibility(false), 2000);
    }

    public void afterTextChanged(Editable a) {
    }

    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }
}
