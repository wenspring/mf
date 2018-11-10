package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.VPageFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mSupportFragmentManager;
    private ArrayList<MallModel.CarouselModel> lists;
    private int mNum;
    private int mAllNum;

    public ViewPagerAdapter(FragmentManager fm, ArrayList lists, int num, int allNum) {
        super(fm);
        this.mSupportFragmentManager = fm;
        this.lists = lists;
        this.mNum = num;
        this.mAllNum = allNum;
    }

    @Override
    public Fragment getItem(int position) {
        MallModel.CarouselModel model = lists.get(position);

        //VPageFragment fragment = new VPageFragment();

        VPageFragment fragment = VPageFragment.newInstance(model.getPhoto(),model.getProductID());
        //fragment.newInstance(model.getPhoto());
//        Bundle b = new Bundle();
//        b.putString("pid",model.getProductID());
//        b.putString("imgKey",model.getPhoto());
//        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mNum;
    }
}
