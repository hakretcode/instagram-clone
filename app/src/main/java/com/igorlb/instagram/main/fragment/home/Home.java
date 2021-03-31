package com.igorlb.instagram.main.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.igorlb.instagram.R;

public class Home extends Fragment {
    private AppCompatActivity activity;
    private MaterialToolbar toolbar;
    private RecyclerView recycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_home_fragment, container, false);
        findViews(view);
        setBars();
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(new RecycleAdapter());
        return view;
    }

    private void findViews(View view) {
        toolbar = view.findViewById(R.id.actionbar);
        recycleView = view.findViewById(R.id.recycler_view);
    }

    private void setBars() {
        activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            statusBarColor(R.color.gray_light);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void statusBarColor(@ColorRes int color) {
        activity.getWindow()
                .setStatusBarColor(getResources().getColor(color, null));
    }

    @Override
    public void onPause() {
        if (activity != null) statusBarColor(android.R.color.transparent);
        super.onPause();
    }
}
