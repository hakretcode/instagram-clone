package com.igorlb.instagram.start.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.igorlb.instagram.R;

public class RegisterFirst extends Fragment implements TextWatcher {
    private TextInputEditText inputEmail;
    private MaterialButton button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.initial_register_first_fragment, container, false);
        findViews(view);
        inputEmail.addTextChangedListener(this);
        return view;
    }

    private void findViews(View view) {
        inputEmail = view.findViewById(R.id.input_email);
        button = view.findViewById(R.id.button);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        button.setEnabled(inputEmail.length() > 0);
    }

    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }

    public void afterTextChanged(Editable a) {
    }
}
