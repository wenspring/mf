package meifeng.mobile.kevin.com.meifeng.utils.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import meifeng.mobile.kevin.com.meifeng.ext.Fields;

public class ViewUtil {

    public static void drawCornerView(View view, int radius, int backgroundColor, boolean[] needCorner) {
        int borderWidth = 0;// 加边框后会出现空心圆角矩形的效果，所以设置为0

        float[] outerRadius = new float[8];
        float[] innerRadius = new float[8];
        for (int i = 0; i < 8; i++) {
            if (needCorner[i / 2]) {
                outerRadius[i] = radius + borderWidth;
                innerRadius[i] = radius;
            }

        }
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    public static void setImageViewNormalShow(ImageView tagImageView, Context context) {
        int screenWidth = ViewUtil.getScreenWidth(context);
        ViewGroup.LayoutParams lp = tagImageView.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tagImageView.setLayoutParams(lp);
        tagImageView.setMaxWidth(screenWidth);
        tagImageView.setMaxHeight((int) (screenWidth * 5));
    }

    // get loading dialog
    public static LoadingDialog getLoadingDialog(String title, Context context) {
        LoadingDialog.Builder builder = new LoadingDialog.Builder(context).setMessage(title).setCancelable(true).setCancelOutside(false);
        return builder.create();
    }


    /**
     * hidden keyboard
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }


    //设置GridView 显示的行数 列数
    public static void setGridViewRowAndColumn(GridView gridView, BaseAdapter adapter, Context context) {
        int ROW = 2;
        int count = adapter.getCount();

        if (count <= 5) {
            ROW = 1;
        }

        int columns = (count % ROW == 0) ? count / ROW : count / ROW + 1;

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columns * dm.widthPixels / Fields.INT_GRID_VIEW_COLUMN,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(dm.widthPixels / Fields.INT_GRID_VIEW_COLUMN);
        gridView.setStretchMode(GridView.NO_STRETCH);

        if (count <= 5) {
            gridView.setNumColumns(count);
        } else {
            gridView.setNumColumns(columns);
        }

    }

}
