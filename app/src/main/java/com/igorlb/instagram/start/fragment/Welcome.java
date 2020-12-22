package com.igorlb.instagram.start.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.igorlb.instagram.R;

public class Welcome extends Fragment {
    MaterialButton textButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.initial_welcome_fragment, container, false);
        findViews(view);
        textButton.setOnTouchListener(TextButtonColor::colorPress);
        return view;
    }

    private void findViews(View view) {
        textButton = view.findViewById(R.id.textButton);
    }
}
