package com.hakretcode.instagram.main.home;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hakretcode.instagram.R;
import com.hakretcode.instagram.commons.TextViewWithMoreButton;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_home_recycle_item, parent, false));
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
        final TextViewWithMoreButton textview;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.post_description);
        }

        public void bind() {
            final SpannableStringBuilder textPost = new SpannableStringBuilder("igorlb");
            textPost.setSpan(new StyleSpan(Typeface.BOLD), 0, textPost.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textPost.append(' ').append("disjfdiwejdijeiojiej ijeiufuifu  ruifruifrfurnfhurfur nfruhnir nfijnriofrinfokrnofeofoenfiefiefieijfeijfeijf irefirnfirukfnenfenfienfienfienfinreifnreifnirnfirnifurnfiriufnreifnrenrnfrnfujnrjkfkefkenfkenfekjn");
            textview.setText(textPost);
        }
    }
}