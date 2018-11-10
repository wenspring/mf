package meifeng.mobile.kevin.com.meifeng.base;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.mob.MobSDK;

import meifeng.mobile.kevin.com.meifeng.mvp.login.model.UserModel;
import meifeng.mobile.kevin.com.meifeng.mvp.login.view.LoginActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.FavoriteActivity;

public class SelfApplication extends Application {

    private static Context mContext;
    public static UserModel user;
    //public static String chooseCity;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        user = new UserModel();
        MobSDK.init(this);

        if (Build.VERSION.SDK_INT >= 24){
            // android 7.0系统解决拍照的问题
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    public static boolean isLogin() {
        if (user == null || user.getToken() == null) {
            showToastMessage("请先登录");
            Intent i = new Intent();
            i.setClass(mContext, LoginActivity.class);
            mContext.startActivity(i);
            return false;
        }
        return true;
    }

    public static boolean isLoginNow() {
        if (user == null || user.getToken() == null) {
            return false;
        }
        return true;
    }

//    // 设置选择的城市
//    public static void setChooseCity(String city) {
//        chooseCity = city;
//    }

    /**
     * 檢查存儲權限
     */
    public static void checkPermissions(final Context context) {
        // 增加權限
        int permission_write = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission_camera = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

        if (permission_write != PackageManager.PERMISSION_GRANTED
                || permission_read != PackageManager.PERMISSION_GRANTED
                || permission_camera != PackageManager.PERMISSION_GRANTED) {
            showSetP(context);
        }
    }

    static void showSetP(Context context) {
        ActivityCompat.requestPermissions(
                (Activity) context,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
    }


    public static Context getContext() {
        return mContext;
    }

    public static void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }


}
