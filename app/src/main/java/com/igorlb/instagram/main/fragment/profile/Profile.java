package com.igorlb.instagram.main.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;

public class Profile extends Fragment {
    MaterialToolbar toolbar;
    private MaterialTextView actionBarTitle;
    private MaterialTextView description;
    private MaterialTextView site;
    private AppBarLayout bar;
    private TabLayout tab;
    private ViewPager2 viewPager2;

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

        return view;
    }

    private void findViews(View view) {
        toolbar = view.findViewById(R.id.actionbar);
        actionBarTitle = (MaterialTextView) toolbar.getChildAt(0);
        bar = view.findViewById(R.id.app_bar);
        description = view.findViewById(R.id.description);
        site = view.findViewById(R.id.site);
        tab = view.findViewById(R.id.tab);
        viewPager2 = view.findViewById(R.id.view_pager2);
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
