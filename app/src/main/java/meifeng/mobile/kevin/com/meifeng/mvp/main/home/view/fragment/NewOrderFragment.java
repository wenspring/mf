package meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.callback.CallBackForAlertListener;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.dialog.ActionAlertDialog;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.httpservice.requesthttp.OkhttpUtil;
import meifeng.mobile.kevin.com.meifeng.mvp.login.view.LoginActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.ControlDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.adapter.NewOrderListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.utils.view.CustomAlertDialog;
import okhttp3.Call;
import okhttp3.Response;

public class NewOrderFragment extends Fragment {

    private View baseView;
    RefreshLayout refreshLayout;
    private static Context context;
    NewOrderListViewAdapter adapter;
    ArrayList<DecorateOrderModel> list = new ArrayList<>();
    private ListView lvNewOrder;
    public static NewOrderFragment instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseView = inflater.inflate(R.layout.fragment_new_order, container, false);
        context = this.getActivity();
        setupUI();

        instance = this;
        return baseView;
    }

    void setupUI() {
        findViewById();

        //initNewOrderAdapter();
        lvNewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (SelfApplication.isLogin()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DecorateOrderDetailActivity.class);
                    intent.putExtra("k_decorate_model", list.get(position));
                    startActivityForResult(intent, Fields.INT_REQUEST_RESULT_LOOK_DECORATE_DETAIL);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                getNewOrder();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

        getNewOrder();
    }

    // 初始化新订单adapter
    void initNewOrderAdapter() {
        adapter = new NewOrderListViewAdapter(context, list);
        lvNewOrder.setAdapter(adapter);
    }

    //抢单
    public void robbingNow(final String orderId) {
        if (SelfApplication.isLogin()) {

            if (Integer.parseInt(SelfApplication.user.getUserInfoModel().getUserStatus()) == 0) {
                Toast.makeText(getActivity(), "你的账号还在审核中哦", Toast.LENGTH_SHORT).show();
                return;
            }

            ActionAlertDialog actionAlertDialog = new ActionAlertDialog(this.getContext())
                    .builder(false)
                    .setTitle("提示")
                    .setMsg("确定抢单？")
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(true)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(orderId)) {
                                controlDecorateOrder(orderId);
                            }
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            actionAlertDialog.show();

//            CustomAlertDialog.showAlertForTip(context, "提醒", "确定抢单？", new CallBackForAlertListener() {
//                @Override
//                public void completed() {
//                    if (!TextUtils.isEmpty(orderId)) {
//                        controlDecorateOrder(orderId);
//                    }
//                }
//            });
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
                if (response.isItem1() == true) {
                    handler.sendMessageDelayed(handler.obtainMessage(2, "已接单成功"), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "接单失败"), 0);
                }
            }
        });
    }

    void findViewById() {
        lvNewOrder = baseView.findViewById(R.id.lv_new_order);
        refreshLayout = baseView.findViewById(R.id.refreshLayout);
        int color = getResources().getColor(R.color.app_main_nav_color);
        refreshLayout.setRefreshHeader(new WaterDropHeader(this.getActivity())).setPrimaryColors(color);
        refreshLayout.setRefreshFooter(new BallPulseFooter(this.getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    // 获取最新的装修订单
    public void getNewOrder() {
        OkHttpApi.getNewOrder(1, Fields.INT_PAGE_SIZE_15 + 50, new CallBackForOk<ArrayList<DecorateOrderModel>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ArrayList<DecorateOrderModel> response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取数据失败啦"), 0);
                }
            }
        });
    }

    public void isNeedRefresh(boolean mustRefresh) {
        if (mustRefresh) {
            getNewOrder();
        } else {
            if (list == null && list.size() == 0) {
                getNewOrder();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    Toast.makeText(getActivity(), "" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    if (msg.obj != null) {
                        list = (ArrayList<DecorateOrderModel>) msg.obj;
                        initNewOrderAdapter();
                    }
                    break;
                case 2:
                    if (msg.obj != null) {
                        Toast.makeText(getActivity(), "" + msg.obj, Toast.LENGTH_SHORT).show();
                        getNewOrder();
                    }
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Fields.INT_REQUEST_RESULT_LOOK_DECORATE_DETAIL) {
            if (resultCode == getActivity().RESULT_OK) {
                getNewOrder();// 刷新列表数据
            }
        }
    }
}