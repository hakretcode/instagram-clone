package com.hakretcode.instagram.initial.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.commons.ProgressButton;
import com.hakretcode.instagram.initial.TextButtonColor;
import com.hakretcode.instagram.main.MainActivity;

public class WelcomeFragment extends Fragment implements Contract.WelcomeView {
    private final Contract.WelcomePresenter presenter;
    private ProgressButton button;
    private MaterialButton usernameButton;
    private MaterialTextView welcomeTitle;
    private MaterialTextView tvError;

    public WelcomeFragment() {
        presenter = Presenter.getInstanceWelcomeView(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.initial_welcome_fragment, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        button.setEnabled(true);
        MaterialButton usernameButton = this.usernameButton;
        usernameButton.setOnTouchListener(TextButtonColor::colorPress);
        usernameButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        button.setOnClickListener(v -> presenter.onFinish());
        welcomeTitle.setText(getString(R.string.welcome, presenter.getUsername()));
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
        MaterialTextView tvError = this.tvError;
        tvError.setText(error);
        tvError.setVisibility(View.VISIBLE);
        tvError.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_down));
    }

    private void findViews(View view) {
        usernameButton = view.findViewById(R.id.changeUserButton);
        welcomeTitle = view.findViewById(R.id.welcome_title);
        button = view.findViewById(R.id.button);
        tvError = view.findViewById(R.id.tv_error);
    }

    @Override
    public void commit() {
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }
}
