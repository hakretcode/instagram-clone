package com.igorlb.instagram.main.gallery;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;

import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private final GalleryAdapter adapter;
    private final List<String> folders;
    private final MaterialTextView btnCurrentFolderTitle;

    public BottomSheetFragment(GalleryAdapter adapter, List<String> folders, MaterialTextView btnCurrentFolderTitle) {
        this.adapter = adapter;
        this.folders = folders;
        this.btnCurrentFolderTitle = btnCurrentFolderTitle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_gallery_bs_fragment, container, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(getDialog()
                .findViewById((com.google.android.material.R.id.design_bottom_sheet)));
        setHeight(bottomSheetBehavior);
        final RecyclerView rvFolders = view.findViewById(R.id.rv_folders);
        setRecycle(rvFolders);
        rvFolders.setOnScrollChangeListener((v, sx, sy, osx, osy) -> {
            if (osy > 100) bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
    }

    private void setHeight(BottomSheetBehavior<View> bottomSheetBehavior) {
        final Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getRealSize(point);
        bottomSheetBehavior.setPeekHeight(point.y);
    }

    private void setRecycle(RecyclerView rvFolders) {
        rvFolders.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFolders.setAdapter(new GalleryBsAdapter(folders, folder -> {
            adapter.setMediaList(folder);
            btnCurrentFolderTitle.setText(folder);
            dismiss();
        }));
    }
}