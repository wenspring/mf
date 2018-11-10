package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.presenter;

import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.ShoppingMallActivity;
import okhttp3.Call;
import okhttp3.Response;

public class MallPresenter {

    ShoppingMallActivity mallActivity;

    public MallPresenter() {

    }

    public MallPresenter(ShoppingMallActivity mallActivity) {
        this.mallActivity = mallActivity;
        initData();
    }

    void initData() {

    }

    public void getHomeList(int productStart, int productPageSize, int classStart, int classPageSize) {
        OkHttpApi.homeList(productStart + "", productPageSize + "", classStart + "", classPageSize + "", new CallBackForOk<MallModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                mallActivity.handler.sendMessageDelayed(mallActivity.handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(MallModelNet response) {
                if (response.isSucceed()) {
                    mallActivity.handler.sendMessageDelayed(mallActivity.handler.obtainMessage(1, response.getData()), 0);
                }else {
                    mallActivity.handler.sendMessageDelayed(mallActivity.handler.obtainMessage(-1, response.getMessage()), 0);
                }
            }
        });
    }
}