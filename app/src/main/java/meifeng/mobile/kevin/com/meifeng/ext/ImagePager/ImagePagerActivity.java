package meifeng.mobile.kevin.com.meifeng.ext.ImagePager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.R;

/**
 * Created by Kevin.w on 2018/7/12.
 */

public class ImagePagerActivity extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private ImageViewPager mPager;

    private TextView tv_indicator;

    private int pagerPosition;
    private int isShowBottom = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);

        // 隐藏通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        String[] urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);

        isShowBottom = getIntent().getIntExtra("isShowBottom", 0);
        mPager = (ImageViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);

        mPager.setAdapter(mAdapter);
        tv_indicator = (TextView) findViewById(R.id.indicator);
        if (isShowBottom != 0) {
            tv_indicator.setVisibility(View.GONE);
        }

        CharSequence text = getString(R.string.img_vpager_indicator, 1, mPager.getAdapter().getCount());
        tv_indicator.setText(text);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.img_vpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                tv_indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }
        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public String[] fileList;

        public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.length;
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList[position];
            return ImageViewerFragment.newInstance(url);
        }

    }
}
