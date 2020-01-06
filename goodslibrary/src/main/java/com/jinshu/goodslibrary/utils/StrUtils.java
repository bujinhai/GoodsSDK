package com.jinshu.goodslibrary.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.jinshu.goodslibrary.baseapp.GBaseSdk;

import java.text.DecimalFormat;

/**
 * Create on 2019/11/13 11:49 by bll
 */


public class StrUtils {

    public static String format(double money) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(money);
    }

    public static boolean isNotEmpty(String target) {
        if (target != null && target.length() > 0 && !target.equalsIgnoreCase("null") && !target.equals("")) {
            return true;
        }

        return false;
    }

    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    private static int sp2px(float spValue) {
        final float fontScale = GBaseSdk.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
