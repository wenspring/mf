package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.adapter.NewOrderListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.MyCDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.adapter.MyCDecorateOrderListViewAdapter;
import okhttp3.Call;

public class MyDecorateOrderActivity extends BaseBindActivity {

    private ToolBar toolBar;

    @BindView(id = R.id.lv_my_c_decorate_order)
    private ListView lvCMyDecorateOrder;

    MyCDecorateOrderListViewAdapter adapter;
    ArrayList<DecorateOrderModel> listMyCDecorates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorate_order);
        setupUI();
        getMyControlDecorateOrderList();
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        lvCMyDecorateOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MyDecorateOrderActivity.this, MyReceiptDecorateOrderActivity.class);
                intent.putExtra("k_decorate_order", listMyCDecorates.get(position));
                startActivityForResult(intent, 100);
            }
        });
    }

    void getMyControlDecorateOrderList() {

        OkHttpApi.getMyControlDecorateOrderList(50, 1, new CallBackForOk<MyCDecorateOrderModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(MyCDecorateOrderModelNet response) {
                if (response != null) {
                    if (response.getItem1() > 0) {
                        handler.sendMessageDelayed(handler.obtainMessage(1, response.getItem2()), 0);
                    } else {
                        handler.sendMessageDelayed(handler.obtainMessage(-1, "没有已接装修订单"), 0);
                    }
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取数据失败"), 0);
                }

            }
        });

    }

    // 初始化新订单adapter
    void initNewOrderAdapter() {
        if (listMyCDecorates != null) {
            adapter = new MyCDecorateOrderListViewAdapter(MyDecorateOrderActivity.this, listMyCDecorates);
            lvCMyDecorateOrder.setAdapter(adapter);
        }
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
                        listMyCDecorates = (ArrayList<DecorateOrderModel>) msg.obj;
                        initNewOrderAdapter();

                        if (listMyCDecorates.size() == 0) {
                            showToastMsg("没有订单");
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                getMyControlDecorateOrderList();
            }
        }
    }
}
