package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model;

public class CompleteDecorateOrderModelNet {

    private String DecorationOrderID;
    private boolean IsSuccessed;

    public boolean isSuccessed() {
        return IsSuccessed;
    }

    public void setSuccessed(boolean successed) {
        IsSuccessed = successed;
    }

    public String getDecorationOrderID() {
        return DecorationOrderID;
    }

    public void setDecorationOrderID(String decorationOrderID) {
        DecorationOrderID = decorationOrderID;
    }

    @Override
    public String toString() {
        return "CompleteDecorateOrderModelNet{" +
                "IsSuccessed=" + IsSuccessed +
                '}';
    }
}
