package com.igorlb.instagram.main.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.igorlb.instagram.R;

public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragment = inflater.inflate(R.layout.main_home_fragment, container, false);
        final RecyclerView recycleView = fragment.findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(new RecycleAdapter());
        statusBarColor(R.color.gray_input);
        return fragment;
    }

    private void statusBarColor(@ColorRes int color) {
        ((AppCompatActivity)getActivity()).getWindow()
                .setStatusBarColor(getResources().getColor(color, null));
    }

    @Override
    public void onPause() {
        statusBarColor(android.R.color.transparent);
        super.onPause();
    }
}
