package com.igorlb.instagram.main.gallery;

import android.content.ContentResolver;
import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public interface Contract {
    interface Presenter {
        List<Media> getMedias(ContentResolver resolver);

        List<String> getFolders(List<Media> mediaList);

        void configurePlayer(PlayerView playerView);

        void stopPlayer();

        void stopPlayerWithoutSave();

        void setMediaItem(String path);
    }

    interface GalleryFragment {

    }
}
