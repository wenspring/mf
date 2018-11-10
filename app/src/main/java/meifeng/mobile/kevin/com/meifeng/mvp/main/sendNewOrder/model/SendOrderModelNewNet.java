package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model;

public class SendOrderModelNewNet {

    private boolean Item1;
    private String Item2;
    private data Item3;

    public boolean isItem1() {
        return Item1;
    }

    public void setItem1(boolean item1) {
        Item1 = item1;
    }

    public String getItem2() {
        return Item2;
    }

    public void setItem2(String item2) {
        Item2 = item2;
    }

    public data getItem3() {
        return Item3;
    }

    public void setItem3(data item3) {
        Item3 = item3;
    }

    @Override
    public String toString() {
        return "SendOrderModelNewNet{" +
                "Item1=" + Item1 +
                ", Item2='" + Item2 + '\'' +
                ", Item3=" + Item3 +
                '}';
    }

    public class data {

        private String ID;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        @Override
        public String toString() {
            return "data{" +
                    "ID='" + ID + '\'' +
                    '}';
        }
    }
}
