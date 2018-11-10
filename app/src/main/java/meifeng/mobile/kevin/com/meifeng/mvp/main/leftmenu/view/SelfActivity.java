package meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.dialog.ActionAlertDialog;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.login.model.UserInfoModel;
import meifeng.mobile.kevin.com.meifeng.mvp.login.view.LoginActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.NewOrderFragment;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.SendOrderFragment;
import meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.adapter.MyInfoListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.SendOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import okhttp3.Call;

public class SelfActivity extends BaseBindActivity implements View.OnClickListener {

    private ToolBar toolBar;
    private ListView lvMyInfo;
    private MyInfoListViewAdapter adapter;
    private UserInfoModel userInfoModel = new UserInfoModel();

    private ImageView ivhead;
    private TextView tvUserName;
    private TextView tvStatus;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
        setupUI();
        getMyInfo();
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        lvMyInfo = findViewById(R.id.lv_my_info);
        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);

        final View headerView = View.inflate(this, R.layout.layout_myinfo_headerview, null);//头部内容,会隐藏的部分
        lvMyInfo.addHeaderView(headerView);

        ivhead = headerView.findViewById(R.id.iv_head);
        tvUserName = headerView.findViewById(R.id.tv_username);
        tvStatus = headerView.findViewById(R.id.tv_status);

        updateUserInfoList();
    }

    void updateUserInfoList() {
        adapter = new MyInfoListViewAdapter(SelfActivity.this, userInfoModel);
        lvMyInfo.setAdapter(adapter);
    }

    void setUserInfo() {
        tvUserName.setText(SelfApplication.user.getUsername());
    }

    void getMyInfo() {

        OkHttpApi.getMyInfo(new CallBackForOk<UserInfoModel>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(UserInfoModel response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    showToastMsg(msg.obj + "");
                    break;
                case 1:
                    if (msg.obj != null) {
                        userInfoModel = (UserInfoModel) msg.obj;
                        updateUserInfoList();

                        SelfApplication.user.setUserInfoModel(userInfoModel);

                        tvStatus.setText(userInfoModel.getUserStatusDesc() + "-" + (userInfoModel.isBusiness() == true ? "商家" : "用户"));
                        tvUserName.setText(userInfoModel.getName() + "");

                        Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + userInfoModel.getHead()).error(R.mipmap.icon_head_def).into(ivhead);
                    }

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                new ActionAlertDialog(this)
                        .builder(false)
                        .setTitle("提示")
                        .setMsg("确定要注销登录么?")
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SelfApplication.user = null;
                                ConfigUtils.saveUserInfo("", "");

                                // 清空我委派的订单
                                if (SendOrderFragment.instance != null) {
                                    SendOrderFragment.instance.clearList();
                                }
                                startActivity(LoginActivity.class, true);

                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                break;
        }
    }
}
