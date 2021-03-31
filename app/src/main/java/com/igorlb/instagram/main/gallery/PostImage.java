package com.igorlb.instagram.main.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.igorlb.instagram.R;

public class PostImage extends Fragment implements Contract.PostImage {
    private RecyclerView recyclerView;
    private Contract.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_gallery_fragment, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new Presenter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new GalleyAdapter(presenter.getImages(getContext().getContentResolver())));
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }
}
