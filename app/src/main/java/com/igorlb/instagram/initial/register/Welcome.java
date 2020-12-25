package com.igorlb.instagram.initial.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;
import com.igorlb.instagram.initial.TextButtonColor;
import com.igorlb.instagram.main.Main;
import com.igorlb.instagram.util.ProgressButton;

public class Welcome extends Fragment implements Contract.Welcome {
    private final Contract.WelcomePresenter presenter;
    private ProgressButton button;
    private MaterialButton textButton;
    private MaterialTextView welcomeTitle;

    public Welcome() {
        presenter = Presenter.getInstanceWelcome(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.load();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_welcome_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        button.setEnabled(true);
        textButton.setOnTouchListener(TextButtonColor::colorPress);
        button.setOnClickListener(v -> presenter.checkButton());
        welcomeTitle.setText(getString(R.string.welcome, presenter.getName()));
    }

    @Override
    public void progress(boolean visibility) {
        button.setProgressVisibility(true);
    }

    private void findViews(View view) {
        textButton = view.findViewById(R.id.textButton);
        welcomeTitle = view.findViewById(R.id.welcome_title);
        button = view.findViewById(R.id.button);
    }

    @Override
    public void commit() {
        startActivity(new Intent(getContext(), Main.class));
        getActivity().finish();
    }
}
