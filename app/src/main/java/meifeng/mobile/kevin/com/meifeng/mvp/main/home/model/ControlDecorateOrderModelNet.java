package meifeng.mobile.kevin.com.meifeng.mvp.main.home.model;

public class ControlDecorateOrderModelNet {
    private String DecorationOrderID;
    private int IsSuccessed;
    private boolean Item1;

    public boolean isItem1() {
        return Item1;
    }

    public void setItem1(boolean item1) {
        Item1 = item1;
    }

    public int getIsSuccessed() {
        return IsSuccessed;
    }

    public void setIsSuccessed(int isSuccessed) {
        IsSuccessed = isSuccessed;
    }

    public String getDecorationOrderID() {
        return DecorationOrderID;
    }

    public void setDecorationOrderID(String decorationOrderID) {
        DecorationOrderID = decorationOrderID;
    }

    @Override
    public String toString() {
        return "ControlDecorateOrderModelNet{" +
                "DecorationOrderID='" + DecorationOrderID + '\'' +
                ", Item1=" + Item1 +
                '}';
    }
}
