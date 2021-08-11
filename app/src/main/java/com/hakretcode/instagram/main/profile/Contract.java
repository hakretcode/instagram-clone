package com.hakretcode.instagram.main.profile;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

public interface Contract {
    interface Presenter {
        void getImageForResult(Fragment fragment, boolean cameraMode);

        Uri getImageUri(ContentResolver contentResolver, Intent intent, int requestCode);
    }

    interface Profile {

    }
}
