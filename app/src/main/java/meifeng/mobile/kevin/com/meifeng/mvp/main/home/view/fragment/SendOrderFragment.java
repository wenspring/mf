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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.dialog.ActionAlertDialog;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.CancelDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.adapter.SendOrderListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.view.SendNewOrderActivity;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import okhttp3.Call;
import okhttp3.Response;

public class SendOrderFragment extends Fragment implements View.OnClickListener {

    private View baseView;
    private static Context context;
    private ListView lvSendOrder;
    RefreshLayout refreshLayout;

    ArrayList<DecorateOrderModel> list = new ArrayList<>();
    SendOrderListViewAdapter adapter;
    private Button btn_add_order;
    private int pageIndex = 1;
    public static SendOrderFragment instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseView = inflater.inflate(R.layout.fragment_new_order, container, false);
        instance = this;
        context = this.getActivity();
        setupUI();
        return baseView;
    }

    void setupUI() {
        findViewById();
        lvSendOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), DecorateOrderDetailActivity.class);
                intent.putExtra("k_decorate_model", list.get(position));
                intent.putExtra("k_is_my_send", true);
                startActivityForResult(intent, Fields.INT_REQUEST_RESULT_LOOK_DECORATE_DETAIL);
            }
        });

        getMyDecorateList(true);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                pageIndex = 1;
                getMyDecorateList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                pageIndex++;
                getMyDecorateList(false);
            }
        });
    }

    void findViewById() {
        lvSendOrder = baseView.findViewById(R.id.lv_new_order);
        btn_add_order = baseView.findViewById(R.id.btn_add_order);
        btn_add_order.setVisibility(View.VISIBLE);
        btn_add_order.setOnClickListener(this);

        refreshLayout = baseView.findViewById(R.id.refreshLayout);
        int color = getResources().getColor(R.color.app_main_nav_color);
        refreshLayout.setRefreshHeader(new WaterDropHeader(this.getActivity())).setPrimaryColors(color);
        refreshLayout.setRefreshFooter(new BallPulseFooter(this.getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    void initDecorateListAdapter() {
        adapter = new SendOrderListViewAdapter(context, list, new CallBackCancelDecorateOrder() {
            @Override
            public void cancelDecorateOrder(String id) {
                actionCancelOrder(id);
            }
        });
        lvSendOrder.setAdapter(adapter);
    }

    void actionCancelOrder(String id) {
        ActionAlertDialog actionAlertDialog = new ActionAlertDialog(this.getContext())
                .builder(false)
                .setTitle("提示")
                .setMsg("确定要取消发布?")
                .setCanceledOnTouchOutside(false)
                .setCancelable(true)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(id)) {
                            cancelDecorateOrder(id);
                        }
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        actionAlertDialog.show();
    }

    // 取消未付款的装修订单
    void cancelDecorateOrder(String id) {
        OkHttpApi.cancelMyNoPayDecorateOrder(id, new CallBackForOk<CancelDecorateOrderModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(CancelDecorateOrderModelNet response) {
                if (response.isSuccessed()) {
                    handler.sendMessageDelayed(handler.obtainMessage(2, "取消成功"), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, response.getRetrunMsg()), 0);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_order: {
                if (SelfApplication.isLogin()) {
                    // 判断是否是商户
                    if (SelfApplication.user.getUserInfoModel() != null) {
                        if (!SelfApplication.user.getUserInfoModel().isBusiness()) {
                            SelfApplication.showToastMessage("商户才可以委派装修任务哦");
                        } else if (Integer.parseInt(SelfApplication.user.getUserInfoModel().getUserStatus()) == 0) {
                            SelfApplication.showToastMessage("你的账号还在审核中哦");
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(context, SendNewOrderActivity.class);
                            startActivityForResult(intent, Fields.INT_REQUEST_RESULT_SEND_NEW_DECORATE_ORDER);
                        }
                    }
                }
            }
            break;
        }
    }

    // 我发布的装修订单
    public void getMyDecorateList(boolean isRefresh) {
        if (!SelfApplication.isLoginNow()) {
            return;
        }
        OkHttpApi.getMyDecorateList(pageIndex, Fields.INT_PAGE_SIZE_15, new CallBackForOk<ArrayList<DecorateOrderModel>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, ""), 0);
            }

            @Override
            public void onResponse(ArrayList<DecorateOrderModel> response) {
                if (response != null) {
                    if (isRefresh) {
                        list.clear();
                        list = response;
                    } else {
                        if (response.size() == 0) {
                            handler.sendMessageDelayed(handler.obtainMessage(-2, response), 0);
                        } else {
                            list.addAll(response);
                        }
                    }
                    handler.sendMessageDelayed(handler.obtainMessage(isRefresh?1:11, response), 0);
                }
            }
        });
    }

    public void clearList() {
        list.clear();
        adapter.notifyDataSetChanged();
    }

    public void isNeedRefresh(boolean mustRefresh) {
        if (mustRefresh) {
            getMyDecorateList(true);
        } else {
            if (list == null && list.size() == 0) {
                getMyDecorateList(true);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:

                    break;
                case 1:
                    if (msg.obj != null) {
                        initDecorateListAdapter();
                    }
                    break;
                case 11:
                    if (msg.obj != null) {
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 2:
                    if (msg.obj != null) {
                        Toast.makeText(getActivity(), msg.obj + "", Toast.LENGTH_SHORT).show();
                        //刷新数据
                        getMyDecorateList(true);
                    }
                    break;
            }
        }
    };

    public interface CallBackCancelDecorateOrder {
        void cancelDecorateOrder(String id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Fields.INT_REQUEST_RESULT_SEND_NEW_DECORATE_ORDER) {
            if (resultCode == 1) { // 发布新的装修订单成功了
                pageIndex = 1;
                getMyDecorateList(true); // 刷新列表数据
            }
        }
    }
}
