package com.igorlb.instagram.main.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;

import java.util.List;

public class GalleryFragment extends Fragment implements Contract.GalleryFragment {
    private RecyclerView recyclerView;
    private Contract.Presenter presenter;
    private LinearLayoutCompat btnFolders;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new Presenter(this);
        return inflater.inflate(R.layout.main_gallery_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4, RecyclerView.VERTICAL, false));
        final List<Media> mediaList = presenter.getMedias(getContext().getContentResolver());
        final GalleryAdapter adapter = new GalleryAdapter(mediaList);
        recyclerView.setAdapter(adapter);
        final List<String> folders = presenter.getFolders(mediaList);
        btnFolders.setOnClickListener(v -> new BottomSheet(adapter, folders, (MaterialTextView)
                btnFolders.getChildAt(0)).show(getParentFragmentManager(), null));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        btnFolders = view.findViewById(R.id.btn_folders);
    }
}
