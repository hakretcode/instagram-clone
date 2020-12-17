package com.igorlb.instagram.start.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.igorlb.instagram.R;

public class Welcome extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragment = inflater.inflate(R.layout.initial_welcome_fragment, container, false);
        MaterialButton textButton = fragment.findViewById(R.id.textButton);
        textButton.setOnTouchListener(TextButtonColor::setColorPress);
        return fragment;
    }
}
