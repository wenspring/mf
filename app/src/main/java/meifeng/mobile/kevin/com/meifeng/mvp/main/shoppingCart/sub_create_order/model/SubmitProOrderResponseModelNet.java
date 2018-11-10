package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model;

import java.util.Date;

public class SubmitProOrderResponseModelNet {

    private boolean Item1;

    private Data Item2;

    public boolean isItem1() {
        return Item1;
    }

    public void setItem1(boolean item1) {
        Item1 = item1;
    }

    public Data getItem2() {
        return Item2;
    }

    public void setItem2(Data item2) {
        Item2 = item2;
    }

    @Override
    public String toString() {
        return "SubmitProOrderResponseModelNet{" +
                "Item1=" + Item1 +
                ", Item2=" + Item2 +
                '}';
    }

    public class Data {
        String ID;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "ID='" + ID + '\'' +
                    '}';
        }
    }
}
