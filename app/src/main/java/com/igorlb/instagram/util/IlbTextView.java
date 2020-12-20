package com.igorlb.instagram.util;

import android.content.Context;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textview.MaterialTextView;
import com.igorlb.instagram.R;

public class IlbTextView extends MaterialTextView {
    private final String more = "... more";
    private boolean setClickForBigText;
    private boolean expand;
    private CharSequence textBig;
    private SpannableStringBuilder textWithMore;

    public IlbTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        post(this::reduce);
    }

    private void reduce() {
        if (expand || getLineCount() <= 2 || getText() == textWithMore) {
            return;
        }
        textBig = getText();
        setupClick();
        setTextWithMore(formatBigText());
        setText(textWithMore);
    }

    public void setTextWithMore(CharSequence formattedText) {
        final SpannableString moreStylezed = new SpannableString(more);
        moreStylezed.setSpan(new ForegroundColorSpan(getContext().getColor(R.color.gray_text)),
                0, more.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textWithMore = new SpannableStringBuilder(formattedText)
                .append(moreStylezed);
    }

    private CharSequence formatBigText() {
        final int startEndLine = getLayout().getLineVisibleEnd(0) + 1;
        final int endLine = getLayout().getLineVisibleEnd(1);
        final CharSequence endLineText = textBig.subSequence(startEndLine, endLine);
        final Rect bounds = new Rect();
        int subEnd = -1;
        do {
            subEnd++;
            CharSequence textWithoutMore = endLineText.subSequence(0, endLineText.length() - subEnd);
            final String textWithMore = textWithoutMore + more;
            getPaint().getTextBounds(textWithMore, 0, textWithMore.length(), bounds);
        } while (bounds.width() > getWidth());
        return textBig.subSequence(0, getLayout().getLineVisibleEnd(1) - subEnd);
    }

    private void setupClick() {
        if (!setClickForBigText) {
            setOnClickListener(view -> {
                if (!expand) setText(textBig);
                else setText(textWithMore);
                expand = !expand;
            });
            setClickForBigText = true;
        }
    }
}
