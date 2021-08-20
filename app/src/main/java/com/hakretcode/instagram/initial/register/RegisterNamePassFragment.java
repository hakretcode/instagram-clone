package com.hakretcode.instagram.initial.register;

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
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.initial.InitialActivity;

public class RegisterNamePassFragment extends Fragment implements Contract.NamePassView, TextWatcher {
    private final Contract.NamePassPresenter presenter;
    private MaterialButton button;
    private TextInputEditText inputName;
    private TextInputEditText inputPass;

    public RegisterNamePassFragment() {
        presenter = Presenter.getInstanceNamePassView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_register_name_pass_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        button.setEnabled(false);
        button.setOnClickListener(v -> presenter.onNext(inputName.getText().toString(), inputPass.getText().toString()));
        inputName.addTextChangedListener(this);
        inputPass.addTextChangedListener(this);
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
    public void completeRegistration() {
        ((InitialActivity) getActivity()).changeFragment(new UsernameFragment());
    }
}
