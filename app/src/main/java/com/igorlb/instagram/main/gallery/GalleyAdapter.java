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
import com.igorlb.instagram.main.gallery.GalleyAdapter.Holder;

import java.util.List;
import java.util.Locale;

public class GalleyAdapter extends RecyclerView.Adapter<Holder> {
    private List<Media> list;

    public GalleyAdapter(List<Media> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_gallery_holder_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
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
            long seconds = (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);
            long minutes = ((millis % (1000 * 60 * 60)) / (1000 * 60));
            long hours = (millis / (1000 * 60 * 60));
            String secondsToFormat;
            if (seconds < 10) secondsToFormat = "0%d";
            else secondsToFormat = "%d";
            if (hours > 0) return String.format(Locale.US, "%d:%d:" + secondsToFormat, hours, minutes, seconds);
            else if (minutes > 0) return String.format(Locale.US, "%d:" + secondsToFormat, minutes, seconds);
            else return String.format(Locale.US, "0:" + secondsToFormat, seconds);
        }
    }
}
