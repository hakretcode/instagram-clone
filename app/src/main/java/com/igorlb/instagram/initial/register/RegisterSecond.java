package com.igorlb.instagram.initial.register;

import android.os.Bundle;
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
import com.igorlb.instagram.R;
import com.igorlb.instagram.initial.Initial;

public class RegisterSecond extends Fragment implements Contract.Register2, TextWatcher {
    private final Contract.Presenter2 presenter;
    private MaterialButton button;
    private TextInputEditText inputName;
    private TextInputEditText inputPass;

    public RegisterSecond() {
        presenter = Presenter.getInstanceSecond(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_register_second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        inputName.addTextChangedListener(this);
        inputPass.addTextChangedListener(this);
        button.setOnClickListener(v -> presenter.next(inputName.getText().toString(), inputPass.getText().toString()));
    }

    private void findViews(View view) {
        inputName = view.findViewById(R.id.input_name);
        inputPass = view.findViewById(R.id.input_pass);
        button = view.findViewById(R.id.button);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        button.setEnabled(inputName.length() > 0 && inputPass.length() > 5);
    }

    public void afterTextChanged(Editable a) {
    }

    public void beforeTextChanged(CharSequence a, int b, int c, int d) {
    }

    @Override
    public void finish() {
        ((Initial) getActivity()).changeFragment(new Welcome());
    }
}
