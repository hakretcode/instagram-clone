package com.igorlb.instagram.main.gallery;

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

    @Override
    public List<String> getImages(ContentResolver resolver) {
        final ArrayList<String> imagesDirectory = new ArrayList<>();
        try (final Cursor cursor = resolver.query(MediaStore.Files.getContentUri("external"),
                new String[]{"media_type", "_data", "date_added"},
                "media_type = 1 OR media_type = 3", null, "date_added DESC")) {
            final int column = cursor.getColumnIndexOrThrow("_data");
            while (cursor.moveToNext()) {
                final String data = cursor.getString(column);
                imagesDirectory.add(data);
            }
        }
        return imagesDirectory;
    }
}
