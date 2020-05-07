package com.yrbase.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;


import com.yrbase.soulpermission.SoulPermission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;

/**
 * 获取项目资源 （如：res目录下的，assets 目录下的，manifest文件配置的资源等）
 * Created by fangs on 2017/9/13.
 */
public class ResUtils {

    //   int markId = ResUtils.getMipmapId("ic_sxt_xyc_" + data.getDeriction());

    private ResUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取 dimens 资源文件 指定 id 的资源
     *
     * @param dimenId 资源id
     * @return dimen 值
     */
    public static float getDimen(@DimenRes int dimenId) {
        return SoulPermission.getInstance().getContext().getResources().getDimension(dimenId);
    }

    /**
     * 获取 colors 资源文件 指定 id 的资源 (getResources().getColor 过时 替代方式)
     *
     * @param colorId 资源id
     * @return color
     */
    @ColorInt
    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(SoulPermission.getInstance().getContext(), colorId);
    }

    /**
     * 生成随机颜色
     * @return 颜色值
     */
    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return Color.rgb(r, g, b);
    }

    /**
     * 获取 strings 资源文件 指定 id 的资源
     *
     * @param stringId 资源id
     * @return ""
     */
    public static String getStr(@StringRes int stringId) {
        return SoulPermission.getInstance().getContext().getString(stringId);
    }

    /**
     * 获取 strings 资源文件 指定 id 的资源
     * @param StrArrayId 资源数组id
     * @return String[]
     */
    public static String[] getStrArray(@ArrayRes int StrArrayId){
        return SoulPermission.getInstance().getContext().getResources().getStringArray(StrArrayId);
    }

    /**
     * %d   （表示整数）
     * %f   （表示浮点数）
     * %s   （表示字符串）
     * 获取 strings 资源文件，指定 id 的资源，替换后的字符串
     *
     * @param id   资源ID（如：ID内容为 “病人ID：%1$d”）
     * @param args 将要替换的内容
     * @return 替换后的字符串
     */
    public static String getReplaceStr(@StringRes int id, Object... args) {
        String format = getStr(id);
        return String.format(format, args);
    }

    /**
     * 获取清单文件 指定 key的 meta-data 的值；（meta-data是一个键值对）
     *
     * @param metaKey       meta-data 的 key
     * @param metaValueType 返回值类型
     * @return
     */
    public static Object getMetaData(String metaKey, Object metaValueType) {
        Object value = null;

        try {
            Context context = SoulPermission.getInstance().getContext();
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = ai.metaData;
            if (metaValueType instanceof Integer) {
                value = bundle.getInt(metaKey);
            } else if (metaValueType instanceof Float) {
                value = bundle.getFloat(metaKey);
            } else if (metaValueType instanceof Boolean) {
                value = bundle.getBoolean(metaKey);
            } else if (metaValueType instanceof String) {
                value = bundle.getString(metaKey);
            }

        } catch (Exception e) {
          LogUtil.yangRui().e("获取清单文件配置失败"+"Dear developer. Don't forget to configure <meta-data android:name=\"my_test_metagadata\" android:value=\"testValue\"/> in your AndroidManifest.xml file.");
        }

        return value;
    }

    /**
     * 读取 assets 目录下 Json文件内容
     * @param fileName  文件名称带后缀名
     * @return
     */
    public static String getAssetsJson(String fileName) {
        Context context = SoulPermission.getInstance().getContext();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 读取 assets 目录下指定文件名的 内容
     * @param fileName
     * @return Properties （使用 properties.getProperty("key") 获取 value）
     */
    public static Properties getProperties(String fileName) {
        Context context = SoulPermission.getInstance().getContext();
        Properties props = new Properties();

        try {
            InputStream in = context.getAssets().open(fileName);
            props.load(in);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return props;
    }

    /*****************************以下为 通过资源名称 获取资源id ***************************************/
    private static final String RES_ID = "id";
    private static final String RES_DIMEN = "dimen";
    private static final String RES_COLOR = "color";
    private static final String RES_STRING = "string";
    private static final String RES_STYLE = "style";
    private static final String RES_DRAWABLE = "drawable";
    private static final String RES_MIPMAP = "mipmap";
    private static final String RES_RAW = "raw";
    private static final String RES_ANIM = "anim";
    private static final String RES_MENU = "menu";
    private static final String RES_LAYOUT = "layout";

    /**
     * 获取资源文件的id
     *
     * @param resName
     * @return
     */
    public static int getId(String resName) {
        return getResId(resName, RES_ID);
    }

    /**
     * 获取资源文件string的id
     *
     * @param resName
     * @return
     */
    public static int getStringId(String resName) {
        return getResId(resName, RES_STRING);
    }

    /**
     * 获取资源文件 drawable 的id
     * @param drawableName
     */
    public static int getDrawableId(String drawableName) {
        return getResId(drawableName, RES_DRAWABLE);
    }

    /**
     * 获取资源文件 mipmap 的id
     * @param mipmapName
     */
    public static int getMipmapId(String mipmapName) {
        return getResId(mipmapName, RES_MIPMAP);
    }

    /**
     * 获取资源文件layout的id
     *
     * @param resName
     * @return
     */
    public static int getLayoutId(String resName) {
        return getResId(resName, RES_LAYOUT);
    }

    /**
     * 获取资源文件style的id
     *
     * @param resName
     * @return
     */
    public static int getStyleId(String resName) {
        return getResId(resName, RES_STYLE);
    }

    /**
     * 获取资源文件color的id
     *
     * @param resName
     * @return
     */
    public static int getColorId(String resName) {
        return getResId(resName, RES_COLOR);
    }

    /**
     * 获取资源文件dimen的id
     *
     * @param resName
     * @return
     */
    public static int getDimenId(String resName) {
        return getResId(resName, RES_DIMEN);
    }

    /**
     * 获取资源文件ainm的id
     *
     * @param resName
     * @return
     */
    public static int getAnimId(String resName) {
        return getResId(resName, RES_ANIM);
    }

    /**
     * 获取资源文件menu的id
     */
    public static int getMenuId(String resName) {
        return getResId(resName, RES_MENU);
    }

    /**
     * 获取资源文件 raw 的id
     * @param resName
     * @return
     */
    public static int getRawId(String resName) {
        return getResId(resName, RES_RAW);
    }

    /**
     * 获取资源文件ID
     *
     * @param resName
     * @param defType
     * @return
     */
    public static int getResId(String resName, String defType) {
        Context context = SoulPermission.getInstance().getContext();
        return context.getResources().getIdentifier(resName, defType, context.getPackageName());
    }
}
