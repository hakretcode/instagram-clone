package com.igorlb.instagram.main.gallery;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;


public class Presenter implements Contract.Presenter {
    private final Contract.PostImage model;

    public Presenter(Contract.PostImage postImage) {
        model = postImage;
    }

    @SuppressLint("InlinedApi")
    @Override
    public List<Media> getImages(ContentResolver resolver) {
        final ArrayList<Media> mediaList = new ArrayList<>();
        try (final Cursor cursor = resolver.query(MediaStore.Files.getContentUri("external"),
                new String[]{"_data", "media_type", MediaStore.Files.FileColumns.DURATION}, "media_type = 1 OR media_type = 3",
                null, "date_added DESC")) {
            while (cursor.moveToNext()) {
                final String path = cursor.getString(0);
                final int type = cursor.getInt(1);
                if (type == 1) mediaList.add(new Media(path, type));
                else mediaList.add(new Media(path, type, cursor.getLong(2)));
            }
        }
        return mediaList;
    }
}
