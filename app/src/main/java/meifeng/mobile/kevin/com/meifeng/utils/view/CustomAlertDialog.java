package meifeng.mobile.kevin.com.meifeng.utils.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.callback.CallBackForAlertListener;
import meifeng.mobile.kevin.com.meifeng.callback.CallBackGetInputTextListener;

/**
 * Created by kevin.w on 2018/4/5.
 */
public class CustomAlertDialog {

    Context ctx;
    AlertDialog ad;
    //TextView titleView;
    TextView messageView;
    LinearLayout buttonLayout;

    public CustomAlertDialog() {

    }

//    public CustomAlertDialog(Context ctx) {
//        this.ctx = ctx;
//        this.ad = new AlertDialog.Builder(ctx).create();
//        ad.show();
//        Window window = ad.getWindow();
//        window.setContentView(R.layout.custom_sure_alertdialog);
//        messageView = (TextView) window.findViewById(R.id.message);
//        buttonLayout = (LinearLayout) window.findViewById(R.id.buttonLayout);
//    }

    /*
    public void setTitle(int resId)
    {
        titleView.setText(resId);
    }
    public void setTitle(String title) {
        titleView.setText(title);
    }
    */
    public void setMessage(int resId) {
        messageView.setText(resId);
    }

    public void setMessage(String message) {
        messageView.setText(message);
    }

//    /**
//     * 设置按钮
//     *
//     * @param text
//     * @param listener
//     */
//    public void setPositiveButton(String text, final View.OnClickListener listener) {
//        Button button = new Button(ctx);
//        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        button.setLayoutParams(params);
//        button.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.btn_corners_gray));
//        button.setWidth(110);
//        button.setHeight(50);
//        button.setText(text);
//        button.setOnClickListener(listener);
//        buttonLayout.addView(button);
//    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
//    public void setNegativeButton(String text, final View.OnClickListener listener) {
//        Button button = new Button(ctx);
//        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        button.setLayoutParams(params);
//        button.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.btn_corners_blue));
//        button.setWidth(110);
//        button.setHeight(50);
//        button.setText(text);
//        button.setTextColor(Color.WHITE);
//        button.setOnClickListener(listener);
//        if (buttonLayout.getChildCount() > 0) {
//            params.setMargins(20, 0, 0, 0);
//            button.setLayoutParams(params);
//            buttonLayout.addView(button, 1);
//        } else {
//            button.setLayoutParams(params);
//            buttonLayout.addView(button);
//        }
//
//    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
//    public void setNegativeButton2(String text, final View.OnClickListener listener) {
//        Button button = new Button(ctx);
//        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        button.setLayoutParams(params);
//        button.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.btn_corners_blue));
//        button.setWidth(220);
//        button.setHeight(50);
//        button.setText(text);
//        button.setTextColor(Color.WHITE);
//        button.setOnClickListener(listener);
//        if (buttonLayout.getChildCount() > 0) {
//            params.setMargins(20, 0, 0, 0);
//            button.setLayoutParams(params);
//            buttonLayout.addView(button, 2);
//        } else {
//            button.setLayoutParams(params);
//            buttonLayout.addView(button);
//        }
//    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }

    public static void showAlertForTip(Context c, String message, final CallBackForAlertListener callBackForAlertListener) {
        AlertDialog.Builder b = new AlertDialog.Builder(c);
        b.setMessage(message);
        b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBackForAlertListener.completed();
            }
        });
        b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog dialog = b.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showAlertForWarningTip(Context c, String message) {
        AlertDialog.Builder b = new AlertDialog.Builder(c);
        b.setTitle("提醒");
        b.setMessage(message);
        b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        Dialog dialog = b.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showAlertForTip(Context c, String title, String message, final CallBackForAlertListener callBackForAlertListener) {
        AlertDialog.Builder b = new AlertDialog.Builder(c);
        b.setMessage(message);
        b.setTitle(title);
        b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBackForAlertListener.completed();
            }
        });
        b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog dialog = b.create();
        //dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // not outside dialog, and custom cancel & Ok title
    public static void showAlertOfAllAndNoOutSide(Context c, String title, String message, String buttonTitleCancel, String buttonTitleOk, final CallBackForAlertListener callBackForAlertListener) {
        AlertDialog.Builder b = new AlertDialog.Builder(c);
        AlertDialog dialog = b.setMessage(message).setTitle(title).setPositiveButton(buttonTitleOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callBackForAlertListener != null) {
                    callBackForAlertListener.completed(true);
                }
            }
        }).setNegativeButton(buttonTitleCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callBackForAlertListener != null) {
                    callBackForAlertListener.completed(false);
                }
            }
        }).create();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        }else{
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }

        //dialog.setCanceledOnTouchOutside(false);//点击屏幕不消失

        if (!dialog.isShowing()) {//此时提示框未显示
            //dialog.show();
            dialog.show();
        }
    }

//    public static void showDialogForEText(Context c, final String title, final CallBackGetInputTextListener callBack) {
//        final AlertDialog dialog = new AlertDialog.Builder(c).create();
//        dialog.setView(LayoutInflater.from(c).inflate(R.layout.layout_dialog_text_input, null));
//        dialog.show();
//        dialog.getWindow().setContentView(R.layout.layout_dialog_text_input);
//        Button btnPositive = (Button) dialog.findViewById(R.id.b_ok);
//        Button btnNegative = (Button) dialog.findViewById(R.id.b_cancel);
//        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
//        tvTitle.setText(title);
//        final EditText etContent = (EditText) dialog.findViewById(R.id.et_input);
//        etContent.setText("");
//        etContent.setHint("quantity");
//        btnPositive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                String str = etContent.getText().toString();
//                if (TextUtils.isEmpty(str)) {
//                    etContent.setError("Empty " + title);
//                } else if (str.equals("0")) {
//                    etContent.setError("Error text of 0");
//                }else if (str.length() > 999) {
//                    etContent.setError("Error length of input");
//                } else {
//                    dialog.dismiss();
//                    if (callBack != null) {
//                        callBack.completed(etContent.getText().toString());
//                    }
//                }
//            }
//        });
//
//        btnNegative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setCanceledOnTouchOutside(false);
//    }

    // alert list
    public static void showAlertForSelect(final String[] items, String title, Context context, final CallBackGetInputTextListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (listener != null) {
                    listener.completedForSelected(items[which], which);
                }

//                NbPoReceiveModel.PurchaseOrdersModel_ pm = items.get(which);
//
//                if (UtilsMethod.isEmptyString(pm.getStatus()+"")){
//                    Toast.makeText(context, "未預約", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(status)) {
//                    Toast.makeText(context, "未預約", Toast.LENGTH_SHORT).show();
//                } else {
//                    nextTodo(Integer.parseInt(pm.getStatus()), items[which], queueId);
//                    //nextTodo(Fields.INT_PORECEIVE_STATUS_DONE, items[which], queueId);
//                }
            }
        });
        builder.show();
    }

}
