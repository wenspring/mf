package meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.dialog.ActionAlertDialog;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.ControlDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;
import okhttp3.Call;

public class DecorateOrderDetailActivity extends BaseBindActivity implements View.OnClickListener {

    @BindView(id = R.id.tv_create_user)
    private TextView tvCreateUser;

    @BindView(id = R.id.tv_create_time)
    private TextView tvCreateTime;

    @BindView(id = R.id.tv_done_date)
    private TextView tvDoneDateTime;

    @BindView(id = R.id.tv_title)
    private TextView tvTitle;

    @BindView(id = R.id.tv_price)
    private TextView tvPrice;

    @BindView(id = R.id.tv_content)
    private TextView tvContent;

    @BindView(id = R.id.wv_location)
    private WebView wvLocation;

    @BindView(id = R.id.tv_location)
    private TextView tvLocation;

    @BindView(id = R.id.btn_control, click = true)
    private Button buttonControl;

    private ToolBar toolBar;

    private boolean isMySendOrder;
    private DecorateOrderModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorate_order_detail);

        initData();

        setupUI();
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        if (model != null) {
            tvCreateUser.setText(model.getUserName());
            try {
                tvCreateTime.setText(UtilsMethod.getDateWithFormat("yyyy-MM-dd HH:mm:ss", model.getCreatedDate()));

                tvDoneDateTime.setText("要求完成日期:   " + UtilsMethod.getDateStrWithFormatNormal("yyyy-MM-dd HH:mm:ss", model.getDoneDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvTitle.setText(model.getTitle());

            float totalPrice = model.getWorkPrice();
            if (model.isHasOtherPrice()) { // 是否有感谢费
                totalPrice += model.getOtherPrice();
            }

            if (model.isHasOtherPrice()) {
                String otherPriceTemp = NumberUtil.getNormalNumber(model.getOtherPrice() + "");
                tvPrice.setText("¥ " + NumberUtil.getNormalNumber(totalPrice + "") + "" + "  (含感谢费:" + otherPriceTemp + ")" + "");
            } else {
                tvPrice.setText("¥ " + NumberUtil.getNormalNumber(totalPrice + ""));
            }

            tvContent.setText(model.getComment());

            tvLocation.setText("地点: " + model.getProvince() + "" + model.getCity() + "" + model.getStreet());

            initWebView();
        }

        if (isMySendOrder) {
            buttonControl.setVisibility(View.GONE);

            tvPrice.setText(model.getStatusDesc()+" - " + tvPrice.getText().toString());
        }
    }

    void initWebView() {
        WebSettings webSettings = wvLocation.getSettings();
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false);
        //调整图片至适合webview的大小
        //webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setAppCacheEnabled(true);
        //设置 缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);

        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);

        webSettings.setJavaScriptEnabled(true);


        wvLocation.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //String url = "http://api.map.baidu.com/geocoder?address=" + model.getProvince() + model.getCity() + model.getStreet() + "&output=html&src=webapp.baidu.openAPIdemo";
        //String url = "http://m.amap.com";
        String url = "file:///android_asset/map.html";
        wvLocation.loadUrl("" + url);
    }

    void initData() {
        if (getIntent() != null) {
            model = (DecorateOrderModel) getIntent().getSerializableExtra("k_decorate_model");
            isMySendOrder = getIntent().getBooleanExtra("k_is_my_send", false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_control: // 抢单

                ActionAlertDialog actionAlertDialog = new ActionAlertDialog(this)
                        .builder(false)
                        .setTitle("提示")
                        .setMsg("确定抢单？")
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(model.getID())) {
                                    controlDecorateOrder(model.getID());
                                }
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                actionAlertDialog.show();

                break;
        }
    }

    void controlDecorateOrder(String orderId) {

        OkHttpApi.controlDecorateOrder(orderId, new CallBackForOk<ControlDecorateOrderModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ControlDecorateOrderModelNet response) {
                if (response.getIsSuccessed() == 1) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, "已接单成功"), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "接单失败"), 0);
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
                    showToastMsg("接单成功");
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };
}
