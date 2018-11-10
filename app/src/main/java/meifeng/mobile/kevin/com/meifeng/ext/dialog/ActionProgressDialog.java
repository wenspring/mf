package meifeng.mobile.kevin.com.meifeng.ext.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.R;


/**
 * 描述：自定义加载对话框
 */
public class ActionProgressDialog {

    public static Dialog mloadingDialog;

    public static void showProgressDialog(final Context context) {
        showProgressDialog(context, "请稍后...");
    }

    /**
     * 打开自定义进度条
     *
     * @param context
     * @param message
     * @return
     */
    public static void showProgressDialog(final Context context, String message) {
        if (mloadingDialog == null) {
            mloadingDialog = new Dialog(context, R.style.ProgressDialogStyle);
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.view_action_progress, new LinearLayout(context), false);
            RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);

            ImageView iv_img = (ImageView) v.findViewById(R.id.img);
            TextView tv_tip = (TextView) v.findViewById(R.id.tipTextView);

            // 加载动画
//            final AnimationDrawable animationDrawable = (AnimationDrawable) iv_img.getDrawable();
//            animationDrawable.start();
            tv_tip.setText(message);

            // 禁用返回键
            mloadingDialog.setCancelable(false);
            mloadingDialog.setCanceledOnTouchOutside(false);
            mloadingDialog.setContentView(layout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));// 设置布局
            mloadingDialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    //animationDrawable.stop();
                    removeProgressDialog(context);
                }
            });
            mloadingDialog.show();
        }
    }

    public static Dialog getDialog(Context context, String message) {
        if (mloadingDialog == null) {
            mloadingDialog = new Dialog(context, R.style.ProgressDialogStyle);
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.view_action_progress, new LinearLayout(context), false);
            RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);


            TextView tv_tip = (TextView) v.findViewById(R.id.tipTextView);


            tv_tip.setText(message);

            // 禁用返回键
            mloadingDialog.setCancelable(false);
            mloadingDialog.setCanceledOnTouchOutside(false);
            mloadingDialog.setContentView(layout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));// 设置布局
            mloadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });

        }
        return mloadingDialog;
    }



    /**
     * 关闭自定义进度条
     *
     * @param context
     */
    public static void removeProgressDialog(Context context) {
        try {
            if (mloadingDialog != null) {
                mloadingDialog.dismiss();
                mloadingDialog = null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
