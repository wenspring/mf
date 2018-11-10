package meifeng.mobile.kevin.com.meifeng.mvp.main.shop;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public class ShopProductDetailNet {

    private int code;
    private String msg;
    private ArrayList<MallModel.productModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<MallModel.productModel> getData() {
        return data;
    }

    public void setData(ArrayList<MallModel.productModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShopProductDetailNet{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
