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
    View fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.main_profile_fragment, container, false);

        final MaterialToolbar toolbar = fragment.findViewById(R.id.actionbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        setActionBarTitle("igorlb");

        final MaterialTextView description = fragment.findViewById(R.id.description);
        final MaterialTextView site = fragment.findViewById(R.id.site);
        setVisibilityText(description);
        setVisibilityText(site);

        AppBarLayout bar = fragment.findViewById(R.id.app_bar);
        bar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) toolbar.setBackgroundColor(0xFFF);
            else toolbar.setBackgroundResource(R.drawable.bg_actionbar);
        });

        TabLayout tab = fragment.findViewById(R.id.tab);
        ViewPager2 viewPager2 = fragment.findViewById(R.id.view_pager2);
        viewPager2.setAdapter(new TabPagerAdapter(this));
        new TabLayoutMediator(tab, viewPager2, (tab1, position) -> {
            if (position == 0) tab1.setIcon(R.drawable.grid);
            else tab1.setIcon(R.drawable.tab_profile_icon);
        }).attach();

        return fragment;
    }

    private void setVisibilityText(MaterialTextView text) {
        if (text.length() > 0) text.setVisibility(View.VISIBLE);
        else text.setVisibility(View.GONE);
    }

    public void setActionBarTitle(String title) {
        ((MaterialTextView) fragment.findViewById(R.id.actionbar_title)).setText(title);
    }
}
