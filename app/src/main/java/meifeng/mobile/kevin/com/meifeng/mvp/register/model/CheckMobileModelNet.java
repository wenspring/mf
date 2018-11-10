package meifeng.mobile.kevin.com.meifeng.mvp.register.model;

public class CheckMobileModelNet {

    private boolean data;
    private boolean succeed;
    private String message;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
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
        return "CheckMobileModelNet{" +
                "data=" + data +
                ", succeed=" + succeed +
                ", message='" + message + '\'' +
                '}';
    }
}
