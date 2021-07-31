package com.hakretcode.instagram.main.profile;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface Contract {
    interface Presenter {
        void getImageForResult(Fragment fragment, boolean cameraMode);
    }

    interface Profile {

    }
}
