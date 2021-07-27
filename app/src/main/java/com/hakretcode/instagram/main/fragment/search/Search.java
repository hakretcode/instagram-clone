package com.hakretcode.instagram.main.fragment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.hakretcode.instagram.R;

public class Search extends Fragment {

    private MaterialToolbar toolbar;
    private RecyclerView recycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_search_fragment, container, false);
        findViews(view);
        setActionBar();

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(new RecycleAdapter());
        return view;
    }

    private void setActionBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void findViews(View view) {
        toolbar = view.findViewById(R.id.actionbar);
        recycleView = view.findViewById(R.id.recycler_view);
    }
}
