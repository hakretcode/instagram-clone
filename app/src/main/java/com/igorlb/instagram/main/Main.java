package com.igorlb.instagram.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.igorlb.instagram.R;
import com.igorlb.instagram.main.gallery.GalleryFragment;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.main_activity);
        changeFragment(new GalleryFragment());
    }

    public void changeFragment(Fragment fragment) {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_alt, fragment);
        fragmentTransaction.commit();
    }
}
