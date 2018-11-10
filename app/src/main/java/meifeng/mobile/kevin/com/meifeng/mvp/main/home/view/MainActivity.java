package meifeng.mobile.kevin.com.meifeng.mvp.main.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.base.BaseFragmentActivity;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.dialog.ActionAlertDialog;
import meifeng.mobile.kevin.com.meifeng.ext.view.ContentPagerAdapter;
import meifeng.mobile.kevin.com.meifeng.ext.view.CustomViewPager;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.FavoriteActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.adapter.LeftMenuListItemAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.view.SelfActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.MyDecorateOrderActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.MyOrderActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.view.ShoppingCartActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.ShoppingMallActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.city.CityActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.NewOrderFragment;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.SendOrderFragment;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import meifeng.mobile.kevin.com.meifeng.utils.view.DensityUtil;
import meifeng.mobile.kevin.com.meifeng.utils.NetworkUtils;
import meifeng.mobile.kevin.com.meifeng.utils.view.TabUtil;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    @BindView(id = R.id.function_city, click = true)
    private RelativeLayout function_city;

    @BindView(id = R.id.function_me, click = true)
    private RelativeLayout function_me;

    @BindView(id = R.id.function_product_list, click = true)
    private RelativeLayout function_product;

    @BindView(id = R.id.view_full_show)
    private View viewFullShow;

    @BindView(id = R.id.tv_choose_city)
    private TextView tvChooseCity;

    NewOrderFragment newOrderFragment;
    SendOrderFragment sendOrderFragment;
    String[] tabTitles;

    private TextView tvUserName;
    private ListView lvLeftMenu;
    private LeftMenuListItemAdapter adapterLeftMenu;
    ArrayList<String> listLeftItem = new ArrayList<>();

    private TabLayout tl_bar;
    private CustomViewPager customViewPage;
    private List<String> mTabIndicators;
    private List<Fragment> mTabFragments;
    private ContentPagerAdapter mContentAdapter;
    PopupWindow popupWindow;
    ActionAlertDialog actionAlertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        setupUI();

        NetworkUtils.checkNetworkState(this);

        //SelfApplication.checkPermissions(this);
    }

    void setupUI() {
        setupTabLayout();
        viewFullShow.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.function_city:
                chooseCity();
                break;
            case R.id.function_me:
                if (SelfApplication.isLogin()) {
                    viewFullShow.setVisibility(View.VISIBLE);
                    initPopupWindow();
                }
                break;
            case R.id.function_product_list:
                Intent intent = new Intent();
                intent.setClass(this, ShoppingMallActivity.class);
                startActivity(intent);
                break;
        }
    }

    void chooseCity() {
        Intent intent = new Intent();
        intent.setClass(this, CityActivity.class);
        startActivityForResult(intent, Fields.INT_REQUEST_RESULT_CITY); // request code
    }

    public void initData() {
        tabTitles = getResources().getStringArray(R.array.tab_titles);
    }

    public void setupTabLayout() {

        tl_bar = (TabLayout) findViewById(R.id.tab_top_menu);
        customViewPage = (CustomViewPager) findViewById(R.id.vp_menu);
        customViewPage.setScanScroll(false); // 禁止滑动

        initTab();
        initContent();

        tl_bar.post(new Runnable() {
            @Override
            public void run() {
                TabUtil.reflex(tl_bar);
            }
        });
    }

    private void initTab() {
        tl_bar.setTabTextColors(ContextCompat.getColor(this, R.color.colorTabTextNormal), ContextCompat.getColor(this, R.color.colorTabIndicator));
        tl_bar.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorTabIndicator));
        ViewCompat.setElevation(tl_bar, 10);
        tl_bar.setupWithViewPager(customViewPage);
        tl_bar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {  // 选中
                int position = tab.getPosition();
                if (position == 1 && sendOrderFragment != null) {  // 刷新数据

                }
                if (position == 0 && newOrderFragment != null) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initContent() {
        mTabIndicators = new ArrayList<>();
        for (int i = 0; i < tabTitles.length; i++) {
            mTabIndicators.add(tabTitles[i]);
        }
        mTabFragments = new ArrayList<>();

        newOrderFragment = new NewOrderFragment();
        sendOrderFragment = new SendOrderFragment();

        mTabFragments.add(newOrderFragment);
        mTabFragments.add(sendOrderFragment);

        mContentAdapter = new ContentPagerAdapter(getSupportFragmentManager(), mTabFragments, mTabIndicators);
        customViewPage.setAdapter(mContentAdapter);
    }

    // for back result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Fields.INT_REQUEST_RESULT_CITY) { // 是选择城市的result
            if (resultCode == RESULT_OK) { // 返回数据成功(setResult)
                if (data != null) {
                    String resultCity = data.getStringExtra("k_city").toString();
                    //SelfApplication.setChooseCity(resultCity);
                    ConfigUtils.saveChooseCity(resultCity);
                    updateShowChooseCity();
                    // 刷新订单列表，根据选择的城市

                }
            }
        }
    }

    // 更显示城市
    void updateShowChooseCity() {
        String[] chooseCity = ConfigUtils.getChooseCity().split("-");
        if (chooseCity.length == 2) {
            tvChooseCity.setText(chooseCity[1]);
        }
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            viewFullShow.setVisibility(View.GONE);
        }
    }

    protected void initPopupWindow() {
        final View popupWindowView = getLayoutInflater().inflate(R.layout.app, null);
        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT, true);

        //动画效果
        popupWindow.setAnimationStyle(R.style.AnimationLeftFade);

        lvLeftMenu = popupWindowView.findViewById(R.id.lv_left_menu);
        tvUserName = popupWindowView.findViewById(R.id.tv_user_name);
        tvUserName.setText(SelfApplication.user.getUsername() + "");
        adapterLeftMenu = new LeftMenuListItemAdapter(this, listLeftItem);
        lvLeftMenu.setAdapter(adapterLeftMenu);
        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0: // 已接装修订单
                        intent.setClass(SelfApplication.getContext(), MyDecorateOrderActivity.class);
                        break;
                    case 1: // 商品订单
                        intent.setClass(SelfApplication.getContext(), MyOrderActivity.class);
                        break;
                    case 2: // 购物车
                        intent.setClass(SelfApplication.getContext(), ShoppingCartActivity.class);
                        break;
                    case 3: // 收藏
                        intent.setClass(SelfApplication.getContext(), FavoriteActivity.class);
                        break;
                    case 4: // 个人
                        intent.setClass(SelfApplication.getContext(), SelfActivity.class);
                        break;

                }
                startActivity(intent);

                popupWindow.dismiss();
                viewFullShow.setVisibility(View.GONE);
            }
        });

        ImageView ivHead = popupWindowView.findViewById(R.id.iv_user_head);
        Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + SelfApplication.user.getUserInfoModel().getHead()).error(R.mipmap.icon_head_def).into(ivHead);
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SelfActivity.class);
                startActivity(intent);

                popupWindow.dismiss();
                viewFullShow.setVisibility(View.GONE);
            }
        });

        Window w = getWindow();
        popupWindow.showAtLocation(w.getDecorView(), Gravity.NO_GRAVITY, 0, DensityUtil.getStatusBarHeight(this));
        popupWindow.setClippingEnabled(false);

        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());
        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            L.print("ss111s", actionAlertDialog.isShowing + "");
            if (actionAlertDialog != null && actionAlertDialog.isShowing) {
                return true;
            }

            new ActionAlertDialog(this)
                    .builder(false)
                    .setTitle("提示")
                    .setMsg("确定要退出APP?")
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(true)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();

            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(ConfigUtils.getChooseCity())) {
            actionAlertDialog = new ActionAlertDialog(this)
                    .builder(false)
                    .setTitle("城市")
                    .setMsg("当前城市: 广东省-东莞市")
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(true)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String defCity = "广东省-东莞市";
                            ConfigUtils.saveChooseCity("" + defCity);
                            updateShowChooseCity();
                        }
                    })
                    .setNegativeButton("切换", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chooseCity();
                        }
                    });
            actionAlertDialog.show();
        } else {
            updateShowChooseCity();
        }

    }
}

