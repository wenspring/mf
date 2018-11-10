package meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.base.BaseModelStatusNet;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.comment.adapter.ProductCommentActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.ShopActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view.CreateOrderActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.view.ShoppingCartActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import okhttp3.Call;

public class ProductDetailActivity extends BaseBindActivity implements View.OnClickListener {

    @BindView(id = R.id.function_back, click = true)
    private RelativeLayout btnBack;

    @BindView(id = R.id.function_favorite, click = true)
    private RelativeLayout btnFavorite;

    @BindView(id = R.id.function_car, click = true)
    private RelativeLayout btnCar;

    @BindView(id = R.id.iv_product_photo, click = true)
    private ImageView ivProductPhoto;

    @BindView(id = R.id.tv_product_name)
    private TextView tvProductName;

    @BindView(id = R.id.tv_product_price)
    private TextView tvProductPrice;

    @BindView(id = R.id.tv_product_score)
    private TextView tvProductScore;

    @BindView(id = R.id.btn_enter_shop, click = true)
    private Button btnEnterShop;

    @BindView(id = R.id.btn_contact, click = true)
    private Button btnContact;

    @BindView(id = R.id.btn_add_to_cart, click = true)
    private Button btnAddToCart;

    @BindView(id = R.id.btn_buy_now, click = true)
    private Button btnBuyNow;

    @BindView(id = R.id.btn_look_comemnt_list, click = true)
    private Button btnLookCommentList;

    @BindView(id = R.id.wv_product_content)
    private WebView wvProductContent;

    @BindView(id = R.id.tv_pro_brand_name)
    private TextView tvProBrandName;

    @BindView(id = R.id.tv_pro_weight)
    private TextView tvProWeight;

    @BindView(id = R.id.tv_pro_use)
    private TextView tvProUse;

    @BindView(id = R.id.tv_pro_size_color)
    private TextView tvProSizeColor;

    MallModel.productModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initData();
        setupUI();
        getProductDetail();
    }

    void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            productModel = (MallModel.productModel) intent.getSerializableExtra("k_product");
        }
    }

    void setupUI() {

        if (productModel != null) {
            tvProductName.setText(productModel.getName());
            tvProductPrice.setText("¥ " + NumberUtil.getNormalNumber(productModel.getPrice()) + "");
            Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + productModel.getThumbNail()).error(R.mipmap.img_small_def).into(ivProductPhoto);
            //tvProductScore.setText("5");
        }

        WebSettings webSettings = wvProductContent.getSettings();
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false);
        //调整图片至适合webview的大小
        //webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        //webSettings.setLoadWithOverviewMode(true);
        //设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.function_back: // 返回
                finish();
                break;
            case R.id.function_favorite:  // 收藏
                if (SelfApplication.isLogin()) {
                    addFavorite();
                }
                break;
            case R.id.function_car:  // 进入购物车
                if (SelfApplication.isLogin()) {
                    startActivity(ShoppingCartActivity.class, false);
                }
                break;
            case R.id.btn_enter_shop: // 进入店铺
                Intent intent = new Intent();
                intent.setClass(ProductDetailActivity.this, ShopActivity.class);
                intent.putExtra("k_shop_id", productModel.getShopID());
                startActivity(intent);
                break;
            case R.id.btn_look_comemnt_list: //查看评价
                Intent intent_pc = new Intent();
                intent_pc.setClass(ProductDetailActivity.this, ProductCommentActivity.class);
                intent_pc.putExtra("k_product_model", productModel);
                startActivity(intent_pc);
                break;
            case R.id.btn_add_to_cart: // 加入购物车
                if (SelfApplication.isLogin()) {
                    if (ConfigUtils.addProductToCart(productModel, 1)) {
                        showToastMsg("已添加到购物车");
                    } else {
                        showToastMsg("加入购物车失败");
                    }
                }
                break;
            case R.id.btn_buy_now: // 立即购买
                if (SelfApplication.isLogin()) {

                    ArrayList<ShoppingCartModel> listBuyOrder = new ArrayList<>();

                    productModel.setCountForCart(1);
                    ShoppingCartModel cartModel = new ShoppingCartModel();
                    cartModel.setDel(false);
                    cartModel.setProductModel(productModel);

                    L.print("listBuyOrder tempProductModel", productModel.toString());

                    listBuyOrder.add(cartModel);

                    Intent intentOrder = new Intent();
                    intentOrder.setClass(ProductDetailActivity.this, CreateOrderActivity.class);
                    intentOrder.putExtra("K_CART_LIST", (Serializable) listBuyOrder);
                    startActivity(intentOrder);

                }
                break;
        }
    }

    // 加入收藏操作
    void addFavorite() {
        showLoading("提交中...");
        OkHttpApi.addFavoriteForProduct(productModel.getID(), new CallBackForOk<BaseModelStatusNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(BaseModelStatusNet response) {
                if (response.getCode() == Fields.INT_HTTP_RESPONSE_SUCCESS) {
                    handler.sendMessageDelayed(handler.obtainMessage(2, "收藏成功"), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, response.getMsg()), 0);
                }
            }
        });
    }

    // 获取商品详情
    void getProductDetail() {
        OkHttpApi.getProductDetailByShopIdProductId(productModel.getShopId(), productModel.getId(), new CallBackForOk<MallModel.productModel>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(MallModel.productModel response) {
                //if (response.getCode() == Fields.INT_HTTP_RESPONSE_SUCCESS) {
                if (response != null) {
                    //handler.sendMessageDelayed(handler.obtainMessage(1, response.getData()), 0);
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取商品数据失败"), 0);
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
                        if (msg.obj instanceof MallModel.productModel) {
                            productModel = (MallModel.productModel) msg.obj;
                            initLayoutValue();
                        }
                    }
                    break;
                case 2:
                    dismissLoading();
                    showToastMsg("" + msg.obj);
                    break;
            }
        }
    };

    // 初始化显示数据
    void initLayoutValue() {
        // 加载商品图片显示
        //Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + "" + productModel.getBigPhoto()).error(R.mipmap.img_loading).into(ivProductPhoto);
        tvProductName.setText(productModel.getName() + "");
        tvProductPrice.setText("¥ " + NumberUtil.getNormalNumber(productModel.getPrice()) + "");

        tvProBrandName.setText(productModel.getClassificationName());
        tvProWeight.setText(productModel.getWeight() + "");
        tvProUse.setText(productModel.getUse());
        tvProSizeColor.setText(productModel.getSize() + "/" + productModel.getColor());

        try {
            String htmlData = URLDecoder.decode(productModel.getDetailDesc(), "UTF-8");
            L.print("html data", htmlData);
            wvProductContent.loadData(htmlData, "text/html; charset=UTF-8", null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            L.print("UnsupportedEncodingException", e.getMessage());
        }
    }
}