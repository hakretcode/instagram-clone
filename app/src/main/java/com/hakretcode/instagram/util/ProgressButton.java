package com.hakretcode.instagram.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.hakretcode.instagram.R;

public class ProgressButton extends FrameLayout {
    private final ProgressBar progressBar;
    private final MaterialButton button;
    private CharSequence text;


    public ProgressButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context);
        setLayoutParams(new FrameLayout.LayoutParams(context, attrs));
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);
        setId(typedArray.getResourceId(R.styleable.ProgressButton_android_id, 0));
        typedArray.recycle();

        MaterialButton button = new MaterialButton(context, attrs);
        this.button = button;
        button.setId(0);
        button.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(button);

        ProgressBar progressBar = new ProgressBar(context);
        this.progressBar = progressBar;
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        progressBar.setVisibility(GONE);
        int pixels = (int) TypedValue.applyDimension(TypedValue
                .COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(pixels, pixels);
        layoutParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(layoutParams);
        addView(progressBar);
    }

    public void setEnabled(boolean enabled, boolean showProgress) {
        setEnabled(enabled);
        if (showProgress) {
            text = button.getText();
            button.setText(null);
            progressBar.setVisibility(VISIBLE);
        } else {
            button.setText(text);
            progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        button.setOnClickListener(l);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        button.setEnabled(enabled);
    }
}
