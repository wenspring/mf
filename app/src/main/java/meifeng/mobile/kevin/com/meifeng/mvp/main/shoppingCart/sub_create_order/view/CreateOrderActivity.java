
package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.httpservice.requesthttp.OkhttpUtil;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.adapter.CreateOrderListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model.OrderSuccessModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model.ProductOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model.SubmitProOrderResponseModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import meifeng.mobile.kevin.com.meifeng.utils.view.DensityUtil;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import meifeng.mobile.kevin.com.meifeng.utils.view.ViewUtil;
import okhttp3.Call;
import okhttp3.Response;

public class CreateOrderActivity extends BaseBindActivity implements View.OnClickListener {

    private ToolBar toolBar;
    private ArrayList<ShoppingCartModel> listOrder = new ArrayList<>();
    private ListView lvOrderProduct;
    CreateOrderListViewAdapter adapter;

    private EditText etUserName, etMobile, etAddress;
    private TextView tvPayTotal;
    private Button btnPayNow;
    private RelativeLayout rlFullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        initData();
        setupUI();
    }

    void initData() {
        if (getIntent() != null) {
            listOrder = (ArrayList<ShoppingCartModel>) getIntent().getSerializableExtra("K_CART_LIST");
        }
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        etAddress = findViewById(R.id.et_address);
        etMobile = findViewById(R.id.et_mobile);
        etUserName = findViewById(R.id.et_username);
        tvPayTotal = findViewById(R.id.tv_pay_total);
        btnPayNow = findViewById(R.id.btn_pay_now);
        btnPayNow.setOnClickListener(this);
        rlFullView = findViewById(R.id.rl_full_view);

        lvOrderProduct = findViewById(R.id.lv_order_product);

        adapter = new CreateOrderListViewAdapter(CreateOrderActivity.this, listOrder);
        lvOrderProduct.setAdapter(adapter);

        // 计算总额
        calculateTotal();
    }

    // 提交订单
    void submitOrder(ProductOrderModel model) {
        showLoading("提交订单中...");
        OkHttpApi.submitProductOrder(model, new CallBackForOk<SubmitProOrderResponseModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(SubmitProOrderResponseModelNet response) {
                if (response.isItem1() == true) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response.getItem2().getID()), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "订单提交失败"), 0);
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
                    showToastMsg("订单提交成功，请完成付款");
                    ConfigUtils.clearCart(); //清空购物车
                    setResult(RESULT_OK);//刷新购物车列表
                    // 显示付款view
                    rlFullView.setVisibility(View.VISIBLE);
                    if (msg.obj != null) {
                        showPayView(msg.obj + "");
                    }
                    break;
                case 2:
                    dismissLoading();
                    showToastMsg("付款成功");
                    finish();
                    break;
            }
        }
    };

    // 统计总数
    float total = 0.0f;
    void calculateTotal() {
        new Runnable() {
            @Override
            public void run() {

                for (ShoppingCartModel model : listOrder) {
                    MallModel.productModel productModel = model.getProductModel();
                    if (productModel.getPrice() >= 0) {
                        total += productModel.getPrice() * productModel.getCountForCart();
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
        }.run();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay_now:
                String userName = etUserName.getText().toString();
                String mobile = etMobile.getText().toString();
                String address = etAddress.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    showToastMsg("请输入收件人姓名");
                } else if (TextUtils.isEmpty(mobile)) {
                    showToastMsg("请输入收件人联系电话");
                } else if (TextUtils.isEmpty(address)) {
                    showToastMsg("请输入收件人地址");
                } else {
                    ViewUtil.hideSoftKeyboard(this);
                    ProductOrderModel model = new ProductOrderModel();
                    model.setFlowAddress(address);
                    model.setReceiveMobile(mobile);
                    model.setReceiveUser(userName);

                    // items
                    ArrayList<ProductOrderModel.ItemModel> items = new ArrayList<>();
                    for (ShoppingCartModel shoppingCartModel : listOrder) {
                        MallModel.productModel productModel = shoppingCartModel.getProductModel();

                        ProductOrderModel.ItemModel itemModel = new ProductOrderModel().getItemModel();
                        itemModel.setComment("");
                        itemModel.setPrice(Float.parseFloat(productModel.getPrice() + ""));
                        itemModel.setProductID(productModel.getID());
                        itemModel.setProductName(productModel.getName());
                        itemModel.setQuantity(productModel.getCountForCart());

                        items.add(itemModel);
                    }
                    // 提交订单
                    model.setItems(items);
                    submitOrder(model);
                }
                break;
        }
    }


    // ***************** pay view **********************
    public interface CallBackForPayClickListener {
        public void forPayChoose();

        public void forPayChooseNew(String payType);

        public void close();
    }

    void showPayView(String orderId) {

        PopupDismissListener popupDismissListener = new PopupDismissListener();

        new PayPopwindow(this, rlFullView, new CallBackForPayClickListener() {
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
                    handler.sendMessageDelayed(handler.obtainMessage(2, ""), 0);
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
