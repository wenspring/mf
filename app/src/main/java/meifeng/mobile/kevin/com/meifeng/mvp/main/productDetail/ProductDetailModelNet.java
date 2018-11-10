package meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail;

import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public class ProductDetailModelNet {

    private int code;
    private String msg;
    private MallModel.productModel data;

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

    public MallModel.productModel getData() {
        return data;
    }

    public void setData(MallModel.productModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProductDetailModelNet{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
