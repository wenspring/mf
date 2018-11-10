package meifeng.mobile.kevin.com.meifeng.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindViewUtils;
import meifeng.mobile.kevin.com.meifeng.mvp.login.view.LoginActivity;
import meifeng.mobile.kevin.com.meifeng.utils.view.LoadingDialog;
import meifeng.mobile.kevin.com.meifeng.utils.view.ViewUtil;

public class BaseBindActivity extends Activity {

    protected LoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        BindViewUtils.initBindView(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        BindViewUtils.initBindView(this);
    }

    public void showLoading(String msg){
        dialog = ViewUtil.getLoadingDialog(msg==null?"处理中...":msg,this);
        dialog.show();
    }

    public void dismissLoading(){
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    public void startActivity(Class clazz,boolean isFinish) {
        startActivity(new Intent(this,clazz));
        if (isFinish) {
            finish();
        }
    }

    public void goLogin(){
        showLoading("请先登录");
        startActivity(LoginActivity.class, false);
    }

    public void showToastMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
