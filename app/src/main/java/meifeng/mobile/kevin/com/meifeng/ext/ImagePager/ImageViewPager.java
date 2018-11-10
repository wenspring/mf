package meifeng.mobile.kevin.com.meifeng.ext.ImagePager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Kevin on 2017/7/12.
 */

public class ImageViewPager extends ViewPager {

    private static final String TAG = ImageViewPager.class.getSimpleName();

    public ImageViewPager(Context context) {
        super(context);
    }

    public ImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "hacky viewpager error1"); // 忽略错误
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "hacky viewpager error2"); // 忽略错误
            return false;
        }
    }
}
