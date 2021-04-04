package com.igorlb.instagram.main.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;
import com.igorlb.instagram.main.gallery.GalleryAdapter.Holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GalleryAdapter extends RecyclerView.Adapter<Holder> {
    private final List<Media> mediaList;
    private List<Media> mediaListFiltered;

    public GalleryAdapter(List<Media> mediaList) {
        this.mediaList = mediaList;
        mediaListFiltered = mediaList;
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
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_gallery_holder_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mediaListFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return mediaListFiltered.size();
    }

    public String formatSeconds(byte seconds) {
        String secondsFormatted;
        if (seconds < 10) secondsFormatted = "0%d";
        else secondsFormatted = "%d";
        return secondsFormatted;
    }

    class Holder extends RecyclerView.ViewHolder {
        private final MaterialTextView tvVideoDuration;
        public AppCompatImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.small_image);
            tvVideoDuration = itemView.findViewById(R.id.tv_video_duration);
        }

        public void bind(Media media) {
            Glide.with(imageView.getContext()).load(media.getPath()).into(imageView);
            if (media.getType() == 3) tvVideoDuration.setText(format(media.getDuration()));
        }

        public String format(long millis) {
            long allSeconds = millis / 1000;
            int allMinutes;
            byte seconds, minutes, hours;
            String timeFormatted;
            if (allSeconds >= 60) {
                allMinutes = (int) (allSeconds / 60);
                seconds = (byte) (allSeconds % 60);
                if (allMinutes >= 60) {
                    hours = (byte) (allMinutes / 60);
                    minutes = (byte) (allMinutes % 60);
                    timeFormatted = String.format(Locale.US, "%d:%d:" + formatSeconds(seconds), hours, minutes, seconds);
                } else
                    timeFormatted = String.format(Locale.US, "%d:" + formatSeconds(seconds), allMinutes, seconds);
            } else
                timeFormatted = String.format(Locale.US, "0:" + formatSeconds((byte) allSeconds), allSeconds);
            return timeFormatted;
        }
    }
}
