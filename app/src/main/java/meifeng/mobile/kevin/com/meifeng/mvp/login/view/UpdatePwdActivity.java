package meifeng.mobile.kevin.com.meifeng.mvp.login.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.mvp.register.RegisterActivity;

public class UpdatePwdActivity extends BaseBindActivity implements View.OnClickListener {

    @BindView(id = R.id.btn_submit, click = true)
    private Button btnSubmit;

    @BindView(id = R.id.rl_back, click = true)
    private RelativeLayout rlBack;

    @BindView(id = R.id.et_pwd)
    private EditText etPwd;

    @BindView(id = R.id.et_pwd_again)
    private EditText etPwdAgain;

    @BindView(id = R.id.tv_mobile)
    private TextView tvMobile;


    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);

        Intent intent = getIntent();
        if (intent != null) {
            mobile = intent.getStringExtra("k_mobile").toString();
        }
        setupUI();
    }

    void setupUI() {
        tvMobile.setText("手机号码："+ mobile);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit: { // 提交
                submit();
            }
            break;
            case R.id.rl_back: { // 返回
                finish();
            }
            break;
        }
    }

    void submit() {

        String pwd = etPwd.getText().toString();
        String pwdAgain = etPwdAgain.getText().toString();

        if (TextUtils.isEmpty(mobile)) {
            showToastMsg("请输入手机号码");
        } else if (TextUtils.isEmpty(pwd)) {
            showToastMsg("请输入新密码");
        } else if (TextUtils.isEmpty(pwdAgain)) {
            showToastMsg("请确认输入新密码");
        } else {
            // 提交
        }
    }

}
