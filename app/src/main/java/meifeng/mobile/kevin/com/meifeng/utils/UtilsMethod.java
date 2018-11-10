package meifeng.mobile.kevin.com.meifeng.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import meifeng.mobile.kevin.com.meifeng.R;

public class UtilsMethod {

    private static long lastClickTime;

    @SuppressLint("SimpleDateFormat")
    public static String TimeStamp2Date(String timestampString) {
        if (TextUtils.isEmpty(timestampString)) {
            return "";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(new Date(timestamp));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String TimeStamp2Date(String timestampString,
                                        String dateformat) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(dateformat)
                .format(new Date(timestamp));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static long DateToTimeStamp(String TimeDate) {
        if (TimeDate != null) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date time = f.parse(TimeDate);
                return time.getTime() / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
                return -1;
            }

        }
        return 0;
    }

    // 获取am pm shot time
    public static String getAmPmFormatDateString(String dateString) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'KK:mm:ss aa", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
        Date newDate = null;
        if (dateString != null) {
            if (dateString.contains("T")) {
                SimpleDateFormat sdf_T = new SimpleDateFormat("yyyy-MM-dd'T'KK:mm:ss");
                SimpleDateFormat sdf_AM_PM = new SimpleDateFormat("KK:mm aa", Locale.ENGLISH);
                try {
                    newDate = sdf_T.parse(dateString);
                    Log.i("eee", sdf_AM_PM.format(newDate));
                    return sdf_AM_PM.format(newDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    newDate = sdf.parse(dateString);
                    return sdf.format(newDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    //格式化带T的日期
    public static String getFormatDateForT(String dateString) {
        if (dateString.contains("T")) {
            Date newDate = null;
            SimpleDateFormat sdf_T = new SimpleDateFormat("yyyy-MM-dd'T'KK:mm:ss");
            SimpleDateFormat sdf_normal = new SimpleDateFormat("MM/dd/yyyy");
            try {
                newDate = sdf_T.parse(dateString);
                return sdf_normal.format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateString;
    }

    // 保留N位数字
    public static String getNumberforString(String str, int n) {
        String format = "";
        switch (n) {
            case 2:
                format = "%02d";
                break;
            case 3:
                format = "%03d";
                break;
            default:
                format = "%01d";
                break;
        }
        //DecimalFormat df = new DecimalFormat(format);
        int number = Integer.parseInt(str);
        return String.format(format, number);
    }


    /**
     * 是否存在SD卡
     *
     * @return
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }

    /**
     * 计算SD卡剩余的内存空间
     *
     * @return
     */
    public static int freeSpaceOnSD() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory()
                .getPath());
        double sdFreeMb = (double) statFs.getAvailableBlocks()
                * (double) statFs.getBlockSize();
        return (int) sdFreeMb;
    }

    public static void showToast(Context context, String text, boolean isLong) {
        Toast.makeText(context, text,
                isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static boolean checkNet(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean checkIsWifi(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            State wifi = connectivity.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI).getState();
            if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取日期字符串.
     * <p/>
     * <pre>
     *  日期字符串格式： yyyyMMdd
     *  其中：
     *      yyyy   表示4位年.
     *      MM     表示2位月.
     *      dd     表示2位日.
     * </pre>
     *
     * @return String "yyyyMMdd"格式的日期字符串.
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(new Date());
    }

    public static String getDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }

    public static String getDateWithFormat(String format, String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = formatter.parse(dateString);

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        return formatter2.format(date);
    }

    public static String getDateStrWithFormatNormal(String format, String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = formatter.parse(dateString);

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");

        return formatter2.format(date);
    }

    public static String getAMPMDate(String dateString) {
        if (!isEmptyString(dateString)) {
            if (dateString.contains(":")) {
                String str[] = dateString.split(":");
                if (str.length == 2) {
                    int hours = Integer.parseInt(str[0]);
                    if (hours > 12) {
                        return dateString + "pm";
                    } else {
                        return dateString + "am";
                    }
                }
            }
        }
        return "00:00";
    }

    /**
     * @param view     传入需要进行键盘处理的
     * @param activity 当前
     */
    public static void setupUI(View view, final Activity activity) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    try {
                        UtilsMethod.hideSoftKeyboard(activity);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }
//		if (view instanceof ViewGroup) {
//			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//				View innerView = ((ViewGroup) view).getChildAt(i);
//				setupUI(innerView, activity);
//			}
//		}
    }

    /**
     * 获取当前分辨率下指定单位对应的像素大小（根据设备信息） px,dip,sp -> px
     * <p/>
     * Paint.setTextSize()单位为px
     * <p/>
     * 代码摘自：TextView.setTextSize()
     *
     * @param unit TypedValue.COMPLEX_UNIT_*
     * @param size
     * @return
     */
    public static float getRawSize(Context c, int unit, float size) {
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

        return TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
    }

    /**
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * @param time1 第一个时间戳
     * @param time2 第二个时间戳
     * @return 两个时间戳相隔天数
     */
    public static int getDay(String time1, String time2) {
        int day = 0;
        day = (Integer.parseInt(time1) - Integer.parseInt(time2))
                / (24 * 60 * 60);
        return day;

    }

    // 返回2分钟前、2小时前、2天前、2月前、2年前等近似的时间表示
    public static String getShortTime(int timestamp) {
        Date current = new Date();
        int currentTime = (int) (current.getTime() / 1000); // 以秒为单位
        int time = currentTime - timestamp;

        if (time < 0) {
            return "0分钟";
        } else if (time < 60) {
            // 1分钟内
            return Math.ceil(time) + "秒";
        } else if (time < 60 * 60) {
            // 1小时内
            return (int) Math.ceil(time / 60) + "分钟";
        } else if (time < 60 * 60 * 24) {
            // 1天内
            return (int) Math.ceil(time / (60 * 60)) + "小时";
        } else if (time < 60 * 60 * 24 * 30) {
            // 1月内
            return (int) Math.ceil(time / (60 * 60 * 24)) + "天";
        } else if (time < 60 * 60 * 24 * 365) {
            // 1年内
            return (int) Math.ceil(time / (60 * 60 * 24 * 30)) + "个月";
        } else {
            // 大于1年
            return (int) Math.ceil(time / (60 * 60 * 24 * 30 * 365)) + "年";
        }
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("(13|15|18)\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean validatePhoneNumber(String number) {
        Pattern p = Pattern.compile("^((1[3,4,5,7,8][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");//正则表达式
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 从view 得到图片
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }

    /**
     * 將bitmap轉話為byte
     *
     * @param bmp
     * @return
     */
    public static byte[] getByteByBitmap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // GET EMPTY VIEW
    public static View getViewForEmpty(Context c) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_empty_listview_textview, null);
        return v;
    }

    // 两次连续点击
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 500) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    public static boolean isEmptyString(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else if (str.equals("null")) {
            return true;
        } else if (str == null) {
            return true;
        }
        return false;
    }

    public static boolean isEmptyList(List list) {
        if (list == null) {
            return true;
        }

        if (list.size() == 0) {
            return true;
        }

        return false;
    }

    public static Type getTypeBarcode(String comfirm, String barcode, String cw) {
        Type type = new Type();
        if (comfirm.equals("") && barcode.equals("Y") && cw.equals("Y")) {
            type.scan = "Y";
            type.generate = "";
            type.print = "";
            return type;
        } else if (comfirm.equals("") && barcode.equals("N") && cw.equals("Y")) {
            type.scan = "";
            type.generate = "Y";
            type.print = "Y/N";
            return type;
        } else if (comfirm.equals("") && barcode.equals("N") && cw.equals("N")) {
            type.scan = "";
            type.generate = "Y";
            type.print = "Y/N";
            return type;
        } else if (comfirm.equals("") && barcode.equals("Y") && cw.equals("N")) {
            type.scan = "Y";
            type.generate = "";
            type.print = "";
            return type;
        } else if (comfirm.equals("Y") && barcode.equals("Y") && cw.equals("Y")) {
            type.scan = "Y";
            type.generate = "";
            type.print = "";
            return type;
        } else if (comfirm.equals("Y") && barcode.equals("N") && cw.equals("Y")) { //6
            type.scan = "";
            type.generate = "Y";
            type.print = "Y";
            return type;
        } else if (comfirm.equals("Y") && barcode.equals("N") && cw.equals("N")) { //7
            type.scan = "";
            type.generate = "Y";
            type.print = "Y";
        } else if (comfirm.equals("Y") && barcode.equals("Y") && cw.equals("N")) { //8
            type.scan = "Y";
            type.generate = "";
            type.print = "";
            return type;
        } else if (comfirm.equals("N") && barcode.equals("Y") && cw.equals("Y")) { //9
            type.scan = "Y";
            type.generate = "";
            type.print = "";
            return type;
        } else if (comfirm.equals("N") && barcode.equals("N") && cw.equals("Y")) { //10
            type.scan = "";
            type.generate = "Y";
            type.print = "";
            return type;
        } else if (comfirm.equals("N") && barcode.equals("N") && cw.equals("N")) { //11
            type.scan = "";
            type.generate = "Y";
            type.print = "";
            return type;
        } else if (comfirm.equals("N") && barcode.equals("Y") && cw.equals("N")) { //12
            type.scan = "Y";
            type.generate = "";
            type.print = "";
            return type;
        }

        return null;
    }

    public static class Type {
        private String scan;
        private String generate;
        private String print;

        public String getScan() {
            return scan;
        }

        public void setScan(String scan) {
            this.scan = scan;
        }

        public String getGenerate() {
            return generate;
        }

        public void setGenerate(String generate) {
            this.generate = generate;
        }

        public String getPrint() {
            return print;
        }

        public void setPrint(String print) {
            this.print = print;
        }
    }
}
