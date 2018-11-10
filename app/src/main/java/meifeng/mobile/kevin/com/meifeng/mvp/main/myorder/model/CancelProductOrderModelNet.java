package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model;

public class CancelProductOrderModelNet {
    private String ShoppingOrderID;
    private boolean IsSuccessed;
    private String RetrunMsg;

    public String getShoppingOrderID() {
        return ShoppingOrderID;
    }

    public void setShoppingOrderID(String shoppingOrderID) {
        ShoppingOrderID = shoppingOrderID;
    }

    public boolean isSuccessed() {
        return IsSuccessed;
    }

    public void setSuccessed(boolean successed) {
        IsSuccessed = successed;
    }

    public String getRetrunMsg() {
        return RetrunMsg;
    }

    public void setRetrunMsg(String retrunMsg) {
        RetrunMsg = retrunMsg;
    }

    @Override
    public String toString() {
        return "CancelProductOrderModelNet{" +
                "IsSuccessed=" + IsSuccessed +
                ", RetrunMsg='" + RetrunMsg + '\'' +
                '}';
    }
}
