package meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model;

import java.util.PrimitiveIterator;

public class AddShopFavoriteModelNet {

    private String ShopID;
    private boolean IsSuccessed;

    public boolean isSuccessed() {
        return IsSuccessed;
    }

    public void setSuccessed(boolean successed) {
        IsSuccessed = successed;
    }

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String shopID) {
        ShopID = shopID;
    }

    @Override
    public String toString() {
        return "AddShopFavoriteModelNet{" +
                "IsSuccessed=" + IsSuccessed +
                '}';
    }
}
