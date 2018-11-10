package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model;

import java.io.Serializable;

import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public class ShoppingCartModel implements Serializable {

    private boolean isDel;

    private MallModel.productModel productModel;

    public MallModel.productModel getProductModel() {
        return productModel;
    }

    public void setProductModel(MallModel.productModel productModel) {
        this.productModel = productModel;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
