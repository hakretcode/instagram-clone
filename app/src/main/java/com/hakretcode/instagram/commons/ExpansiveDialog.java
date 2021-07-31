package com.hakretcode.instagram.commons;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.textview.MaterialTextView;
import com.hakretcode.instagram.R;

import java.util.ArrayList;
import java.util.List;

public class ExpansiveDialog extends Dialog {
    private final String title;

    private ExpansiveDialog(Context context, String title, List<Pair<MaterialTextView, View.OnClickListener>> textViewList) {
        super(context);
        Window window = getWindow();
        window.setContentView(R.layout.expansive_dialog);
        window.setBackgroundDrawableResource(R.drawable.bg_dialog);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.windowAnimations = R.style.ExpansiveDialog;
        window.setAttributes(layoutParams);
        window.setLayout((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 260, context.getResources().getDisplayMetrics()), FrameLayout.LayoutParams.WRAP_CONTENT);
        LinearLayoutCompat container = findViewById(R.id.ll_parent);
        for (Pair<MaterialTextView, View.OnClickListener> pair : textViewList) {
            MaterialTextView textView = pair.first;
            textView.setOnClickListener(v -> {
                View.OnClickListener onClick = pair.second;
                if (onClick != null) onClick.onClick(v);
                dismiss();
            });
            container.addView(textView);
        }
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (title == null) return;
        findViewById(R.id.line).setVisibility(View.VISIBLE);
        final MaterialTextView text = findViewById(R.id.tv_title);
        text.setVisibility(View.VISIBLE);
        text.setText(title);
    }

    public static class Builder {
        private final Context context;
        private final List<Pair<MaterialTextView, View.OnClickListener>> textViewList = new ArrayList<>();
        private String title;

        public Builder(Context context) {
            this.context = context;
        }

        public ExpansiveDialog.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ExpansiveDialog.Builder addButton(String title, View.OnClickListener onClick) {
            final MaterialTextView textView = new MaterialTextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(Utils.convertDpToPixel(context.getResources(), 14), 0, 0, 0);
            textView.setText(title);
            textView.setTextSize(16);
            textView.setFocusable(true);
            textView.setClickable(true);
            textView.setBackgroundResource(R.drawable.bg_tv_dialog);
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, Utils.convertDpToPixel(context.getResources(), 48)));
            textViewList.add(new Pair<>(textView, onClick));
            return this;
        }

        public ExpansiveDialog build() {
            return new ExpansiveDialog(context, title, textViewList);
        }
    }
}
