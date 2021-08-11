package com.hakretcode.instagram.main.profile;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public Uri getImageUri(ContentResolver contentResolver, Intent intent, int mode) {
        Uri data;
        if (mode == 1)
            data = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, (Bitmap) intent
                    .getExtras().get("data"), "img-" + new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date(System.currentTimeMillis())), null));
        else data = intent.getData();
        return data;
    }
}
