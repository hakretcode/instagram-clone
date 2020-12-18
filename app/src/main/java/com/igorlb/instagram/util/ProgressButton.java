package com.igorlb.instagram.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.igorlb.instagram.R;

public class ProgressButton extends FrameLayout {

    final ProgressBar progress;
    private final MaterialButton button;
    String text;


    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.progress_button, this);
        button = (MaterialButton) getChildAt(0);
        progress = (ProgressBar) getChildAt(1);
        TypedArray attr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressButton, 0, 0);
        setAttrs(attr);
        attr.recycle();
        finnaly();
    }

    private void setAttrs(TypedArray attr) {
        text = attr.getString(R.styleable.ProgressButton_android_text);
        final int width = attr.getInt(R.styleable.ProgressButton_android_layout_width, -1);
        final int height = attr.getInt(R.styleable.ProgressButton_android_layout_height, -1);
        final LayoutParams layoutParams = new LayoutParams(width, height);
        button.setText(text);
        button.setLayoutParams(layoutParams);
    }

    @Override
    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
        finnaly();
    }

    private void finnaly() {
        invalidate();
        requestLayout();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        button.setOnClickListener(l);
    }

    public void setProgressVisibility(boolean visibility) {
        if (visibility) {
            button.setText(null);
            progress.setVisibility(VISIBLE);
        } else {
            button.setText(text);
            progress.setVisibility(GONE);
        }
        finnaly();
    }
}
