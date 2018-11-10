package meifeng.mobile.kevin.com.meifeng.mvp.register.model;

public class RegisterModelNet {

    private boolean succeed;
    private String message;

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
        return "RegisterModelNet{" +
                "succeed=" + succeed +
                ", message='" + message + '\'' +
                '}';
    }
}
