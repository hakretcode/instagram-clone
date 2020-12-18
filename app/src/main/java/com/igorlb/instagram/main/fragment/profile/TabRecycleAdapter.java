package com.igorlb.instagram.main.fragment.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igorlb.instagram.R;

public class TabRecycleAdapter extends RecyclerView.Adapter<TabRecycleAdapter.Holder> {
    private static int sum = 1;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup v, int viewType) {
        return new Holder(LayoutInflater.from(v.getContext()).inflate(R.layout.profile_view_pager_item, v, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    static class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
