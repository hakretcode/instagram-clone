package com.igorlb.instagram.start.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.igorlb.instagram.R;

public class Login extends Fragment implements View.OnTouchListener {
    private MaterialButton buttonFacebook;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragment = inflater.inflate(R.layout.initial_login_fragment, container, false);
        buttonFacebook = fragment.findViewById(R.id.button_facebook);
        buttonFacebook.setOnTouchListener(this);
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                buttonFacebook.setTextColor(getContext().getColor(R.color.blue_pressed));
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                buttonFacebook.setTextColor(getContext().getColor(R.color.blue));
                break;
        }
        return true;
    }
}
