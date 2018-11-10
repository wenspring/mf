package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model;

public class MallModelNet {

    private MallModel data;
    private boolean succeed;
    private String message;

    public MallModel getData() {
        return data;
    }

    public void setData(MallModel data) {
        this.data = data;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MallModelNet{" +
                "data=" + data +
                ", succeed=" + succeed +
                ", message='" + message + '\'' +
                '}';
    }
}
