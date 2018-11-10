package meifeng.mobile.kevin.com.meifeng.mvp.login.model;

public class UserModelNet {

    private UserModel data;
    private boolean succeed;
    private String message;

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
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
        return "UserModelNet{" +
                "data=" + data +
                ", succeed=" + succeed +
                ", message='" + message + '\'' +
                '}';
    }
}
