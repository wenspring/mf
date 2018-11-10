package meifeng.mobile.kevin.com.meifeng.ext.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.utils.view.DensityUtil;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

public class ActionSelectorDialog {

    public static boolean isShowing = false;

    private Context mContext;
    private Dialog mDialog;
    private LinearLayout ll_bg;
    private TextView tv_alertTitle;
    private EditText tv_alertMsg;
    private Button btn_neg;
    private Button btn_pos;
    private RadioGroup rg_content;
    private RadioButton rb_select1;
    private RadioButton rb_select2;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public ActionSelectorDialog(Context context) {
        this.mContext = context;
    }

    public ActionSelectorDialog builder(boolean isSysAlt, String rbStr1, String rbStr2, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_action_selector, new LinearLayout(mContext), false);

        // 获取自定义Dialog布局中的控件
        ll_bg = (LinearLayout) view.findViewById(R.id.ll_alert_bg);

        tv_alertTitle = (TextView) view.findViewById(R.id.txt_title);
        tv_alertTitle.setVisibility(View.GONE);

        tv_alertMsg = (EditText) view.findViewById(R.id.txt_msg);
        tv_alertMsg.setVisibility(View.GONE);

        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);

        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);

        rg_content = (RadioGroup) view.findViewById(R.id.rg_content);
        if (UtilsMethod.isEmptyString(rbStr1)|| UtilsMethod.isEmptyString(rbStr2)){
            rg_content.setVisibility(View.GONE);
            this.showMsg = true;
        }else{
            rg_content.setVisibility(View.VISIBLE);
            this.showMsg = false;
        }

        rb_select1 = (RadioButton) view.findViewById(R.id.rb_1);
        rb_select1.setText(rbStr1);

        rb_select2 = (RadioButton) view.findViewById(R.id.rb_2);
        rb_select2.setText(rbStr2);
        if (position==0){
            rb_select1.setChecked(true);
            rb_select2.setChecked(false);
        }else{
            rb_select1.setChecked(false);
            rb_select2.setChecked(true);
        }
        // 定义Dialog布局和参数
        mDialog = new Dialog(mContext, R.style.AlertDialogStyle);
        mDialog.setContentView(view);
        mDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    isShowing = false;
                }
                return false;
            }
        });

        if (isSysAlt) {
            // 设为任何界面下都可以显示的系统级Dialog
            mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }

        // 调整Dialog背景大小
        int width = DensityUtil.getWidthInPx(mContext);
        ll_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (width * 0.85), LayoutParams.WRAP_CONTENT));
        return this;
    }

    public ActionSelectorDialog setOnKeyListener(OnKeyListener mOnKeyListener) {
        mDialog.setOnKeyListener(mOnKeyListener);
        return this;
    }

//    public static void displayMsg(Context context, String msg) {
//        new ActionSelectorDialog(context).builder(false).setTitle(context.getResources().getString(R.string.system_message_tips)).setMsg(msg).setPositiveButton("Confirm", new OnEditSureClickListener() {
//            @Override
//            public void OnMsgReturn(String msg) {
//
//            }
//        }).show();
//    }

    public static void displayMsg(Context context, String msg,String rbstr1,String rbstr2,int position,OnEditSureClickListener mListener) {

        new ActionSelectorDialog(context).builder(false,rbstr1,rbstr2,position).setCanceledOnTouchOutside(false).setTitle(context.getResources().getString(R.string.system_message_tips)).setMsg(msg).setPositiveButton(context.getResources().getString(R.string.system_confirm), mListener).show();
    }

    public static void display(Context context, String msg, String rbstr1,String rbstr2,int position,OnEditSureClickListener listener) {
        new ActionSelectorDialog(context).builder(false,rbstr1,rbstr2,position).setTitle("").setMsg(msg).setPositiveButton(context.getResources().getString(R.string.system_confirm), listener).setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    public static void display(Context context, String msg, String rbstr1,String rbstr2,OnEditSureClickListener listener) {
        new ActionSelectorDialog(context).builder(false,rbstr1,rbstr2,0).setTitle("").setMsg(msg).setPositiveButton(context.getResources().getString(R.string.system_confirm), listener).setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }


    public static void display(Context context, String msg,String rbstr1,String rbstr2,int position, OnEditSureClickListener mPositiveListener, OnClickListener mNegativeListener) {
        new ActionSelectorDialog(context).builder(false,rbstr1,rbstr2,position).setTitle(context.getResources().getString(R.string.system_message_tips)).setMsg(msg).setPositiveButton(context.getResources().getString(R.string.system_confirm), mPositiveListener).setNegativeButton("Canael", mNegativeListener).show();
    }
    public static void display(Context context, String msg,String rbstr1,String rbstr2, OnEditSureClickListener mPositiveListener, OnClickListener mNegativeListener) {
        new ActionSelectorDialog(context).builder(false,rbstr1,rbstr2,0).setTitle(context.getResources().getString(R.string.system_message_tips)).setMsg(msg).setPositiveButton(context.getResources().getString(R.string.system_confirm), mPositiveListener).setNegativeButton("Canael", mNegativeListener).show();
    }
//    public static void displayExit(Context context) {
//        new ActionSelectorDialog(context)
//                .builder(false)
//                .setTitle(context.getResources().getString(R.string.system_message_tips))
//                .setMsg(context.getResources().getString(R.string.system_message_action_exit))
//                .setCanceledOnTouchOutside(false)
//                .setPositiveButton(context.getResources().getString(R.string.system_confirm), new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .setNegativeButton(context.getResources().getString(R.string.system_cancel), new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                }).show();
//    }

    public ActionSelectorDialog setTitle(String title) {
        this.showTitle = true;
        if ("".equals(title)) {
            tv_alertTitle.setText(mContext.getResources().getString(R.string.system_message_tips));
        } else {
            tv_alertTitle.setText(title);
        }
        return this;
    }

    public ActionSelectorDialog setMsg(String msg) {
       // this.showMsg = true;
        if ("".equals(msg)) {
            tv_alertMsg.setText("");
        } else {
            tv_alertMsg.setText(msg);
        }
        return this;
    }

    public ActionSelectorDialog setCancelable(boolean cancel) {
        this.mDialog.setCancelable(cancel);
        return this;
    }

    public ActionSelectorDialog setCanceledOnTouchOutside(boolean cancel) {
        this.mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ActionSelectorDialog setPositiveButton(String text, final OnEditSureClickListener listener) {
        this.showPosBtn = true;

        if ("".equals(text)) {
            btn_pos.setText(mContext.getResources().getString(R.string.system_confirm));
        } else {
            btn_pos.setText(text);
        }

        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_select1.isChecked()){
                    listener.OnMsgReturn(tv_alertMsg.getText().toString().trim(),0);
                }else if (rb_select2.isChecked()){
                    listener.OnMsgReturn(tv_alertMsg.getText().toString().trim(),1);
                }else{
                    listener.OnMsgReturn(tv_alertMsg.getText().toString().trim(),0);
                }

                isShowing = false;
                mDialog.dismiss();
            }
        });
        return this;
    }

    public ActionSelectorDialog setNegativeButton(String text, final OnClickListener listener) {
        this.showNegBtn = true;

        if ("".equals(text)) {
            btn_neg.setText(mContext.getResources().getString(R.string.system_cancel));
        } else {
            btn_neg.setText(text);
        }

        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                isShowing = false;
                mDialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            tv_alertTitle.setText(mContext.getResources().getString(R.string.system_message_tips));
            tv_alertTitle.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            tv_alertTitle.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            tv_alertMsg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText(mContext.getResources().getString(R.string.system_confirm));
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.selector_alertdialog_single);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isShowing = false;
                    mDialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.selector_alertdialog_right);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.selector_alertdialog_left);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.selector_alertdialog_single);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.selector_alertdialog_single);
        }
    }

    public void show() {

        isShowing = true;

        setLayout();

        mDialog.show();
    }

    public interface OnEditSureClickListener {
       void OnMsgReturn(String msg, int position);
    }
}
