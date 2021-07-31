package com.hakretcode.instagram.commons;

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
import com.hakretcode.instagram.R;

public class TextViewWithMoreButton extends MaterialTextView {
    private final String more = "... more";
    private boolean isClick;
    private boolean isExpand;
    private CharSequence mText;
    private SpannableStringBuilder textWithMore;

    public TextViewWithMoreButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        post(this::reduce);
    }

    private void reduce() {
        if (isExpand || getLineCount() <= 2 || getText() == textWithMore) {
            return;
        }
        mText = getText();
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
        final int firstIndexSecondLine = getLayout().getLineVisibleEnd(0);
        int firstIndexThirdLine = getLayout().getLineVisibleEnd(1);
        // The second param is set value - 1 automatically
        final CharSequence textInSecondLine = mText.subSequence(firstIndexThirdLine, firstIndexThirdLine);
        final Rect bounds = new Rect();
        int subEnd = -1;
        do {
            subEnd++;
            CharSequence textWithoutMore = textInSecondLine.subSequence(0, textInSecondLine.length() - subEnd);
            final String textWithMore = textWithoutMore + more;
            getPaint().getTextBounds(textWithMore, 0, textWithMore.length(), bounds);
        } while (bounds.width() > getWidth());
        return mText.subSequence(0, firstIndexThirdLine - subEnd);
    }

    private void setupClick() {
        if (!isClick) {
            setOnClickListener(view -> {
                if (isExpand) setText(textWithMore);
                else setText(mText);
                isExpand = !isExpand;
            });
            isClick = true;
        }
    }
}
