package meifeng.mobile.kevin.com.meifeng.mvp.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.mvp.login.presenter.LoginPresenter;
import meifeng.mobile.kevin.com.meifeng.mvp.register.RegisterActivity;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import meifeng.mobile.kevin.com.meifeng.utils.jPushUtil;

public class LoginActivity extends BaseBindActivity implements View.OnClickListener {

    @BindView(id = R.id.btn_login, click = true)
    private Button btnLogin;

    @BindView(id = R.id.tv_forget_pwd, click = true)
    private TextView tvForgetPwd;

    @BindView(id = R.id.tv_register, click = true)
    private TextView tvRegister;

    @BindView(id = R.id.rl_back, click = true)
    private RelativeLayout rlBack;

    @BindView(id = R.id.et_username)
    private EditText etUserName;

    @BindView(id = R.id.et_pwd)
    private EditText etPwd;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initPresenter();
        setupUI();
        //initData();
    }

    void setupUI() {

    }

    void initData() {
        String[] params = ConfigUtils.getUserNameAndPwd();
        if (params != null && params.length == 2) {
            etUserName.setText(params[0]);
            etPwd.setText(params[1]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.print("onResume", "onResume");
        initData();
    }

    void initPresenter() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: { // 登录
                startLogin();
            }
            break;
            case R.id.tv_forget_pwd: { // 忘记密码
                Intent i = new Intent();
                i.setClass(this, RegisterActivity.class);
                i.putExtra("isUpdatePwd", true);
                startActivity(i);
            }
            break;
            case R.id.tv_register: { // 注册
                startActivity(RegisterActivity.class, false);
            }
            break;
            case R.id.rl_back: { // 返回
                finish();
            }
            break;
        }
    }

    void startLogin() {
        String username = etUserName.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(username)) {
            showToastMsg("请输入手机号码");
        } else if (TextUtils.isEmpty(pwd)) {
            showToastMsg("请输入密码");
        } else {
            showLoading("正在登录...");
            loginPresenter.login(username, pwd);
        }
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1: {
                    dismissLoading();
                    showToastMsg(msg.obj + "");
                }
                break;
                case 1: {
                    dismissLoading();
                    showToastMsg("登录成功");
                    finish();
                }
                break;
                case 2:
                    if (SelfApplication.user.getUserid() != null && !TextUtils.isEmpty(SelfApplication.user.getUserid())) {
                        jPushUtil.setjPushAlias(SelfApplication.user.getUserid());
                    }
                    break;
            }
        }
    };

}
