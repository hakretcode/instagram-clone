package com.igorlb.instagram.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;

public class ExpansiveDialog extends Dialog {
    private ExpansiveDialog(View content, Context context) {
        super(context);
        setContentView(content);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().getAttributes().windowAnimations = R.style.ExpansiveDialog;
    }

    public static class Builder {
        private final Context context;
        private final View content;
        private final LinearLayoutCompat parent;

        public Builder(Context context) {
            this.context = context;
            content = LayoutInflater.from(context).inflate(R.layout.expansive_dialog, null);
            parent = content.findViewById(R.id.ll_parent);
        }

        public ExpansiveDialog.Builder setTitle(String title) {
            final MaterialTextView text = content.findViewById(R.id.tv_title);
            text.setVisibility(View.VISIBLE);
            content.findViewById(R.id.line).setVisibility(View.VISIBLE);
            text.setText(title);
            return this;
        }

        public ExpansiveDialog.Builder addButton(String title, View.OnClickListener onClick) {
            final MaterialTextView textView = new MaterialTextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(30, 0, 0, 0);
            textView.setText(title);
            textView.setTextSize(15);
            textView.setFocusable(true);
            textView.setClickable(true);
            textView.setOnTouchListener((v, event) -> {
                final MaterialTextView button = (MaterialTextView) v;
                final Context context = button.getContext();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.setBackgroundResource(R.color.gray_200);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        button.setBackgroundResource(android.R.color.transparent);
                        break;
                }
                return false;
            });
            parent.addView(textView, new ViewGroup.LayoutParams(convertDpToPixel(250), convertDpToPixel(50)));
            return this;
        }

        private int convertDpToPixel(int dp) {
            return (int) (dp * context.getResources().getDisplayMetrics().density);
        }

        public ExpansiveDialog build() {
            return new ExpansiveDialog(content, context);
        }
    }
}
