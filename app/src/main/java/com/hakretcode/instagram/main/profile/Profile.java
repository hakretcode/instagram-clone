package com.hakretcode.instagram.main.profile;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.commons.ExpansiveDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment implements Contract.Profile {
    private final Contract.Presenter presenter = new Presenter();
    MaterialToolbar toolbar;
    private MaterialTextView actionBarTitle;
    private MaterialTextView description;
    private MaterialTextView site;
    private AppBarLayout bar;
    private TabLayout tab;
    private ViewPager2 viewPager2;
    private CircleImageView profilePhoto;
    private ActivityResultLauncher<CropImageContractOptions> launcher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        launcher = registerForActivityResult(new CropImageContract(), result -> {
            if (result.isSuccessful()) profilePhoto.setImageBitmap(result.getBitmap(getContext()));
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_profile_fragment, container, false);
        findViews(view);
        setActionBar("igorlb");
        setVisibilityText(description);
        setVisibilityText(site);

        viewPager2.setAdapter(new TabPagerAdapter(this));
        new TabLayoutMediator(tab, viewPager2, (tab1, position) -> {
            if (position == 0) tab1.setIcon(R.drawable.grid);
            else tab1.setIcon(R.drawable.tab_profile_icon);
        }).attach();

        profilePhoto.setOnClickListener(v -> {
            ExpansiveDialog.Builder dialog = new ExpansiveDialog.Builder(getContext());
            if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
                dialog.addButton(getString(R.string.take_a_photo), w -> presenter.getImageForResult(this, true));
            dialog.addButton(getString(R.string.get_from_gallery), w -> presenter.getImageForResult(this, false)).build().show();
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != Activity.RESULT_OK) return;
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri data;
        if (requestCode == 1)
            data = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, (Bitmap) intent
                    .getExtras().get("data"), "img-"+new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date(System.currentTimeMillis())), null));
        else data = intent.getData();
        CropImageOptions cropImageOptions = new CropImageOptions();
        cropImageOptions.activityTitle = "Instagram Clone";
        cropImageOptions.aspectRatioX = 1;
        cropImageOptions.aspectRatioY = 1;
        cropImageOptions.fixAspectRatio = true;
        cropImageOptions.autoZoomEnabled = true;
        cropImageOptions.activityTitle = "TEsT";
        launcher.launch(new CropImageContractOptions(data, cropImageOptions));
    }

    private void findViews(View view) {
        toolbar = view.findViewById(R.id.actionbar);
        actionBarTitle = (MaterialTextView) toolbar.getChildAt(0);
        bar = view.findViewById(R.id.app_bar);
        description = view.findViewById(R.id.description);
        site = view.findViewById(R.id.site);
        tab = view.findViewById(R.id.tab);
        viewPager2 = view.findViewById(R.id.view_pager2);
        profilePhoto = view.findViewById(R.id.profile_photo);
    }

    private void setActionBar(final String title) {
        actionBarTitle.setText(title);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
        }
        bar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) toolbar.setBackgroundColor(0xFFF);
            else toolbar.setBackgroundResource(R.drawable.bg_actionbar_profile);
        });
    }

    private void setVisibilityText(MaterialTextView text) {
        if (text.length() > 0) text.setVisibility(View.VISIBLE);
        else text.setVisibility(View.GONE);
    }
}
