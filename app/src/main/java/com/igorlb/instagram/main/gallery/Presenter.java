package com.igorlb.instagram.main.gallery;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Presenter implements Contract.Presenter {
    private final Contract.GalleryFragment model;

    public Presenter(Contract.GalleryFragment galleryFragment) {
        model = galleryFragment;
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
}
