package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseFragmentActivity;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.mvp.main.brand.BrandActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.ProductDetailActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.presenter.MallPresenter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.adapter.GridBrandListAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.adapter.GridProductListAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.view.ShoppingCartActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.adapter.ViewPagerAdapter;
import meifeng.mobile.kevin.com.meifeng.utils.view.ViewUtil;

public class ShoppingMallActivity extends BaseFragmentActivity implements View.OnClickListener {

    @BindView(id = R.id.gv_brand)
    private GridView gridViewBrand;

    @BindView(id = R.id.gv_product)
    private GridView gvProduct;

    @BindView(id = R.id.function_back, click = true)
    private RelativeLayout functionBack;

    @BindView(id = R.id.function_car, click = true)
    private RelativeLayout functionCar;

    @BindView(id = R.id.function_search, click = true)
    private RelativeLayout functionSearch;

    @BindView(id = R.id.top_content_view)
    private RelativeLayout topContentView;

    private MallPresenter presenter;

    private ViewPager mViewpager;
    private ViewPagerIndicator mIndicatorDefault;

    GridBrandListAdapter adapterBrand;
    GridProductListAdapter adapterProduct;

    public MallModel model = new MallModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        initPresenter();
        setupUI();
        presenter.getHomeList(1, 50, 1, 15);
    }

    void initPresenter() {
        presenter = new MallPresenter(this);
    }

    void setupUI() {
        setLvListener();
    }

    void initBrandAdapter() {
        // 品牌列表
        adapterBrand = new GridBrandListAdapter(ShoppingMallActivity.this, model.getClassifications());
        gridViewBrand.setAdapter(adapterBrand);
        gridViewBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("k_brand", model.getClassifications().get(position));
                intent.setClass(ShoppingMallActivity.this, BrandActivity.class);
                startActivity(intent);
            }
        });

        ViewUtil.setGridViewRowAndColumn(gridViewBrand, adapterBrand, this);
    }

    void initProductAdapter() {
        // 商品列表
        adapterProduct = new GridProductListAdapter(ShoppingMallActivity.this, model.getProductInfos());
        gvProduct.setAdapter(adapterProduct);
        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("k_product", model.getProductInfos().get(position));
                intent.setClass(ShoppingMallActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    void setViewPage() {

        mIndicatorDefault = findViewById(R.id.indicator_default);
        mViewpager = findViewById(R.id.viewpager_top);

        int allNum = 100000;
        final int num = model.getCarousels().size();  // 100/7=14..2    7*7=49  20/7=2..6   7   30/7=4..2    14   40/7=5..5   14
        int firstIndex = 0;
        if (num > 0) {
            firstIndex = allNum / num / 2 * num;
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), model.getCarousels(), num, allNum);
        mViewpager.setAdapter(adapter);
        mViewpager.setCurrentItem(firstIndex);

        mIndicatorDefault.setViewPager(mViewpager, num);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    showToastMsg(msg.obj + "");
                    break;
                case 1:
                    model = (MallModel) msg.obj;
                    if (model != null) {

                        if (model.getCarousels() != null && model.getCarousels().size() > 0) {
                            setViewPage(); // set view page
                        }
                        // 品牌
                        if (model.getClassifications() != null && model.getClassifications().size() > 0) {
                            initBrandAdapter();
                        }

                        // 商品
                        if (model.getProductInfos() != null && model.getProductInfos().size() > 0) {
                            initProductAdapter();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.function_back: {
                finish();
            }
            break;
            case R.id.function_car: {
                if (SelfApplication.isLogin()) {
                    startActivity(ShoppingCartActivity.class, false);
                }
            }
            break;
            case R.id.function_search: {
                startActivity(SearchProductActivity.class, false);
            }
            break;
        }
    }

    //监听listview 滚动事件
    private int cFirstVisibleItem;

    void setLvListener() {
        gvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {//滚动停止

                    if (cFirstVisibleItem > 2) {
                        topContentView.setVisibility(View.GONE);
                    } else {
                        topContentView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                cFirstVisibleItem = firstVisibleItem;
            }
        });

    }

}
