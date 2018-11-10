package meifeng.mobile.kevin.com.meifeng.mvp.main.shop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onFunctionListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.brand.BrandActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.ProductDetailActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.adapter.ShopBrandListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.adapter.ShopInfoListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model.AddShopFavoriteModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model.ShopModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.adapter.GridProductListAdapter;
import okhttp3.Call;
import okhttp3.Response;

public class ShopActivity extends BaseBindActivity implements View.OnClickListener {

    private ToolBar toolBar;
    private String shopId;

    private ListView lvShopBrand;
    ShopBrandListViewAdapter shopBrandListViewAdapter;
    private ArrayList<MallModel.brandModel> listBrands = new ArrayList<>();

    private ListView lvShopInfo;
    ShopInfoListViewAdapter shopInfoListViewAdapter;

    GridProductListAdapter adapterProduct;
    private GridView gvShopProduct;
    private ArrayList<MallModel.productModel> listProducts = new ArrayList<>();

    @BindView(id = R.id.iv_shop_logo, click = true)
    private ImageView ivShopLogo;

    @BindView(id = R.id.tv_shop_name, click = true)
    private TextView tvShopName;

    @BindView(id = R.id.rl_btn_brand, click = true)
    private RelativeLayout rlBtnBrand;

    @BindView(id = R.id.line_brand)
    private View lineBrand;

    @BindView(id = R.id.rl_btn_product, click = true)
    private RelativeLayout rlBtnProduct;

    @BindView(id = R.id.line_product)
    private View lineProduct;

    @BindView(id = R.id.rl_btn_introduce, click = true)
    private RelativeLayout rlBtnIntroduce;

    @BindView(id = R.id.line_introduce)
    private View lineIntroduce;

    @BindView(id = R.id.wv_shop_desc)
    private WebView wvShopDesc;
    //
    ShopModel shopModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initData();
        setupUI();

        getShopInfoList();// 店铺详情
        //getBrandInfoList();// 店铺品牌
        //getProductInfoList(); // 店铺商品
    }

    void initData() {
        if (getIntent() != null) {
            shopId = getIntent().getStringExtra("k_shop_id");
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
        toolBar.setFunctionDisplayable(true);
        toolBar.setFunctionImage(R.mipmap.icon_favorite);
        toolBar.setFunctionListener(new onFunctionListener() {
            @Override
            public void onToolbarFunctionClick() {
                if (SelfApplication.isLogin())
                    addFavoriteForShop();
            }
        });

        lvShopBrand = findViewById(R.id.lv_shop_brand);
        gvShopProduct = findViewById(R.id.gv_shop_product);
        lvShopInfo = findViewById(R.id.lv_shop_info);

        changeLineView(lineIntroduce, lvShopInfo, wvShopDesc); //显示商铺简介信息
    }

    // 店铺信息
    void getShopInfoList() {
        OkHttpApi.getShopInfoByShopId(shopId, new CallBackForOk<ShopModel>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ShopModel response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取数据失败"), 0);
                }
            }
        });
    }

    // 店铺下的品牌
    void getBrandInfoList() {
        OkHttpApi.getShopBrandListByShopId(shopId, new CallBackForOk<ArrayList<MallModel.brandModel>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ArrayList<MallModel.brandModel> response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(2, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取数据失败"), 0);
                }
            }
        });
    }

    // 店铺下的商品
    void getProductInfoList() {
        OkHttpApi.getShopProductListByShopId(shopId, "" + 1, "" + Fields.INT_PAGE_SIZE_15, new CallBackForOk<ArrayList<MallModel.productModel>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ArrayList<MallModel.productModel> response) {
                if (response != null) {
                    if (response.size() > 0) {
                        handler.sendMessageDelayed(handler.obtainMessage(3, response), 0);
                    } else {
                        handler.sendMessageDelayed(handler.obtainMessage(-1, "获取商品失败"), 0);
                    }
                }
            }
        });
    }

    // 收藏店铺
    void addFavoriteForShop() {
        OkHttpApi.addFavoriteForStop(shopId, new CallBackForOk<AddShopFavoriteModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(AddShopFavoriteModelNet response) {
                if (response != null) {
                    if (response.isSuccessed()) {
                        handler.sendMessageDelayed(handler.obtainMessage(4, "收藏成功"), 0);
                    } else {
                        handler.sendMessageDelayed(handler.obtainMessage(-1, "收藏成功"), 0);
                    }

                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "收藏失败"), 0);
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
                case 1: // 获取店铺数据
                    dismissLoading();
                    if (msg.obj != null) {
                        shopModel = (ShopModel) msg.obj;
                        initShopValueForUI();
                    }
                    break;
                case 2:// 获取品牌数据
                    if (msg.obj != null) {
                        listBrands = (ArrayList<MallModel.brandModel>) msg.obj;
                        initBrandListAdapter();
                    }
                    break;
                case 3: //商品
                    if (msg.obj != null) {
                        listProducts = (ArrayList<MallModel.productModel>) msg.obj;
                        initShopProductAdapter();
                    }
                    break;
                case 4:// 收藏店铺
                    if (msg.obj != null) {
                        showToastMsg("收藏成功");
                    }
                    break;
            }
        }
    };

    void initShopValueForUI() {
        // 显示店铺lOGO
        //Glide.with(ShopActivity.this).load(OkHttpApi.sevenNiuDomain + shopModel.getPhoto()).error(R.mipmap.img_loading).into(ivShopLogo);
        tvShopName.setText(shopModel.getName() + "");
        shopInfoListViewAdapter = new ShopInfoListViewAdapter(ShopActivity.this, shopModel);
        lvShopInfo.setAdapter(shopInfoListViewAdapter);

        wvShopDesc.loadDataWithBaseURL(null, shopModel.getDetailDesc(), "text/html", "UTF-8", null);
    }

    void initBrandListAdapter() {
        shopBrandListViewAdapter = new ShopBrandListViewAdapter(ShopActivity.this, listBrands);
        lvShopBrand.setAdapter(shopBrandListViewAdapter);
        lvShopBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("k_brand", listBrands.get(position));
                intent.setClass(ShopActivity.this, BrandActivity.class);
                startActivity(intent);
            }
        });
    }

    void initShopProductAdapter() {
        adapterProduct = new GridProductListAdapter(ShopActivity.this, listProducts);
        gvShopProduct.setAdapter(adapterProduct);
//        gvShopProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("k_product", listProducts.get(position));
//                intent.setClass(ShopActivity.this, ProductDetailActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_btn_brand:
                changeLineView(lineBrand, lvShopBrand, null);
                break;
            case R.id.rl_btn_product:
                changeLineView(lineProduct, gvShopProduct, null);
                break;
            case R.id.rl_btn_introduce:
                changeLineView(lineIntroduce, lvShopInfo, wvShopDesc);
                break;
        }
    }

    void changeLineView(View showView, View tagView, View tagWebView) {
        lineBrand.setVisibility(View.GONE);
        lineProduct.setVisibility(View.GONE);
        lineIntroduce.setVisibility(View.GONE);
        // show view
        showView.setVisibility(View.VISIBLE);

        lvShopInfo.setVisibility(View.GONE);
        lvShopBrand.setVisibility(View.GONE);
        gvShopProduct.setVisibility(View.GONE);
        //
        tagView.setVisibility(View.VISIBLE);

        if (tagWebView != null) {
            tagWebView.setVisibility(View.VISIBLE);
        } else {
            wvShopDesc.setVisibility(View.GONE);
        }

    }
}
