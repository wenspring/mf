package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model;

public class PayForDecorateModelNet {

    private float Money;
    private  String PayType;
    private String DecorationOrderID;

    private boolean IsSuccessed;
    private String ErrorMsg;

    public float getMoney() {
        return Money;
    }

    public void setMoney(float money) {
        Money = money;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getDecorationOrderID() {
        return DecorationOrderID;
    }

    public void setDecorationOrderID(String decorationOrderID) {
        DecorationOrderID = decorationOrderID;
    }

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
        return "PayForDecorateModelNet{" +
                "IsSuccessed=" + IsSuccessed +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                '}';
    }
}
