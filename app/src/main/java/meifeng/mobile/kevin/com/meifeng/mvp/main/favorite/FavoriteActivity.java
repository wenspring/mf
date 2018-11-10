package meifeng.mobile.kevin.com.meifeng.mvp.main.favorite;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.ext.view.ContentPagerAdapter;
import meifeng.mobile.kevin.com.meifeng.ext.view.CustomViewPager;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.adapter.FavoriteListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.fragment.CollectionProductFragment;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.fragment.CollectionShopFragment;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model.FavoriteModel;
import meifeng.mobile.kevin.com.meifeng.utils.view.TabUtil;

public class FavoriteActivity extends FragmentActivity {

    private ToolBar toolBar;
    FavoriteListViewAdapter adapter;
    ArrayList<FavoriteModel> list = new ArrayList<>();

    private ListView lvFavorite;
    private boolean isDeleting;

    CollectionShopFragment collectionShopFragment;
    CollectionProductFragment collectionProductFragment;
    String[] tabTitles;

    private TabLayout tl_bar;
    private CustomViewPager customViewPage;
    private List<String> mTabIndicators;
    private List<Fragment> mTabFragments;
    private ContentPagerAdapter mContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
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
//        toolBar.setFunctionDisplayable(true);
//        toolBar.setFunctionImage(R.mipmap.icon_edit);
//        toolBar.setFunctionListener(new onFunctionListener() {
//            @Override
//            public void onToolbarFunctionClick() {
//                //editList();
//            }
//        });

        setupTabLayout();
    }

    public void initData() {
        tabTitles = getResources().getStringArray(R.array.tab_collection_titles);
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
    }

    private void initContent() {
        mTabIndicators = new ArrayList<>();
        for (int i = 0; i < tabTitles.length; i++) {
            mTabIndicators.add(tabTitles[i]);
        }
        mTabFragments = new ArrayList<>();

        collectionProductFragment = new CollectionProductFragment();
        collectionShopFragment = new CollectionShopFragment();

        mTabFragments.add(collectionProductFragment);
        mTabFragments.add(collectionShopFragment);

        mContentAdapter = new ContentPagerAdapter(getSupportFragmentManager(), mTabFragments, mTabIndicators);
        customViewPage.setAdapter(mContentAdapter);
    }

}
