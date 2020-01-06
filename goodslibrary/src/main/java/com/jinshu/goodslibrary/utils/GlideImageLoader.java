package com.jinshu.goodslibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Create on 2019/9/30 21:28 by bll
 */


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLoaderUtils.display(context, imageView, (String) path);
    }
}
