package com.igorlb.instagram.main.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;
import com.igorlb.instagram.main.gallery.GalleryBsAdapter.Holder;

import java.util.List;
import java.util.function.Consumer;

public class GalleryBsAdapter extends RecyclerView.Adapter<Holder> {
    private final List<String> folders;
    private final Consumer<String> loadImage;

    public GalleryBsAdapter(List<String> folders, Consumer<String> click) {
        this.folders = folders;
        this.loadImage = click;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_gallery_bs_holder_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(folders.get(position));
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private final MaterialTextView tvFolderTitle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvFolderTitle = itemView.findViewById(R.id.tv_folder_title);
        }

        public void bind(String title) {
            tvFolderTitle.setText(title);
            tvFolderTitle.setOnClickListener(v -> loadImage.accept(title));
        }
    }
}
