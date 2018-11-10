package meifeng.mobile.kevin.com.meifeng.base;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.ext.customSwipeBack.SwipeBackLayout;
import meifeng.mobile.kevin.com.meifeng.ext.customSwipeBack.WaitingDialog;
import meifeng.mobile.kevin.com.meifeng.utils.view.LoadingDialog;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by kevin.w on 2018/4/1.
 */
public class SwipeBackActivity extends FragmentActivity {

    public final static int Waiting_Dialog = 0x000f;
    private SwipeBackLayout mSwipeBackLayout;
    public boolean isCancelTouch;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    protected LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mSwipeBackLayout.findViewById(id);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//		UserApplication.getInstance().finishActivity(this);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case Waiting_Dialog:
                dialog = new WaitingDialog(this, R.style.QuitDialogStyles);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.setContentView(R.layout.progress_dialog_layout);
                WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                p.width = WindowManager.LayoutParams.MATCH_PARENT;
                p.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(p);
                break;
        }
        return dialog;
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isCancelTouch){
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (UtilsMethod.isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }



}
