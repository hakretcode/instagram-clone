package com.hakretcode.instagram.main.gallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textview.MaterialTextView;
import com.hakretcode.instagram.R;
import com.hakretcode.instagram.main.Main;
import com.hakretcode.instagram.util.ExpansiveDialog;

import java.util.List;

public class GalleryFragment extends Fragment implements Contract.GalleryFragment {
    private RecyclerView recyclerView;
    private Contract.Presenter presenter;
    private LinearLayoutCompat btnFolders;
    private AppCompatImageView imagePreview;
    private AppBarLayout ablHeader;
    private boolean firstBootRecycle = true;
    private PlayerView videoPreview;
    private String mediaPath;
    private AppCompatImageView ivExpandButton;

    @Override
    public void onStart() {
        super.onStart();
        presenter.setMediaItem(mediaPath);
        presenter.configurePlayer(videoPreview);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopPlayer();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                requestDenied();
            else {
                final ActivityResultLauncher<String> launcher = registerForActivityResult
                        (new ActivityResultContracts.RequestPermission(), result -> {
                            if (result) {
                                ((Main) getActivity()).changeFragment(new GalleryFragment());
                                getParentFragmentManager().beginTransaction().remove(this).commit();
                            } else requestDenied();
                        });
                launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    public void requestDenied() {
        Toast.makeText(getContext(), R.string.permission_necessary, Toast.LENGTH_LONG).show();
        getParentFragmentManager().popBackStack();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new Presenter(this);
        return inflater.inflate(R.layout.main_gallery_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        ivExpandButton.setOnClickListener(v -> {
            if (imagePreview.getScaleType() == ImageView.ScaleType.CENTER_CROP &&
                    videoPreview.getResizeMode() == AspectRatioFrameLayout.RESIZE_MODE_ZOOM) {
                imagePreview.setScaleType(ImageView.ScaleType.FIT_CENTER);
                videoPreview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            } else {
                imagePreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                videoPreview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
            }
        });
        setRecycler(view);
    }

    private void setRecycler(View view) {
        final GridLayoutManager manager = new CustomGridLayoutManager(view.getContext(), 4);
        recyclerView.setLayoutManager(manager);
        final List<Media> mediaList = presenter.getMedias(getContext().getContentResolver());
        final GalleryAdapter adapter = new GalleryAdapter(recyclerView, mediaList, media -> {
            presenter.stopPlayerWithoutSave();
            final String path = media.getPath();
            if (media.getType() == 1) {
                imagePreview.setVisibility(View.VISIBLE);
                videoPreview.setVisibility(View.GONE);
                Glide.with(imagePreview.getContext()).load(path).into(imagePreview);
            } else {
                videoPreview.setVisibility(View.VISIBLE);
                imagePreview.setVisibility(View.GONE);
                presenter.setMediaItem(path);
                presenter.configurePlayer(videoPreview);
                mediaPath = path;
            }
            ablHeader.setExpanded(true);
        });

        recyclerView.setOnScrollChangeListener((v, sy, sx, osy, osx) -> {
            if (recyclerView.computeVerticalScrollOffset() == 0) {
                if (firstBootRecycle) {
                    firstBootRecycle = false;
                    return;
                }
                recyclerView.stopScroll();
                ablHeader.setExpanded(false);
            }
        });

        recyclerView.setAdapter(adapter);

        final List<String> folders = presenter.getFolders(mediaList);
        btnFolders.setOnClickListener(v -> new BottomSheetFragment(adapter, folders, (MaterialTextView)
                btnFolders.getChildAt(0)).show(getParentFragmentManager(), null));
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        btnFolders = view.findViewById(R.id.btn_folders);
        imagePreview = view.findViewById(R.id.image_preview);
        videoPreview = view.findViewById(R.id.video_preview);
        ablHeader = view.findViewById(R.id.abl_header);
        ivExpandButton = view.findViewById(R.id.iv_expand);
    }
}
