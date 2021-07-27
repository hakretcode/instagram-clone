package com.hakretcode.instagram.main.gallery;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Presenter implements Contract.Presenter {
    private final Contract.GalleryFragment view;
    private boolean playWhenReady = true;
    private long currentTime;
    private MediaItem mediaItem;
    private SimpleExoPlayer player;
    private int windowMode;

    public Presenter(Contract.GalleryFragment galleryFragment) {
        view = galleryFragment;
    }

    @SuppressLint("InlinedApi")
    @Override
    public List<Media> getMedias(ContentResolver resolver) {
        final ArrayList<Media> mediaList = new ArrayList<>();
        try (final Cursor cursor = resolver.query(MediaStore.Files.getContentUri("external"),
                new String[]{"_data", "media_type", MediaStore.Files.FileColumns.DURATION}, "media_type = 1 OR media_type = 3",
                null, "date_added DESC")) {
            while (cursor.moveToNext()) {
                final String path = cursor.getString(0);
                final int type = cursor.getInt(1);
                final String[] pathSplit = path.split("/");
                final String folder = pathSplit[pathSplit.length - 2];
                if (type == 1) mediaList.add(new Media(path, type, folder));
                else mediaList.add(new Media(path, type, folder, cursor.getLong(2)));
            }
        } catch (Exception e) {
            Log.e("HakretCode/getMedia", e.getMessage(), e);
        }
        return mediaList;
    }

    @Override
    public List<String> getFolders(List<Media> mediaList) {
        final List<String> folders = mediaList.stream().map(Media::getFolder)
                .distinct().sorted().collect(Collectors.toList());
        folders.add(0, "Gallery");
        return folders;
    }

    @Override
    public void configurePlayer(PlayerView playerView) {
        if (mediaItem == null) return;
        if (player == null) {
            player = new SimpleExoPlayer.Builder(playerView.getContext()).build();
            player.addListener(new Player.EventListener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == SimpleExoPlayer.STATE_ENDED) configurePlayer(playerView);
                }
            });
        }
        playerView.setPlayer(player);
        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(windowMode, currentTime);
        player.prepare();
        playWhenReady = true;
        windowMode = 0;
        currentTime = 0;
    }

    @Override
    public void setMediaItem(String path) {
        if (path != null) mediaItem = MediaItem.fromUri(Uri.fromFile(new File(path)));
    }

    @Override
    public void stopPlayer() {
        if (player == null) return;
        playWhenReady = player.getPlayWhenReady();
        currentTime = player.getCurrentPosition();
        windowMode = player.getCurrentWindowIndex();
        player.release();
        player = null;
    }

    @Override
    public void stopPlayerWithoutSave() {
        if (player == null) return;
        player.release();
        player = null;
    }
}
