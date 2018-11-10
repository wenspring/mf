package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.producOrdertDetailView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.dialog.ActionAlertDialog;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.httpservice.requesthttp.OkhttpUtil;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.CancelProductOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.MyOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.ShopingOrderItemModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model.OrderSuccessModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view.CreateOrderActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view.PayPopwindow;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import okhttp3.Call;
import okhttp3.Response;

public class ProductOrderDetailActivity extends BaseBindActivity implements View.OnClickListener {

    private ToolBar toolBar;
    private MyOrderDetailListViewAdapter adapter;

    @BindView(id = R.id.lv_my_order_detail)
    private ListView lvMyOrderDetail;

    @BindView(id = R.id.tv_order_receive_info)
    private TextView tvOrderReceiveInfo;

    @BindView(id = R.id.tv_pay_total)
    private TextView tvPayTotal;

    @BindView(id = R.id.btn_cancel_order, click = true)
    private Button btnCancelOrder;

    @BindView(id = R.id.btn_pay_now, click = true)
    private Button btnPayNow;

    @BindView(id = R.id.ll_flow, click = true)
    private LinearLayout llFlow;

    @BindView(id = R.id.ll_pay_content)
    private LinearLayout llPayContent;

    @BindView(id = R.id.btn_look_flow_info, click = true)
    private Button btnLookFlowInfo;


    @BindView(id = R.id.btm_sure_receipt, click = true)
    private Button btnSureReceipt;

    @BindView(id = R.id.rl_full_view)
    private RelativeLayout rlFullView;


    private MyOrderModelNet myOrderModelNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_order_detail);

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

        if (myOrderModelNet != null) {

            tvOrderReceiveInfo.setText("收货人：" + myOrderModelNet.getReceiveUser() + "\n" +
                    "联系电话：" + myOrderModelNet.getReceiveMobile() + "\n" +
                    "收货地址：" + myOrderModelNet.getFlowAddress() + "\n");

            adapter = new MyOrderDetailListViewAdapter(ProductOrderDetailActivity.this, myOrderModelNet.getShops());
            lvMyOrderDetail.setAdapter(adapter);

            llFlow.setVisibility(View.GONE);
            llPayContent.setVisibility(View.GONE);
            switch (myOrderModelNet.getStatus()) {
                case 0: // 已下单， 未付款
                    //llFlow.setVisibility(View.VISIBLE);
                    llPayContent.setVisibility(View.VISIBLE);
                    showToastMsg("订单还未付款");
                    calculateTotal(); // 统计订单总价，需要付款
                    break;
                case 1:// 已付款
                    showToastMsg("订单已付款，等待商家发货");
                    break;
                case 2: // 已发货
                    llFlow.setVisibility(View.VISIBLE);//显示物流查看
                    break;
                case 3:
                    showToastMsg("已确认收货，待评价");
                    break;
                case 5:// 已取消
                    toolBar.setTitle("订单-已取消");
                    break;
            }
        }
    }

    void initData() {
        if (getIntent() != null) {
            myOrderModelNet = (MyOrderModelNet) getIntent().getSerializableExtra("k_pro_order_info");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel_order: // 取消订单 (未付款才可以)
                ActionAlertDialog actionAlertDialog = new ActionAlertDialog(this)
                        .builder(false)
                        .setTitle("提示")
                        .setMsg("确定要取消订单?")
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (myOrderModelNet != null && !TextUtils.isEmpty(myOrderModelNet.getID() + "")) {
                                    String orderId = myOrderModelNet.getID() + "";
                                    cancelProductOrder(orderId);
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
            case R.id.btn_pay_now: // 付款
                if (myOrderModelNet.getStatus() == 0) { //未付款才需要付款
                    rlFullView.setVisibility(View.VISIBLE); // 显示付款view
                    showPayView(myOrderModelNet.getID() + "");
                }
                break;
            case R.id.btn_look_flow_info: // 查看物流信息
                Intent intent = new Intent();
                intent.setClass(ProductOrderDetailActivity.this, FlowInfoActivity.class);
                intent.putExtra("k_my_order", myOrderModelNet);
                startActivity(intent);
                break;
            case R.id.btm_sure_receipt: // 确认收货
            {
                ActionAlertDialog actionAlertDialogReceipt = new ActionAlertDialog(this)
                        .builder(false)
                        .setTitle("提示")
                        .setMsg("请确定已收到货品?")
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sureReceipt();
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                actionAlertDialogReceipt.show();
            }

            break;
        }
    }

    // 确认收货
    void sureReceipt() {
        showLoading("正在提交...");
        OkHttpApi.sureReceipt(myOrderModelNet.getID() + "", new CallBackForOk<CancelProductOrderModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(CancelProductOrderModelNet response) {
                if (response.isSuccessed()) {
                    handler.sendMessageDelayed(handler.obtainMessage(4, "收货成功"), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "收货失败"), 0);
                }
            }
        });

    }

    // 统计总数
    float total = 0.0f;

    void calculateTotal() {
        new Runnable() {
            @Override
            public void run() {
                if (myOrderModelNet.getShops() != null && myOrderModelNet.getShops().size() > 0) {
                    for (ShopingOrderItemModelNet model : myOrderModelNet.getShops().get(0).getShopingOrderItemDtos()) {
                        if (model.getPrice() >= 0) {
                            total += model.getPrice() * model.getQuantity();
                        }
                    }

                    float finalTotal = total;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvPayTotal.setText(NumberUtil.getNormalNumber(finalTotal + ""));
                        }
                    });
                }
            }
        }.run();
    }

    // 取消订单
    void cancelProductOrder(String orderId) {
        showLoading("取消订单中...");
        OkHttpApi.cancelMyNoPayProductOrder(orderId, new CallBackForOk<CancelProductOrderModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(CancelProductOrderModelNet response) {
                if (response != null) {
                    if (response.isSuccessed()) {
                        handler.sendMessageDelayed(handler.obtainMessage(1, "订单已取消"), 0);
                    }

                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "取消失败"), 0);
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
                case 1: //取消订单成功
                    dismissLoading();
                    if (msg.obj != null) {
                        showToastMsg("" + msg.obj);
                        finish();
                    }
                    break;
                case 2:
                    dismissLoading();
                    if (msg.obj != null) {

                    }
                    break;
                case 3:
                    dismissLoading();
                    showToastMsg("付款成功");
                    setResult(RESULT_OK);//通知刷新
                    finish();
                    break;
                case 4:
                    dismissLoading();
                    showToastMsg("确认收货成功");
                    setResult(RESULT_OK);//通知刷新
                    finish();
                    break;
            }
        }
    };

    // ***************** pay view **********************
    void showPayView(String orderId) {
        PopupDismissListener popupDismissListener = new PopupDismissListener();
        new PayPopwindow(this, rlFullView, new CreateOrderActivity.CallBackForPayClickListener() {
            @Override
            public void forPayChoose() {

            }

            @Override
            public void forPayChooseNew(String payType) {
                startPay(orderId, payType, total);
            }

            @Override
            public void close() {
                rlFullView.setVisibility(View.GONE);
                finish();

            }
        }, popupDismissListener, total).init();
    }

    void startPay(String orderId, String payType, float total) {
        showLoading("支付中...");
        OkHttpApi.payForProductByOrderId(orderId, total, payType, new CallBackForOk<OrderSuccessModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(OrderSuccessModelNet response) {
                if (response.isSuccessed() == true) {
                    handler.sendMessageDelayed(handler.obtainMessage(3, ""), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "付款失败"), 0);
                }
            }
        });

    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    public class PopupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            rlFullView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
