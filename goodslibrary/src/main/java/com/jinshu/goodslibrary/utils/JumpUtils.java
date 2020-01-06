package com.jinshu.goodslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jinshu.goodslibrary.R;


/**
 * Created by bll on 19/11/12.
 */
public final class JumpUtils {


    public static void jumpActivity(final Activity context, Class cls) {
        jumpActivity(context, cls, null);
    }

    public static void jumpActivity(final Activity context, Class cls,
                                    Bundle bundle) {
        jumpActivity(context, cls, bundle, false);
    }

    public static void jumpActivity(final Activity context, Class cls,
                                    Bundle bundle, boolean bTop) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        intent.setClass(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
        context.overridePendingTransition(R.anim.g_fade_in, R.anim.g_fade_out);

    }

    public static void jumpActivityWithIntent(final Context context,
                                              Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    public static void jumpActivityForResult(final Activity context, Class cls,
                                             int requestCode) {
        jumpActivityForResult(context, cls, requestCode, null);
    }

    public static void jumpActivityForResult(final Activity context, Class cls,
                                             int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        intent.setClass(context, cls);

        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(R.anim.g_fade_in, R.anim.g_fade_out);
    }
}