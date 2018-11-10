package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.utils.view.DensityUtil;

public class PayPopwindow implements View.OnClickListener {

    private Activity context;
    CreateOrderActivity.CallBackForPayClickListener callBackForPayClickListener;
    private PopupWindow popupWindow;
    private RelativeLayout rlFullView;
    private PopupWindow.OnDismissListener popupDismissListener;
    private float amountTotal;

    public PayPopwindow(Activity c, RelativeLayout rlFullView, CreateOrderActivity.CallBackForPayClickListener callBackForPayClickListener, PopupWindow.OnDismissListener listener) {
        this.context = c;
        this.callBackForPayClickListener = callBackForPayClickListener;
        this.rlFullView = rlFullView;
        this.popupDismissListener = listener;
    }

    public PayPopwindow(Activity c, RelativeLayout rlFullView, CreateOrderActivity.CallBackForPayClickListener callBackForPayClickListener, PopupWindow.OnDismissListener listener, float amountTotal) {
        this.context = c;
        this.callBackForPayClickListener = callBackForPayClickListener;
        this.rlFullView = rlFullView;
        this.popupDismissListener = listener;
        this.amountTotal = amountTotal;
    }

    public PayPopwindow() {

    }

//    void PayPopwindow(Activity c, RelativeLayout rl, CreateOrderActivity.CallBackForPayClickListener callBackForPayClickListener, CreateOrderActivity.PopupDismissListener popupDismissListener) {
//        this.context = c;
//        this.callBackForPayClickListener = callBackForPayClickListener;
//        this.rlFullView = rl;
//        this.popupDismissListener = popupDismissListener;
//    }

//    void PayPopwindow(Activity c, RelativeLayout rl, CreateOrderActivity.CallBackForPayClickListener callBackForPayClickListener) {
//        this.context = c;
//        this.callBackForPayClickListener = callBackForPayClickListener;
//        this.rlFullView = rl;
//    }

    public void init() {
        final View popupWindowView = context.getLayoutInflater().inflate(R.layout.layout_pay_choose, null);
        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        Button btnClose = popupWindowView.findViewById(R.id.btn_close_choose);
        btnClose.setOnClickListener(this);
        Button btnPayNow = popupWindowView.findViewById(R.id.btn_pay_now);
        btnPayNow.setOnClickListener(this);
        TextView tvChoosePayTotal = popupWindowView.findViewById(R.id.tv_choos_total_pay);
        tvChoosePayTotal.setText(amountTotal + "");

        CheckBox ckWeixin = popupWindowView.findViewById(R.id.ck_weixin);
        CheckBox ckZhifubao = popupWindowView.findViewById(R.id.ck_zhifubao);
        ckWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ckZhifubao.setChecked(false);
                }
            }
        });

        ckZhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ckWeixin.setChecked(false);
                }
            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackForPayClickListener != null) {
                    if (ckZhifubao.isChecked() || ckWeixin.isChecked()) { //
                        callBackForPayClickListener.forPayChooseNew(ckWeixin.isChecked() ? "1" : "2"); // 支付宝
                        popupWindow.dismiss();
                    } else {
                        Toast.makeText(context, "请选择付款方式", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //动画效果
        //popupWindow.setAnimationStyle(R.style.AnimationLeftFade);

        popupWindow.showAtLocation(rlFullView, Gravity.BOTTOM, 0, DensityUtil.getStatusBarHeight(context));
        popupWindow.setClippingEnabled(false);

        //关闭事件
        popupWindow.setOnDismissListener(popupDismissListener);

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_choose:
                if (callBackForPayClickListener != null) {
                    callBackForPayClickListener.close();
                    popupWindow.dismiss();
                }

                break;
        }
    }

}
