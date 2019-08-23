package com.yrbase.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yrbase.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 图片相关显示类
 *
 * @Author 郑家双
 * @Date 2018/4/13
 * @Version 1.0
 *
 * 1.分辨图片方向，并将图片进行一定的旋转
 * 2.不做任何处理的加载网络图片
 * 3.不做任何处理的加载资源图片
 * 4.加载圆角资源图片
 * 5.加载圆角网络图片
 *
 */
public class ImageUtil {
    /**
     * 分辨图片方向，并将图片进行一定的旋转
     * @param path 主要是为了判断图片的方向而已
     * @return Android开发中，在对图片进行展示、编辑、发送等操作时经常会涉及Exif的操作
     * ，Android中操作Exif主要是通过ExifInterface，ExifInterface看上去是一个接口
     * ，其实是一个类，位于Android.media.ExifInterface的位置。进入ExifInterface类
     * ，发现方法很少，主要就是三个方面：读取、写入、缩略图。
     * orientation获取图片的方向
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();
            //旋转角度
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载图片,centercrop
     * @param url 图片对象可以是uri,string,int,
     * @param target 目标控件
     * @param defaultResId 默认占位图片id
     */
    public static void displayCenterCrop(Object url, ImageView target, int defaultResId){

        if (target != null) {
            RequestOptions options = RequestOptions
                    .centerCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(defaultResId)
                    .placeholder(defaultResId)
                    ;
            Glide.with(target.getContext()).load(url).apply(options).into(target);
        }
    }


  /*
   */
    public static void displayCenterCrop(ImageView mImageView, String url){
        if (mImageView != null) {
            RequestOptions options = RequestOptions
                    .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                    .error(R.mipmap.default_image)
                    .placeholder(R.mipmap.default_image)
                    .centerCrop();
            Glide.with(mImageView.getContext()).load(url).apply(options).into(mImageView);
        }

    }



    /*
     */
    public static void displayCenterCrop(ImageView mImageView, String url, int defaultResId, int errotResId){
        RequestOptions options = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                .error(errotResId)
                .placeholder(defaultResId)
                .centerCrop();
        Glide.with(mImageView.getContext()).load(url).apply(options).into(mImageView);
    }
    /**
     * 不做任何处理的加载网络图片
     * @param context 上下文
     * @param url 图片对象可以是uri,string,int,
     * @param target 目标控件
     * @param defaultResId 默认占位图片id
     * @param errotResId 错误图片id
     */
    public static void displayNormal(Context context, String url, ImageView target, int defaultResId, int errotResId){
        RequestOptions options = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                .centerInside()
                .error(errotResId)
                .placeholder(defaultResId);
        Glide.with(context).load(url).apply(options).into(target);
    }

    /**
     * 根据旋转角度将图片旋转
     *
     * @param img
     * @return
     */
    public static Bitmap toturn(int angle, Bitmap img) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle); /*翻转90度*/
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }
    /**
     * 不做任何处理的图片，并使图片居中显示
     * @param context 上下文
     * @param url 图片对象可以是uri,string,int,
     * @param target 目标控件
     * @param defaultResId 默认占位图片id
     * @param errotResId 错误图片id
     */
    public static void displayFitCenter(Context context, Object url, ImageView target, int defaultResId, int errotResId){
        RequestOptions options = RequestOptions
                .fitCenterTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errotResId)
                .placeholder(defaultResId);
        Glide.with(context).load(url).apply(options).into(target);
    }








    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /**
     * 加载gif动图
     * @param context 上下文
     * @param url 图片对象可以是uri,string,int,
     * @param target 目标控件
     * @param defaultResId 默认占位图片id
     * @param errotResId 错误图片id
     */
    public static void displayGif(Context context, Object url, ImageView target, int defaultResId, int errotResId){
        RequestOptions options = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                .error(errotResId)
                .placeholder(defaultResId);
        Glide.with(context).asGif().load(url).apply(options).into(target);
    }

    /**
     * byte[]转换成Bitmap
     *
     * @param b
     * @return
     */

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * bitmap转字节数组
     *
     * @return
     * @auther 万紫辉
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        //初始化一个流对象
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        //把bitmap100%高质量压缩 到 output对象里
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 90;
        while (output.toByteArray().length / 1024 > 1024) { // 循环判断如果压缩后图片是否大于1024kb,大于继续压缩
            output.reset(); // 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);// 这里压缩options%，把压缩后的数据存放到output中
            options -= 10;// 每次都减少10
        }
        //回收bitmap，避免泄漏
        bitmap.recycle();
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取网落图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) throws IOException {
        URL myFileURL;
        Bitmap bitmap = null;

        myFileURL = new URL(url);
        //获得连接
        HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
        //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
        conn.setConnectTimeout(6000);
        //连接设置获得数据流
        conn.setDoInput(true);
        //不使用缓存
        conn.setUseCaches(false);
        //这句可有可无，没有影响
        //conn.connect();
        //得到数据流
        InputStream is = conn.getInputStream();
        //解析得到图片
        bitmap = BitmapFactory.decodeStream(is);
        //关闭数据流
        is.close();

        return bitmap;
    }
}

