package meifeng.mobile.kevin.com.meifeng.mvp.login.presenter;


import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.login.model.UserInfoModel;
import meifeng.mobile.kevin.com.meifeng.mvp.login.view.LoginActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.login.model.UserModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.NewOrderFragment;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.SendOrderFragment;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import okhttp3.Call;

public class LoginPresenter {

    private LoginActivity loginActivity;

    public LoginPresenter() {

    }

    public LoginPresenter(LoginActivity activity) {
        loginActivity = activity;
    }

    public void login(final String username, final String pwd) {
        OkHttpApi.login(username, pwd, new CallBackForOk<UserModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                if (loginActivity != null) {
                    loginActivity.mHandler.sendMessageDelayed(loginActivity.mHandler.obtainMessage(-1, e.getMessage()), 0);
                }
            }

            @Override
            public void onResponse(UserModelNet userModelNet) {
                if (userModelNet != null && userModelNet.isSucceed()) {

                    if (userModelNet.getData() != null) {

                        SelfApplication.user = userModelNet.getData();

                        ConfigUtils.saveUserInfo(username, pwd);

                        if (loginActivity != null) {
                            loginActivity.mHandler.sendEmptyMessage(1);
                        }

                        // 设置jPush 别名
                        if (SelfApplication.user != null && SelfApplication.user.getUserid() != null && !TextUtils.isEmpty(SelfApplication.user.getUserid())) {

                            // 获取用户信息
                            getUserInfo();

                            //setjPushAlias(SelfApplication.user.getUserid()+"");
                            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, SelfApplication.user.getUserid() + ""));

                            // 刷新主页数据
                            if (NewOrderFragment.instance != null) {
                                NewOrderFragment.instance.getNewOrder();
                            }

                            if (SendOrderFragment.instance != null) {
                                SendOrderFragment.instance.getMyDecorateList(true);
                            }

                        }
                    }
                } else {
                    loginActivity.mHandler.sendMessageDelayed(loginActivity.mHandler.obtainMessage(-1, userModelNet.getMessage()), 0);
                }
            }
        });
    }

    void getUserInfo() {

        OkHttpApi.getMyInfo(new CallBackForOk<UserInfoModel>() {
            @Override
            public void onFailure(Call call, Exception e) {
                //handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(UserInfoModel response) {
                if (response != null) {
                    //handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                    SelfApplication.user.setUserInfoModel(response);
                }
            }
        });

    }

    // auto login
    public void autoLogin() {
        String[] params = ConfigUtils.getUserNameAndPwd();
        if (params != null && params.length == 2) {
            login(params[0], params[1]);
        }
    }

    private final static String TAG = "JPUSH_UTIL";

    // Set jPush Alias
    public static void setjPushAlias(String alias) {

        // 调用 Handler 来异步设置别名

    }

    private static final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            //ExampleUtil.showToast(logs, getApplicationContext());
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(SelfApplication.getContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}
