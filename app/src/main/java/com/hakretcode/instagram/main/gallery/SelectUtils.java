package com.hakretcode.instagram.main.gallery;

import android.view.View;
import android.widget.FrameLayout;

import androidx.core.util.Pair;

import com.google.android.material.textview.MaterialTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectUtils {
    private final View foreground;
    private final FrameLayout flContainer;
    private final MaterialTextView counterView;
    private final CircleImageView civCountBackground;

    public SelectUtils(View foreground, CircleImageView civCountBackground, MaterialTextView counterView) {
        this.foreground = foreground;
        flContainer = (FrameLayout) civCountBackground.getParent();
        this.civCountBackground = civCountBackground;
        this.counterView = counterView;
    }

    public Pair<Media, View> setForegroundVisibility(Media media, Pair<Media, View> pair) {
        if (media.isForegroundEnable()) return pair;
        pair.second.setVisibility(View.GONE);
        pair.first.setForegroundUnable(false);
        foreground.setVisibility(View.VISIBLE);
        media.setForegroundUnable(true);
        return new Pair<>(media, foreground);
    }

    public boolean isForegroundVisible(Media media) {
        final boolean result = media.isForegroundEnable();
        if (result) foreground.setVisibility(View.VISIBLE);
        else foreground.setVisibility(View.GONE);
        return result;
    }

    public View getForegroundView() {
        return foreground;
    }
}
