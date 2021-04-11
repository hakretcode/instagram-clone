package com.igorlb.instagram.main.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;
import com.igorlb.instagram.main.gallery.GalleryAdapter.Holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class GalleryAdapter extends RecyclerView.Adapter<Holder> {
    private final RecyclerView recyclerView;
    private final List<Media> mediaList;
    private final Consumer<Media> setImagePreview;
    private List<Media> mediaListFiltered;
    private byte count;
    private Pair<Media, View> currentForeground;
    private boolean firstBind = true;

    public GalleryAdapter(RecyclerView recyclerView, List<Media> mediaList, Consumer<Media> setImagePreview) {
        this.recyclerView = recyclerView;
        this.mediaList = mediaList;
        mediaListFiltered = mediaList;
        this.setImagePreview = setImagePreview;
    }

    public void setMediaList(String title) {
        if (title.equals("Gallery")) mediaListFiltered = mediaList;
        else {
            List<Media> tempMediaList = new ArrayList<>();
            mediaList.forEach(media -> {
                if (media.getFolder().equals(title)) tempMediaList.add(media);
            });
            mediaListFiltered = tempMediaList;
        }
        currentForeground.first.setForegroundUnable(false);
        final Media media = mediaListFiltered.get(0);
        setImagePreview.accept(media);
        media.setForegroundUnable(true);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_gallery_holder_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Media media = mediaListFiltered.get(position);
        holder.bind(media);
        if (firstBind) {
            firstBind = false;
            final SelectUtils selectUtils = holder.selectUtils;
            currentForeground = new Pair<>(media, selectUtils.getForegroundView());
            selectUtils.setForegroundVisibility(media, currentForeground);
            setImagePreview.accept(media);
        }
    }

    @Override
    public int getItemCount() {
        return mediaListFiltered.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private final MaterialTextView tvVideoDuration;
        private final ConstraintLayout container;
        public AppCompatImageView imageView;
        public SelectUtils selectUtils;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.small_image);
            tvVideoDuration = itemView.findViewById(R.id.tv_video_duration);
            container = (ConstraintLayout) imageView.getParent();
            selectUtils = new SelectUtils(
                    itemView.findViewById(R.id.select_foreground), itemView.findViewById(
                    R.id.civ_counter_background), itemView.findViewById(R.id.tv_counter));
        }

        public void bind(Media media) {
            Glide.with(imageView.getContext()).load(media.getPath()).into(imageView);
            if (media.getType() == 3) tvVideoDuration.setText(format(media.getDuration()));
            if (selectUtils.isForegroundVisible(media))
                if (!selectUtils.getForegroundView().equals(currentForeground.second)) {
                    currentForeground.second.setVisibility(View.GONE);
                    currentForeground = new Pair<>(media, selectUtils.getForegroundView());
                }
            container.setOnClickListener(v -> {
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, getAdapterPosition());
                currentForeground = selectUtils.setForegroundVisibility(media, currentForeground);
                setImagePreview.accept(media);
            });
        }

        public String format(long millis) {
            long allSeconds = millis / 1000;
            int allMinutes;
            byte seconds, minutes, hours;
            if (allSeconds >= 60) {
                allMinutes = (int) (allSeconds / 60);
                seconds = (byte) (allSeconds % 60);
                if (allMinutes >= 60) {
                    hours = (byte) (allMinutes / 60);
                    minutes = (byte) (allMinutes % 60);
                    return String.format(Locale.US, "%d:%d:" + formatSeconds(seconds), hours, minutes, seconds);
                } else
                    return String.format(Locale.US, "%d:" + formatSeconds(seconds), allMinutes, seconds);
            } else
                return String.format(Locale.US, "0:" + formatSeconds((byte) allSeconds), allSeconds);
        }

        public String formatSeconds(byte seconds) {
            String secondsFormatted;
            if (seconds < 10) secondsFormatted = "0%d";
            else secondsFormatted = "%d";
            return secondsFormatted;
        }
    }
}
