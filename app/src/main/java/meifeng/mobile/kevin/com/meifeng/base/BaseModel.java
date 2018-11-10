package meifeng.mobile.kevin.com.meifeng.base;

import java.io.Serializable;

import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public abstract class BaseModel implements Serializable{

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
        return "BaseModel{" +
                "data=" + data +
                ", succeed=" + succeed +
                ", message='" + message + '\'' +
                '}';
    }
}
