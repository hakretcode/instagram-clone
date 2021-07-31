package com.hakretcode.instagram.commons;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static int convertDpToPixel(Resources resources, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
