package meifeng.mobile.kevin.com.meifeng.ext.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by kevin.w on 2018/4/9.
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mTabFragments;
    private List<String> mTabIndicators;

    public ContentPagerAdapter(FragmentManager fm, List<Fragment> mTabFragments, List<String> mTabIndicators) {
        super(fm);
        this.mTabFragments = mTabFragments;
        this.mTabIndicators = mTabIndicators;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabIndicators.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabIndicators.get(position);
    }

    // 防止Fragment自动销毁
    @Override
    public void destroyItem(View container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
