package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class SendOrderModelNet {

    private int code;
    private String msg;
    private OrderModel data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderModel getData() {
        return data;
    }

    public void setData(OrderModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SendOrderModelNet{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public OrderModel getOrderModel() {
        return new OrderModel();
    }

    public class OrderModel {

        private String Title;
        private String Comment;
        private String DoneDate;
        private float WorkPrice;
        private boolean HasOtherPrice;
        private float OtherPrice;
        private boolean HasMaterial;
        private int MaterialTotal;
        private String Province;
        private String City;
        private String Street;

        private ArrayList<String> MaterialIds = new ArrayList<>();
        private ArrayList<String> Images = new ArrayList<>();

        public int getMaterialTotal() {
            return MaterialTotal;
        }

        public void setMaterialTotal(int materialTotal) {
            MaterialTotal = materialTotal;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getDoneDate() {
            return DoneDate;
        }

        public void setDoneDate(String doneDate) {
            DoneDate = doneDate;
        }

        public float getWorkPrice() {
            return WorkPrice;
        }

        public void setWorkPrice(float workPrice) {
            WorkPrice = workPrice;
        }

        public boolean isHasOtherPrice() {
            return HasOtherPrice;
        }

        public void setHasOtherPrice(boolean hasOtherPrice) {
            HasOtherPrice = hasOtherPrice;
        }

        public float getOtherPrice() {
            return OtherPrice;
        }

        public void setOtherPrice(float otherPrice) {
            OtherPrice = otherPrice;
        }

        public boolean isHasMaterial() {
            return HasMaterial;
        }

        public void setHasMaterial(boolean hasMaterial) {
            HasMaterial = hasMaterial;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String province) {
            Province = province;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String street) {
            Street = street;
        }

        public ArrayList<String> getMaterialIds() {
            return MaterialIds;
        }

        public void setMaterialIds(ArrayList<String> materialIds) {
            MaterialIds = materialIds;
        }

        public ArrayList<String> getImages() {
            return Images;
        }

        public void setImages(ArrayList<String> images) {
            Images = images;
        }

        @Override
        public String toString() {
            return "OrderModel{" +
                    "Title='" + Title + '\'' +
                    ", Comment='" + Comment + '\'' +
                    ", DoneDate='" + DoneDate + '\'' +
                    ", WorkPrice=" + WorkPrice +
                    ", HasOtherPrice=" + HasOtherPrice +
                    ", OtherPrice=" + OtherPrice +
                    ", HasMaterial=" + HasMaterial +
                    ", MaterialTotal=" + MaterialTotal +
                    ", Province='" + Province + '\'' +
                    ", City='" + City + '\'' +
                    ", Street='" + Street + '\'' +
                    ", MaterialIds=" + MaterialIds +
                    ", Images=" + Images +
                    '}';
        }
    }
}
