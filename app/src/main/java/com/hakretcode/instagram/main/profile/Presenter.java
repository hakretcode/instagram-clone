package com.hakretcode.instagram.main.profile;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;

import androidx.fragment.app.Fragment;

public class Presenter implements Contract.Presenter {
    @Override
    public void getImageForResult(Fragment fragment, boolean cameraMode) {
        if (cameraMode)
        fragment.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
        else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            fragment.startActivityForResult(intent, 2);
        }
    }
}
