package com.igorlb.instagram.initial;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.igorlb.instagram.R;

public class TextButtonColor {
    public static boolean colorPress(View view, MotionEvent event) {
        final MaterialButton button = (MaterialButton) view;
        final Context context = button.getContext();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setTextColor(context.getColor(R.color.blue_pressed));
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                button.setTextColor(context.getColor(R.color.blue));
                break;
        }
        return false;
    }
}
