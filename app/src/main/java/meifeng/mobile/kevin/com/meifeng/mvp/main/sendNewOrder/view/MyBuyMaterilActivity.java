package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.dialog.ActionAlertDialog;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onFunctionListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.ShoppingMallActivity;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import okhttp3.Call;
import okhttp3.Response;

public class MyBuyMaterilActivity extends BaseBindActivity {

    ToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buy_materil);

        setupUI();

        //tipNoProductHadBuy();
        getNotUseProduct();

    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });
        toolBar.setFunctionDisplayable(true);
        toolBar.setFunctionImage(R.mipmap.icon_goods);
        toolBar.setFunctionListener(new onFunctionListener() {
            @Override
            public void onToolbarFunctionClick() {
                startActivity(ShoppingMallActivity.class, false);
            }
        });
    }

    void tipNoProductHadBuy() {
        ActionAlertDialog actionAlertDialog = new ActionAlertDialog(this)
                .builder(false)
                .setTitle("提示")
                .setMsg("你还没购买过材料，是否现在选购？")
                .setCanceledOnTouchOutside(false)
                .setCancelable(true)
                .setPositiveButton("选购", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(ShoppingMallActivity.class, false);

                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        actionAlertDialog.show();
    }

    // 获取没有被消耗的材料（商品)
    void getNotUseProduct() {
        showLoading("获取数据中...");
        OkHttpApi.getNotUseProduct(new CallBackForOk<Response>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(Response response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取数据失败"), 0);
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
                    dismissLoading();
                    showToastMsg("" + msg.obj);
                    break;
                case 1:
                    dismissLoading();
                    break;
            }
        }
    };
}