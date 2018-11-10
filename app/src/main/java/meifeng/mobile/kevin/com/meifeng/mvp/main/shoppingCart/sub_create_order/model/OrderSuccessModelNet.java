package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model;

public class OrderSuccessModelNet {

    private boolean IsSuccessed;
    private String ErrorMsg;

    public boolean isSuccessed() {
        return IsSuccessed;
    }

    public void setSuccessed(boolean successed) {
        IsSuccessed = successed;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "OrderSuccessModelNet{" +
                "IsSuccessed=" + IsSuccessed +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                '}';
    }
}
