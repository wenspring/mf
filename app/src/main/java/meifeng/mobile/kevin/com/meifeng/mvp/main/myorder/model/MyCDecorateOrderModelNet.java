package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;

public class MyCDecorateOrderModelNet {

    private int Item1;

    private ArrayList<DecorateOrderModel> Item2;

    public int getItem1() {
        return Item1;
    }

    public void setItem1(int item1) {
        Item1 = item1;
    }

    public ArrayList<DecorateOrderModel> getItem2() {
        return Item2;
    }

    public void setItem2(ArrayList<DecorateOrderModel> item2) {
        Item2 = item2;
    }

    @Override
    public String toString() {
        return "MyCDecorateOrderModelNet{" +
                "Item1=" + Item1 +
                ", Item2=" + Item2 +
                '}';
    }
}
