package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onFunctionListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.ProductDetailActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view.CreateOrderActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;

public class ShoppingCartActivity extends BaseBindActivity implements View.OnClickListener {

    ToolBar toolBar;
    private ListView lvShoppingCart;
    ShoppingCartListViewAdapter adapter;
    private ArrayList<ShoppingCartModel> list = new ArrayList<>();
    private boolean isDeleting;

    private TextView tvPayTotal;
    private Button btnPayNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

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
        toolBar.setFunctionDisplayable(true);
        toolBar.setFunctionImage(R.mipmap.icon_edit);
        toolBar.setFunctionListener(new onFunctionListener() {
            @Override
            public void onToolbarFunctionClick() {
                editCartList();
            }
        });

        tvPayTotal = findViewById(R.id.tv_pay_total);
        btnPayNow = findViewById(R.id.btn_pay_now);
        btnPayNow.setOnClickListener(this);
        lvShoppingCart = findViewById(R.id.lv_shopping_cart);
//        lvShoppingCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 取消编辑状态
//                if (isDeleting) {
//                    editCartList();
//                }
//                // 商品详情
//                Intent intent = new Intent();
//                intent.setClass(ShoppingCartActivity.this, ProductDetailActivity.class);
//                intent.putExtra("k_product", list.get(position).getProductModel());
//                startActivity(intent);
//
//            }
//        });

        // 加载购物车数据 （异步）
        new initCartThreadRun().run();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay_now:
                if (isDeleting) {
                    editCartList();//去掉delete status
                }
                if (SelfApplication.isLogin() && list != null && list.size() > 0) {
                    // 开始结算付款步骤
                    // 1. 提交订单 -(清空购物车信息)
                    // 2. 输入收货信息()地址, 联系方式等
                    // 3. 付款
                    Intent intent = new Intent();
                    intent.setClass(ShoppingCartActivity.this, CreateOrderActivity.class);
                    intent.putExtra("K_CART_LIST", (Serializable) list);
                    startActivityForResult(intent, Fields.INT_REQUEST_RESULT_CREATE_ORDER);
                }
                break;
        }
    }

    //初始化购物车数据
    class initCartThreadRun implements Runnable {
        @Override
        public void run() {
            // 获取本地缓存的数据
            if (ConfigUtils.getLocalCartList() != null) {
                ArrayList<MallModel.productModel> cartList = ConfigUtils.getLocalCartList();
                if (cartList != null && cartList.size() > 0) {

                    for (int i = 0; i < cartList.size(); i++) {
                        MallModel.productModel tempProductModel = cartList.get(i);
                        L.print("tempProductModel", tempProductModel.toString());
                        ShoppingCartModel cartModel = new ShoppingCartModel();
                        cartModel.setDel(false);
                        cartModel.setProductModel(tempProductModel);
                        list.add(cartModel);
                    }

                    if (list != null) {
                        handler.sendMessageDelayed(handler.obtainMessage(1, ""), 0);
                    }

                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "购物车空空的"), 0);
                }
            }
        }
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
                    initCartAdapter();
                    calculateTotal();
                    break;
                case 2: // 删除购物车商品,刷新列表
                    dismissLoading();
                    showToastMsg("" + msg.obj);
                    adapter.notifyDataSetChanged();
                    calculateTotal();
                    break;
            }
        }
    };

    void initCartAdapter() {
        adapter = new ShoppingCartListViewAdapter(this, list);
        lvShoppingCart.setAdapter(adapter);
    }

    void editCartList() {
        if (SelfApplication.user == null) {
            goLogin();
            return;
        }
        if (list != null && list.size() > 0) {
            isDeleting = !isDeleting;
            for (int i = 0; i < list.size(); i++) {
                ShoppingCartModel model = list.get(i);
                model.setDel(isDeleting);
                list.set(i, model);
            }
            adapter.notifyDataSetChanged();
        }
    }

    // 移除购物车商品
    void deleteItem(int position) {
        showLoading("正在删除...");
        new Runnable() {
            @Override
            public void run() {
                list.remove(position);
                boolean isSuccess = ConfigUtils.upateCartProduct(list);
                if (isSuccess) {
                    handler.sendMessageDelayed(handler.obtainMessage(2, "删除成功"), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "删除失败"), 0);
                }
            }
        }.run();
    }

    // 数量增加 删除
    void opCartProductCount(int opCount, int position) {
        ShoppingCartModel cartModel = list.get(position);
        MallModel.productModel productModel = cartModel.getProductModel();
        productModel.setCountForCart(productModel.getCountForCart() + opCount);
        boolean isSuccess = ConfigUtils.upateCartProduct(list);
        if (isSuccess) {
            adapter.notifyDataSetChanged();
            calculateTotal();
        }
    }

    // 统计总数
    void calculateTotal() {
        new Runnable() {
            @Override
            public void run() {
                float total = 0.0f;
                for (ShoppingCartModel model : list) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Fields.INT_REQUEST_RESULT_CREATE_ORDER) {
            if (resultCode == RESULT_OK) { // 清空购物车
                list.clear();
                tvPayTotal.setText("0.00");
                initCartAdapter();
            }
        }
    }
}