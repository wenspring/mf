package meifeng.mobile.kevin.com.meifeng.mvp.main.home.model;

public class NewOderModelNet {

    private int IsSuccessed;
    private String ReturnMsg;

    public int getIsSuccessed() {
        return IsSuccessed;
    }

    public void setIsSuccessed(int isSuccessed) {
        IsSuccessed = isSuccessed;
    }

    public String getReturnMsg() {
        return ReturnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        ReturnMsg = returnMsg;
    }

    @Override
    public String toString() {
        return "NewOderModelNet{" +
                "IsSuccessed=" + IsSuccessed +
                ", ReturnMsg='" + ReturnMsg + '\'' +
                '}';
    }
}
