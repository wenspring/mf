package meifeng.mobile.kevin.com.meifeng.mvp.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.login.view.UpdatePwdActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.register.model.CheckMobileModelNet;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;
import okhttp3.Call;

public class RegisterActivity extends BaseBindActivity implements View.OnClickListener {

    @BindView(id = R.id.btn_reg_next, click = true)
    private Button btnRegNext;

    @BindView(id = R.id.rl_back, click = true)
    private RelativeLayout rlBack;

    @BindView(id = R.id.et_check_number)
    private EditText etCheckNumber;

    @BindView(id = R.id.et_mobile_number)
    private EditText etMobileNumber;

    private EventHandler handler;
    private boolean isUpdatePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        setupUI();
    }

    void initData() {
        if (getIntent() != null) {
            Intent i = getIntent();
            isUpdatePwd = i.getBooleanExtra("isUpdatePwd", false);
        }
    }

    void setupUI() {

        handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToastMsg("验证成功");
                                startNextAct(); // 开始执行注册资料的填写
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "验证码已发送,5分钟内有效!", Toast.LENGTH_SHORT).show();
                                etMobileNumber.setEnabled(false);
                                etCheckNumber.setVisibility(View.VISIBLE); // 显示输入验证码框
                                etCheckNumber.setEnabled(true);
                                btnRegNext.setText("提交验证");
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {

                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("error");
                        if (!TextUtils.isEmpty(des)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "提交错误信息," + des, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(handler);
    }

    void startNextAct() {
        if (!isUpdatePwd) { // 注册
            Intent intent = new Intent(RegisterActivity.this, RegisterBaseActivity.class);
            intent.putExtra("k_mobile", etMobileNumber.getText().toString());
            startActivity(intent);
        } else { // 修改密码
            Intent intent = new Intent(RegisterActivity.this, UpdatePwdActivity.class);
            intent.putExtra("k_mobile", etMobileNumber.getText().toString());
            startActivity(intent);
        }
        finish();
    }

    // 验证手机是否已经注册
    void checkMobileIsReg(String mobile) {

        OkHttpApi.checkMobileIsReg(mobile, new CallBackForOk<CheckMobileModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                myHandler.sendMessageDelayed(myHandler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(CheckMobileModelNet response) {
                if (response != null) {
                    if (response.isSucceed()) {
                        myHandler.sendMessageDelayed(myHandler.obtainMessage(1, response.isData()), 0);
                    } else {
                        myHandler.sendMessageDelayed(myHandler.obtainMessage(-1, "手机验证失败"), 0);
                    }
                }
            }
        });
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case -1:// 验证失败
                    showToastMsg("" + msg.obj);
                    break;
                case 1:// 注册情况返回成功
                    boolean isReg = (boolean) msg.obj;
                    if (!isUpdatePwd) { // 执行注册
                        if (isReg) {
                            showToastMsg("手机号码已注册过了");
                        } else {
                            // 手机可以注册，发送验证码
                            sendSMSMessage(etMobileNumber.getText().toString());
                        }
                    } else { // 执行修改密码
                        if (!isReg) {
                            showToastMsg("该手机号码未注册过，请核对是否输入正确");
                        } else {
                            startNextAct();//可修改
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg_next: {
                if (etCheckNumber.getVisibility() == View.GONE) { // 获取验证码
                    String mobileNumber = etMobileNumber.getText().toString();
                    if (TextUtils.isEmpty(mobileNumber)) {
                        etMobileNumber.setError("请输入手机号码");
                    } else if (!UtilsMethod.validatePhoneNumber(mobileNumber)) {
                        etMobileNumber.setError("请输入正确手机号码");
                    } else {
                        etMobileNumber.setError(null);
                        //sendSMSMessage(etMobileNumber.getText().toString());
                        //next();
                        checkMobileIsReg(etMobileNumber.getText().toString()); // 验证手机是否已经注册过
                    }
                } else {  // 提交验证码验证
                    String checkNumber = etCheckNumber.getText().toString();
                    if (TextUtils.isEmpty(checkNumber)) {
                        etCheckNumber.setError("请输入验证码");
                    } else if (checkNumber.length() != 4) {
                        etCheckNumber.setError("请输入正确格式验证码");
                    } else {
                        etCheckNumber.setError(null);
                        verificationMessage(etMobileNumber.getText().toString(), etCheckNumber.getText().toString());
                    }
                }
            }
            break;
            case R.id.rl_back: {
                finish();
            }
            break;
        }
    }

    void sendSMSMessage(String mobileNumber) {
        SMSSDK.getVerificationCode("86", "" + mobileNumber);
    }

    void verificationMessage(String mobileNumber, String checkNumber) {
        SMSSDK.submitVerificationCode("86", mobileNumber, checkNumber);
    }
}