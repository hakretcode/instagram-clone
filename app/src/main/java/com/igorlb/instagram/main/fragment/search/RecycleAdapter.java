package com.igorlb.instagram.main.fragment.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;
import com.igorlb.instagram.main.fragment.search.RecycleAdapter.Holder;

public class RecycleAdapter extends RecyclerView.Adapter<Holder> {
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_search_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    static class Holder extends RecyclerView.ViewHolder {
        private final MaterialTextView username;
        private final MaterialTextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.name);
        }

        public void bind() {
            username.setText("igorlb");
            name.setText("Teste igorlb");
        }
    }
}