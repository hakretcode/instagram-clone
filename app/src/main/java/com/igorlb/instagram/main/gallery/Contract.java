package com.igorlb.instagram.main.gallery;

import android.content.ContentResolver;

import java.util.List;

public interface Contract {
    interface Presenter {
        List<String> getImages(ContentResolver resolver);
    }

    interface PostImage {

    }
}
