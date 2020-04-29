package com.gcstorage.reportservice.imageutil;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideLoader implements ILoader {

    @Override
    public void load(Context context, ImageView imageView, int drawableRes) {
        Glide.with(context)
                .load(drawableRes)
                .into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, byte[] image) {
        Glide.with(context)
                .load(image)
                .into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, int drawableRes, int errorResId, int placeholderResId) {

        RequestOptions requestOptions = new RequestOptions().placeholder(placeholderResId).error(errorResId);
        Glide.with(context)
                .load(drawableRes)
                .apply(requestOptions)
                .into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, String url, int errorResId, int placeholderResId) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholderResId).error(errorResId);

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    @Override
    public void load(Context context, ImageView imageView, byte[] image, int errorResId, int placeholderResId) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholderResId).error(errorResId);

        Glide.with(context)
                .load(image)
                .apply(requestOptions)
                .into(imageView);
    }
}
