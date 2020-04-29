package com.gcstorage.reportservice.imageutil;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ILoader {
    void load(Context context, ImageView imageView, @DrawableRes int drawableRes);

    void load(Context context, ImageView imageView, String url);

    void load(Context context, ImageView imageView, byte[] image);

    void load(Context context, ImageView imageView, @DrawableRes int drawableRes, @DrawableRes int errorResId, @DrawableRes int placeholderResId);

    void load(Context context, ImageView imageView, String url, @DrawableRes int errorResId, @DrawableRes int placeholderResId);

    void load(Context context, ImageView imageView, byte[] image, @DrawableRes int errorResId, @DrawableRes int placeholderResId);
}
