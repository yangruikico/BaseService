package com.gcstorage.reportservice.imageutil;

import android.content.Context;
import android.widget.ImageView;

public class ImageLoader implements ILoader {
    private ILoader mImageLoader;
    private static ImageLoader imageManager;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (imageManager == null) {
            synchronized (ImageLoader.class) {
                if (imageManager == null) {
                    imageManager = new ImageLoader();
                }
            }
        }
        return imageManager;
    }

    public void setImageLoader(ILoader imageLoader) {
        mImageLoader = imageLoader;
    }


    @Override
    public void load(Context context, ImageView imageView, int drawableRes) {
        mImageLoader.load(context, imageView, drawableRes);
    }

    @Override
    public void load(Context context, ImageView imageView, String url) {
        mImageLoader.load(context, imageView, url);
    }

    @Override
    public void load(Context context, ImageView imageView, byte[] image) {
        mImageLoader.load(context, imageView, image);
    }

    @Override
    public void load(Context context, ImageView imageView, int drawableRes, int errorResId, int placeholderResId) {
        mImageLoader.load(context, imageView, drawableRes, errorResId, placeholderResId);
    }

    @Override
    public void load(Context context, ImageView imageView, String url, int errorResId, int placeholderResId) {
        mImageLoader.load(context, imageView, url, errorResId, placeholderResId);
    }

    @Override
    public void load(Context context, ImageView imageView, byte[] image, int errorResId, int placeholderResId) {
        mImageLoader.load(context, imageView, image, errorResId, placeholderResId);
    }
}