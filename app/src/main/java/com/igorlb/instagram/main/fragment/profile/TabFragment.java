package com.igorlb.instagram.main.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.igorlb.instagram.R;

public class TabFragment extends Fragment {
    private final int position;

    public TabFragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_profile_viewpager_fragment, container, false);
        final RecyclerView recycle = view.findViewById(R.id.recycler_view);
        recycle.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycle.setAdapter(new TabRecycleAdapter());
        return view;
    }
}
