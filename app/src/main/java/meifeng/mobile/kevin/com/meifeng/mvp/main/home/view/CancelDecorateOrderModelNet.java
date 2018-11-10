package meifeng.mobile.kevin.com.meifeng.mvp.main.home.view;

public class CancelDecorateOrderModelNet {

    private String DecorationOrderID;
    private boolean IsSuccessed;
    private String RetrunMsg;

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

    public String getRetrunMsg() {
        return RetrunMsg;
    }

    public void setRetrunMsg(String retrunMsg) {
        RetrunMsg = retrunMsg;
    }

    @Override
    public String toString() {
        return "CancelDecorateOrderModelNet{" +
                ", IsSuccessed=" + IsSuccessed +
                ", RetrunMsg='" + RetrunMsg + '\'' +
                '}';
    }
}
