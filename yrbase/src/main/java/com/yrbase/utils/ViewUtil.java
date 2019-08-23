package com.yrbase.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yrbase.baseactivity.utils.AppManager;
import com.yrbase.soulpermission.SoulPermission;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by EA on 2018/5/2
 */

public class ViewUtil {

    public static int stringToNum(String num) {
        //
        if (ViewUtil.isNumeric(num)) {
            return Integer.valueOf(num);
        }
        return 0;
    }



    private static final double EARTH_RADIUS = 6371393; // 平均半径,单位：m


    /**
     * 通过AB点经纬度获取距离
     *
     * @return 距离(单位 ： 米)
     */
    public static String getDistance(String longitude1, String latitude1, String longitude2, String latitude2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin


        if (ViewUtil.isDoubleOrFloat(longitude1) &&
                ViewUtil.isDoubleOrFloat(latitude1) &&
                ViewUtil.isDoubleOrFloat(longitude2) &&
                ViewUtil.isDoubleOrFloat(latitude2)) {

            double radiansAX = Math.toRadians(Double.valueOf(longitude1)); // A经弧度
            double radiansAY = Math.toRadians(Double.valueOf(latitude1)); // A纬弧度
            double radiansBX = Math.toRadians(Double.valueOf(longitude2)); // B经弧度
            double radiansBY = Math.toRadians(Double.valueOf(latitude2)); // B纬弧度

            // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
            double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                    + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
            double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
            return EARTH_RADIUS * acos + ""; // 最终结果

        } else {

            return "未知";
        }


    }


    private static HashMap<String, Integer> noticeId = new HashMap<>();


    private static int maxNoticeId = 1;

    /*
     * 是否为浮点数？double或float类型。
     * @param str 传入的字符串。
     * @return 是浮点数返回true,否则返回false。
     */
    public static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }




    /*
     * 设置字体
     *
     * */

    public static final String DIN_BOLD = "fonts/din_bold.otf";

    public static void setFonts(String fonts, TextView textView) {
        if (textView == null)
            return;
        try {
            Typeface typeFace = Typeface.createFromAsset(textView.getContext().getAssets(), fonts);
            textView.setTypeface(typeFace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化数字为千分位显示 要格式化的数字
     *
     * @param text
     * @return
     */
    public static String changePrice(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.00");//0.00
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.00");//0.00
            } else {
                df = new DecimalFormat("###,##0.00");//0.00
            }
        } else {
            df = new DecimalFormat("###,##0.00");//0.00  ###,##0
        }
        double number = 0.00;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            return text;
        }
        return df.format(number);
    }


    /**
     * 电话号码分割
     *
     * @param mobile
     * @return
     */
    public static String getMobile(String mobile) {
        if (mobile != null && mobile.length() > 3) {
            StringBuilder stringBuffer = new StringBuilder(mobile);
            int length = mobile.length();
            int index = length % 3;
            int position = length / 3;
            for (int i = 1; i < position; i++) {
                if (i <= index) {
                    stringBuffer.insert(length - (4 * i), "-");
                } else {
                    stringBuffer.insert(length - 3 * i - index, "-");
                }
            }
            System.out.println(" 电话 : " + stringBuffer.toString());
            return stringBuffer.toString();
        }
        return "";
    }

    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkClientIsInstall(Context context, String clientPageName) {
        if (ViewUtil.isEmpty(clientPageName)) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(clientPageName)) {
                    return true;
                }
            }
        }

        return false;
    }


    public static String getVersionCode() {
        try {
            String pkName = SoulPermission.getInstance().getContext().getPackageName();
            return SoulPermission.getInstance().getContext().getPackageManager().getPackageInfo(pkName, 0).versionCode + "";
        } catch (Exception e) {
            return 8 + "";
        }
    }

    public static String getVersionName() {
        try {
            String pkName = SoulPermission.getInstance().getContext().getPackageName();
            return SoulPermission.getInstance().getContext().getPackageManager().getPackageInfo(pkName, 0).versionName + "";
        } catch (Exception e) {
            return "1.0.0";
        }
    }

    /*
     *
     *
     * 打开微信
     *
     * */
    public static void openWeiChat(Context context, String message) {

        //调到微信
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText(message, message);
        cm.setPrimaryClip(mClipData);
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setComponent(cmp);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }


    /*
     *
     * 判断是否是整数
     *
     * */
    public static boolean isNumeric(String str) {
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为数字、、包括整数和小数
     *
     * @param str
     * @return
     */
    public static boolean isNumericWithPoint(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 这个方法是保证时间两位数据显示，如果为1点时，就为01
     *
     * @return String
     */

    public static String dataLong(int c)// 这个方法是保证时间两位数据显示，如果为1点时，就为01
    {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


    /**
     * 是否是同一天
     *
     * @param t0
     * @param t1
     * @return
     */
    public static boolean isSameDay(Long t0, Long t1) {

        Date date0 = new Date(t0);   //假设给的毫秒t0,t1，就从这句开始
        Date date1 = new Date(t1);

        GregorianCalendar ca0 = new GregorianCalendar();
        //如果给的Date对象date，就忽略上句
        GregorianCalendar ca1 = new GregorianCalendar();
        ca0.setTime(date0);
        ca1.setTime(date1);
        //获取ca0和ca1的年，月，日，对比是否相同
        return ca0.get(GregorianCalendar.YEAR) == ca1.get(GregorianCalendar.YEAR) &&
                ca0.get(GregorianCalendar.MONTH) == ca1.get(GregorianCalendar.MONTH) &&
                ca0.get(GregorianCalendar.DAY_OF_MONTH) == ca1.get(GregorianCalendar.DAY_OF_MONTH);
    }


    //运费类型0:运费到付 1:到件付
    public static String getFreightType(String string) {
        String Type0 = "运费到付";
        String Type1 = "货到付款";
        if ("0".equals(string)) {
            return Type0;
        } else if ("1".equals(string)) {
            return Type1;
        }
        return null;
    }


    //订单状态0：待付款；1：待发货；2：待收货；3：已完成；4：已关闭

    public static String getSellerOrderState(int orderSaleStatus) {
        switch (orderSaleStatus) {
            case 0:
                return "待付款";
            case 1:
                return "待发货";
            case 2:
                return "待收货";
            case 3:
                return "已完成";
            case 4:
                return "已关闭";
        }

        return "";
    } //

    //订单状态（商家端显示）。0：待付款；1：待开单；2：待发货；3：已发货；4：挂单中；5：已关闭；6：已完成
    public static String getBuyerOrderState(int orderSaleStatus) {
        switch (orderSaleStatus) {
            case 0:
                return "待付款";
            case 1:
                return "待开单";
            case 2:
                return "待发货";
            case 3:
                return "已发货";
            case 4:
                return "挂单中";
            case 5:
                return "已关闭";
            case 6:
                return "已完成";
        }

        return "";
    } //


    // 付款方式。0：暂无；1：微信；2：支付宝；3：银联；4：赊账

    public static String getPayType(int payType) {
        switch (payType) {
            case 0:
                return "暂无";
            case 1:
                return "微信";
            case 2:
                return "支付宝";
            case 3:
                return "银联";
            case 4:
                return "赊账";
        }

        return "";
    }


    // 账单类型：0:售出 1:进货 2:退款（金额为负）目前只有售出

    public static String amountType(int payType) {
        switch (payType) {
            case 0:
                return "[售出]";
            case 1:
                return "[进货]";
            case 2:
                return "[退款]";
            case 3:
                return "[还款]";

        }

        return "";
    }


    public static List<String> getSimpleList() {
        return getSimpleList(30);
    }

    public static List<String> getSimpleList(int size) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            strings.add(i + "");
        }
        return strings;
    }


    /**
     * 强制打开
     */
    public static void openImm(Activity mActivity) {
        if (mActivity != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && mActivity.getCurrentFocus() != null) {
                imm.showSoftInput(mActivity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    /**
     * 强制隐藏键盘
     */
    public static void closeImm(Activity mActivity) {
        if (mActivity != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && mActivity.getCurrentFocus() != null) {
                if (mActivity.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    public static String formatString(int resourceID, Object object) {
        return String.format(SoulPermission.getInstance().getContext().getResources().getString(resourceID), object);
    }


    public static int getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String getIntToString(int string) {
        String s = String.valueOf(string);
        return TextUtils.isEmpty(s) ? "" : s;

    }

    /**
     * 返回年-月-日
     *
     * @param _ms
     * @return
     */
    public static String ms2Date(long _ms) {
        if (_ms == 0) return "";
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }


    /**
     * 返回年-月-日
     *
     * @param _ms
     * @return
     */
    public static String ms2DatePoint(long _ms) {
        if (_ms == 0) return "";
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 返回月
     *
     * @param _ms
     * @return
     */
    public static String ms2Month(long _ms) {
        if (_ms == 0) return "";
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("MM", Locale.getDefault());
        return format.format(date);
    }


    /**
     * 返回年-月-日
     *
     * @param _ms
     * @return
     */

    public static String ms2Date(long _ms, String string) {
        if (_ms == 0) return string;
        return ms2Date(_ms);
    }

    /**
     * 返回年-月-日 时:分:秒
     *
     * @param _ms
     * @return
     */
    public static String ms2DateAll(long _ms, String string) {
        if (_ms == 0) return string;
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }


    public static long stringTOLong(String miniTime) {


        long miniTimeL = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ViewUtil.YMDHMS);

            Date birthday = sdf.parse(miniTime);

            miniTimeL = birthday.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return miniTimeL;
        }


        return miniTimeL;
    }

    public static String simple = "MM-dd HH:mm";

    public static String allData = "yyyy-MM-dd HH:mm:ss";

    public static String ms2DateAll(long _ms, String string, String formart) {
        if (_ms == 0) return string;
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat(formart, Locale.getDefault());
        return format.format(date);
    }

    public static String ms2DateAll(long _ms) {
        return ms2DateAll(_ms, "");
    }

    /**
     * 获取以小时为最大单位的时间格式显示
     * HH:mm:ss
     *
     * @return
     */
    public static String getDiffTimeStr(Long diffS, String split) {
        long diffSL = diffS.longValue() / 1000;
        if (diffSL < 0L) {
            return null;
        }

        StringBuffer strBuf = new StringBuffer();
        //一小时
        long hh = 1 * 60 * 60L;
        //一分钟
        long mm = 1 * 60L;
        //一秒
//		long ss = 1L;
        if (diffSL > hh) {
            long hour = diffSL / hh;
            diffSL = diffSL - hour * hh;
            strBuf.append(hour);
        } else {
            strBuf.append("00");
        }
        strBuf.append(split);
        if (diffSL > mm) {
            long mmL = diffSL / mm;
            diffSL = diffSL - mmL * mm;
            if (mmL < 10)
                strBuf.append("0");
            strBuf.append(mmL);
        } else {
            strBuf.append("00");
        }
        strBuf.append(split);
        if (diffSL < 10L) {
            strBuf.append("0");
        }
        strBuf.append(diffSL);
        return strBuf.toString();
    }

    /**
     * 返回月.日
     *
     * @param _ms
     * @return
     */
    public static String ms2Date2(long _ms) {
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
        return format.format(date);
    }


    /**
     * 设置edittext只能输入小数点后两位
     */
    public static void afterDotTwo(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.toString().indexOf(".") > 9) {
                        s = s.toString().subSequence(0, 9) + s.toString().substring(s.toString().indexOf("."));
                        editText.setText(s);
                        editText.setSelection(9);
                    }
                } else {// 限制最多能输入9位整数
                    if (s.toString().length() > 9) {
                        s = s.toString().subSequence(0, 9);
                        editText.setText(s);
                        editText.setSelection(9);
                    }
                } // 判断小数点后只能输入两位
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }//如果第一个数字为0，第二个不为点，就不允许输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().trim() != null && !editText.getText().toString().trim().equals("")) {
                    if (editText.getText().toString().trim().substring(0, 1).equals(".")) {
                        editText.setText("0" + editText.getText().toString().trim());
                        editText.setSelection(2);
                    }
                }
            }
        });
    }


    public static int getDaySum(String yearString, String monthString) {

        int days = 30;

        try {
            int year = Integer.valueOf(yearString);
            int month = Integer.valueOf(monthString);
            if (month != 2) {
                switch (month) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        days = 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        days = 30;

                }
            } else {
                // 闰年
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                    days = 29;
                else
                    days = 28;

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return days;
        }
        return days;
    }

    /**
     * 空 返回 true
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty() || list.size() == 0;
    }

    /**
     * 空返回 false  验证姓名
     *
     * @param text
     * @param tipString
     * @return
     */
    public static boolean validateText(String text, String tipString) {
        if (ViewUtil.isEmpty(text)) {
            ViewUtil.Toast(tipString);
            return false;
        }

        return true;
    }


    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        if (ViewUtil.isEmpty(password)) {
            ViewUtil.Toast("验证码不能为空");
            return false;
        }

        if (password.length() != 4) {
            ViewUtil.Toast("验证码长度为4位");
            return false;
        }

        return true;
    }

    /**
     * 验证用户名并给出提示
     *
     * @param mobile
     * @return
     */
    public static boolean validateMobileWithToast(String mobile) {

        if (TextUtils.isEmpty(mobile)) {
            ViewUtil.Toast("手机号不能为空");
            return false;
        }

        if (!ViewUtil.isMobile(mobile)) {
            ViewUtil.Toast("请输入正确的手机号");
            return false;
        }

        return true;
    }

    /**
     * 验证用户名
     *
     * @param mobile
     * @return
     */
    public static boolean validateMobile(String mobile) {

        if (TextUtils.isEmpty(mobile)) {
            return false;
        }

        if (!ViewUtil.isMobile(mobile)) {
            return false;
        }

        return true;
    }


    public static void putParams(Map<String, String> params, String key, String
            string) {
        if (!(isEmpty(string) || isEmpty(key))) {
            params.put(key, string);
        } else {
            params.put(key, "");
        }
    }

    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView
            mRecyclerView, int n) {
        if (manager == null || mRecyclerView == null)
            return;
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }


    /**
     * @param imgUrl
     * @return
     */
    public static float getVideoScale(String imgUrl) {
        float videoScale = 16.0f / 9.0f;
        try {
            if (imgUrl != null) {
                int indexStart = imgUrl.indexOf("w_");
                int endW = imgUrl.indexOf("h_");
                int endH = imgUrl.indexOf("s_");
                if (endH != -1 && endW != -1) {
                    String width = imgUrl.substring(indexStart + 2, endW);
                    String height = imgUrl.substring(endW + 2, endH);
                    videoScale = (float) Integer.valueOf(width) / Integer.valueOf(height);
                }
            }
            return videoScale;
        } catch (NumberFormatException e) {
            return videoScale;
        }
    }

    public static float getScale(String imgUrl) {
        float videoScale = 0.0f;
        try {
            if (null != imgUrl && imgUrl.startsWith("http")) {
                int indexStart = imgUrl.indexOf("w_");
                int endW = imgUrl.indexOf("h_");
                int endH = imgUrl.indexOf("s_");
                String width = imgUrl.substring(indexStart + 2, endW);
                String height = imgUrl.substring(endW + 2, endH);
                videoScale = (float) Integer.valueOf(width) / Integer.valueOf(height);
            }
        } catch (Exception e) {
            return 1;
        }
        return videoScale;
    }

    public static int[] getWidthAndHeight(String imgUrl) {
        int[] value = new int[2];
        value[0] = 0;
        value[1] = 0;
        if (null != imgUrl && imgUrl.startsWith("http")) {
            int indexStart = imgUrl.indexOf("w_");
            int endW = imgUrl.indexOf("h_");
            int endH = imgUrl.indexOf("s_");
            String width = imgUrl.substring(indexStart + 2, endW);
            String height = imgUrl.substring(endW + 2, endH);

            value[0] = Integer.valueOf(width);
            value[1] = Integer.valueOf(height);
        }
        return value;
    }


    public static File getVideoCacheDir(Context context) {
        return new File(context.getExternalCacheDir(), "yiyun");
    }

    /**
     * make true current connect service is wifi
     *
     * @param
     * @return
     */
    public static boolean isWifi() {

        ConnectivityManager connectivityManager = (ConnectivityManager) SoulPermission.getInstance().getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();


        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /*
     * 检查网络是否可用,可用返回true
     * */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connect = (ConnectivityManager) SoulPermission.getInstance().getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connect == null) {
            return false;
        } else {
            // get all network info if (networkInfo != null) {
            NetworkInfo[] info = connect.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * 判断是否为手机号
     *
     * */
    public static boolean isMobile(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
       /* Pattern p = Pattern
                .compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
        Matcher m = p.matcher(mobiles);*/
        return mobiles.length() == 11;

    }


    public static void setText(TextView textView, String str) {
        setText(textView, str, null);
    }


    public static void setText(TextView textView, String str, String defaultString) {
        if (null != textView) {
            if (!isEmpty(str)) {
                textView.setText(str);
               /* if (textView.getVisibility() != View.VISIBLE) {
                    textView.setVisibility(View.VISIBLE);
                }*/
            } else {
                if (TextUtils.isEmpty(defaultString)) {
                    textView.setText("");
                } else {
                    textView.setText(defaultString);
                }
            }
        }
    }

    public static void setText(TextView textView, String str, int place) {


        if (null != textView) {
            if (!isEmpty(str)) {
                textView.setText(str);
               /* if (textView.getVisibility() != View.VISIBLE) {
                    textView.setVisibility(View.VISIBLE);
                }*/
            } else {
                String string = ViewUtil.getString(place);
                if (TextUtils.isEmpty(string)) {
                    textView.setText("");
                } else {
                    textView.setText(string);
                }
            }
        }
    }


    public static boolean isEmpty(String str) {
        boolean aNull = str == null || "".equals(str.trim()) || "{}".equals(str)
                || "[]".equals(str) || "null".equals(str) || "{[]}".equals(str) || "[{}]".equals(str);
        return aNull;
    }


    private static long lastTime = 0;

    /**
     * @param string
     */
    public static void Toast(String string) {
        if (ViewUtil.isEmpty(string) && null != SoulPermission.getInstance().getContext()) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTime >= 2 * 1000) {
            lastTime = currentTimeMillis;
            int tid = android.os.Process.myTid();

            try {
                Toast.makeText(SoulPermission.getInstance().getContext(), string + "", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* if (tid == GcstorageApplication.Thread.currentThread()) {
                Toast.makeText(SoulPermission.getInstance().getContext(), string + "", Toast.LENGTH_SHORT).show();
            } else {
                Looper.prepare();
                Toast.makeText(SoulPermission.getInstance().getContext(), string + "", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }*/

        }
    }



    public static void Toast(int stringId) {
        String str = getString(stringId);
        Toast(str);
    }

    /**
     * @param stringArrayId
     * @return
     */
    public static String[] getStringArray(int stringArrayId) {

        //List<String> list= Arrays.asList(array);
        return SoulPermission.getInstance().getContext().getResources().getStringArray(stringArrayId);
    }


    /**
     * @param stringArrayId
     * @return
     */
    public static List<String> getStringList(int stringArrayId) {
        return Arrays.asList(SoulPermission.getInstance().getContext().getResources().getStringArray(stringArrayId));
    }

    /**
     * 获取颜色
     *
     * @param colorId
     * @return
     */
    @ColorInt
    public static int getColor(int colorId) {
        return ContextCompat.getColor(SoulPermission.getInstance().getContext(), colorId);
    }


    /**
     * @param stringId
     * @return
     */
    public static String getString(int stringId) {
        return SoulPermission.getInstance().getContext().getResources().getString(stringId);
    }


    public static int getHeightInPx() {
        return SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().heightPixels;
    }


    public static int getWidthInPx() {
        return SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeightInDp() {
        return px2dip(SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().heightPixels);
    }

    public static int getWidthInDp() {
        final float height = SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().widthPixels;
        return px2dip(height);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dp) {
        final float scale = SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float sp) {
        final float scale = SoulPermission.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public static void startActivity(Class<? extends Activity> clazz) {
        Activity activity = AppManager.getAppManager().currentActivity();
        Intent intent = new Intent(activity, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    /**
     * 启动指定 包名 的 第三方应用
     *
     * @param act
     * @param packageName
     * @param bundle
     */
    public static void jumpPackage(Activity act, String packageName, Bundle bundle) {
        PackageManager packageManager = act.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (null != bundle) {
            intent.putExtras(bundle);
        }

        act.startActivity(intent);
    }


    public static void startActivity(Intent intent) {
        Activity activity = AppManager.getAppManager().currentActivity();
        activity.startActivity(intent);
    }

    public static void startActivityForResult(Class<? extends Activity> clazz,
                                              int requestCode) {
        Activity activity = AppManager.getAppManager().currentActivity();
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, requestCode);
    }


    public static void startActivityForResult(Intent intent, int requestCode) {
        Activity activity = AppManager.getAppManager().currentActivity();
        activity.startActivityForResult(intent, requestCode);
    }

    /*
     * 设置控件的 显示 隐藏  避免  callback 回调  控件为 null
     * */

    public static void setVisibility(View mView, int visible) {
        if (mView != null) {
            mView.setVisibility(visible);
        }
    }

    public static void setVisibility(View mView, boolean visible) {
        if (mView != null) {
            mView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public static void setImageResource(ImageView mView, int resId) {
        if (mView != null) {
            mView.setImageResource(resId);
        }
    }

    /**
     * 把布局转换成bitmap
     *
     * @return bitmap
     */
    public static Bitmap getBitmapByView(View view) {
        Bitmap bitmap = null;
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    public static void callPhone(String phoneNum) {

        if (!ViewUtil.isEmpty(phoneNum)) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            startActivity(intent);
        } else {
            ViewUtil.Toast("电话号码不存在");
        }

    }


    /**
     * 将数字转化为以K ，w结尾
     */
    public static String changeNum(long num) {
        String str = "";
        double n = (double) num;

        if (num < 1000) {
            return num + "";
        }

        if (num >= 1000 && num < 10000) {
            n = n / 1000.0f;
            str = "k";
        } else if (n >= 10000.0f) {
            n = n / 10000.0f;//1.将数字转换成以万为单位的数字
            str = "w";
        }
        BigDecimal b = new BigDecimal(n);
        n = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//2.转换后的数字四舍五入保留小数点后一位;
        return n + str;
    }


    public static View getView(@LayoutRes int resource, @Nullable ViewGroup root) {
        return LayoutInflater.from(AppManager.getAppManager().currentActivity()).inflate(resource, root, false);
    }


    /**
     * 38    * 获取现在时间
     * 39    *
     * 40    * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     * 41
     */


    public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String YMD = "yyyy-MM-dd";

    public static String getStringDate(String string, Date currentTime) {


        if (!ViewUtil.isEmpty(string) && null != currentTime) {
            SimpleDateFormat formatter = new SimpleDateFormat(string, Locale.getDefault());
            String dateString = formatter.format(currentTime);
            return dateString;

        }


        return "emptyDate";
    }


    public static long getStringToLong(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(time);
            Date birthday = sdf.parse(time);


            return birthday.getTime();

        } catch (Exception e) {
            return 0;
        }

    }


}
