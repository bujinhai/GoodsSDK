package com.jinshu.goodslibrary.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

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

}
