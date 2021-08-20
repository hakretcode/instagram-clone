package com.hakretcode.instagram.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.main.gallery.GalleryFragment;
import com.hakretcode.instagram.main.home.Home;
import com.hakretcode.instagram.main.profile.Profile;
import com.hakretcode.instagram.main.search.Search;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private final Fragment home = new Home();
    private final Fragment search = new Search();
    private final Fragment stars = null;
    private final Fragment profile = new Profile();
    private Fragment gallery = new GalleryFragment();
    private Fragment currentFragment;
    private boolean requestUpdateGalleryFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.main_activity);
        currentFragment = home;
        final var fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_alt, home).add(R.id.fragment_alt, search)
                .add(R.id.fragment_alt, gallery).add(R.id.fragment_alt, profile)
                .hide(search).hide(gallery).hide(profile).commit();
        BottomNavigationView bottomNav = findViewById(R.id.navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                changeFragment(home);
                break;
            case R.id.search_item:
                changeFragment(search);
                break;
            case R.id.gallery_item:
                Fragment galleryFragment = gallery;
                if (((GalleryFragment) galleryFragment).verifyStoragePermission()) {
                    if (requestUpdateGalleryFragment) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(galleryFragment);
                        galleryFragment = new GalleryFragment();
                        gallery = galleryFragment;
                        fragmentTransaction.add(R.id.fragment_alt, galleryFragment).hide(galleryFragment).commit();
                        requestUpdateGalleryFragment = false;
                    }
                    changeFragment(gallery);
                } else requestUpdateGalleryFragment = true;
                break;
            case R.id.profile_item:
                changeFragment(profile);
                break;
        }
        return true;
    }

    public void changeFragment(Fragment fragment) {
        if (fragment == currentFragment) return;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.hide(currentFragment).commit();
        currentFragment = fragment;
    }
}
