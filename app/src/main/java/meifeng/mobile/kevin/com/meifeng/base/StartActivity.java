package meifeng.mobile.kevin.com.meifeng.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.mvp.login.presenter.LoginPresenter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.MainActivity;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.ext.view.StatusBarSetting;

public class StartActivity extends Activity {
    private static final long DELAY_TIME = 1000L;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarSetting.setColor(this, getResources().getColor(R.color.app_main_nav_color));
        setContentView(R.layout.activity_start);

        initPresenter();

        NotificationManagerCompat manager = NotificationManagerCompat.from(SelfApplication.getContext());
        boolean isOpened = manager.areNotificationsEnabled();
        L.print("StartActivity->", "isOPen=" + isOpened);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions();
        } else {
            redirectByTime();
        }
    }

    void initPresenter() {
        loginPresenter = new LoginPresenter();
        loginPresenter.autoLogin();
    }

    /**
     * 请求权限并初始化
     */
    private void requestPermissions() {

        // 增加權限
        int permission_write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission_camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permission_phone_status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permission_write != PackageManager.PERMISSION_GRANTED ||
                permission_read != PackageManager.PERMISSION_GRANTED ||
                permission_phone_status != PackageManager.PERMISSION_GRANTED ||
                permission_camera != PackageManager.PERMISSION_GRANTED
                ) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_PHONE_STATE},
                    100);
        } else {
            redirectByTime();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    redirectByTime();
                } else {
                    Toast.makeText(SelfApplication.getContext(), "请授权！", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 800);
                }
                return;
            }
        }
    }

    private Handler mHandler = new Handler();


    /**
     * 根据时间进行页面跳转
     */
    private void redirectByTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_TIME);
    }
}
