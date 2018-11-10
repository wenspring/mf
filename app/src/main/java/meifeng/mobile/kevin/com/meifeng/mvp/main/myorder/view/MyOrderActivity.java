package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.MyOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.adapter.MyOrderListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.producOrdertDetailView.ProductOrderDetailActivity;
import okhttp3.Call;


// 装修订单， 商品订单
public class MyOrderActivity extends BaseBindActivity {

    private ToolBar toolBar;
    @BindView(id = R.id.lv_myorder)
    private ListView lvMyOrder;

    ArrayList<MyOrderModelNet> listOrders = new ArrayList<>();
    private MyOrderListViewAdapter adapterMyOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        setupUI();

        getMyProductOrder();
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        lvMyOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MyOrderActivity.this, ProductOrderDetailActivity.class);
                intent.putExtra("k_pro_order_info", (Serializable) listOrders.get(position));
                startActivityForResult(intent, Fields.INT_REQUEST_RESULT_CANCEL_PRODUCT_ORDER);
            }
        });
    }

    void initListAdapter() {
        adapterMyOrder = new MyOrderListViewAdapter(this, listOrders);
        lvMyOrder.setAdapter(adapterMyOrder);
    }

    void getMyProductOrder() {

        OkHttpApi.getMyProductOrderList(1, 15, new CallBackForOk<ArrayList<MyOrderModelNet>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ArrayList<MyOrderModelNet> response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取订单失败"), 0);
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
                    if (msg.obj != null) {
                        listOrders = (ArrayList<MyOrderModelNet>) msg.obj;
                        initListAdapter();

                        if (listOrders.size() == 0) {
                            showToastMsg("没有商品订单");
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Fields.INT_REQUEST_RESULT_CANCEL_PRODUCT_ORDER) { // 是取消订单的request , 或者是付款完成的回调
            if (resultCode == RESULT_OK) { // 返回数据成功(setResult)
                getMyProductOrder(); // 刷新数据
            }
        }
    }
}
