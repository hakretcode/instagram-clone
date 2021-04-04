package com.igorlb.instagram.main.gallery;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;

import java.util.List;

public class BottomSheet extends BottomSheetDialogFragment {
    private final GalleryAdapter adapter;
    private final List<String> folders;
    private final MaterialTextView btnCurrentFolderTitle;

    public BottomSheet(GalleryAdapter adapter, List<String> folders, MaterialTextView btnCurrentFolderTitle) {
        this.adapter = adapter;
        this.folders = folders;
        this.btnCurrentFolderTitle = btnCurrentFolderTitle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public void onStart() {
        super.onStart();
        final Dialog dialog = getDialog();
        final FragmentActivity activity = getActivity();
        if (dialog == null || activity == null) return;
        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(dialog.
                findViewById((com.google.android.material.R.id.design_bottom_sheet)));
        final Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        bottomSheetBehavior.setPeekHeight(point.y);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_gallery_bs_fragment, container, false);
        final RecyclerView rvFolders = view.findViewById(R.id.rv_folders);
        rvFolders.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFolders.setAdapter(new GalleryBsAdapter(folders, folder -> {
            adapter.setMediaList(folder);
            btnCurrentFolderTitle.setText(folder);
            dismiss();
        }));
        return view;
    }
}