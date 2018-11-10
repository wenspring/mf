package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model;

import java.util.ArrayList;

public class ProductOrderModel {

    private String FlowAddress;
    private String ReceiveUser;
    private String ReceiveMobile;
    private ArrayList<ItemModel> Items = new ArrayList<>();

    public String getFlowAddress() {
        return FlowAddress;
    }

    public void setFlowAddress(String flowAddress) {
        FlowAddress = flowAddress;
    }

    public String getReceiveUser() {
        return ReceiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        ReceiveUser = receiveUser;
    }

    public String getReceiveMobile() {
        return ReceiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        ReceiveMobile = receiveMobile;
    }

    public ArrayList<ItemModel> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ItemModel> items) {
        Items = items;
    }

    public ItemModel getItemModel() {
        return new ItemModel();
    }

    @Override
    public String toString() {
        return "ProductOrderMode{" +
                "FlowAddress='" + FlowAddress + '\'' +
                ", ReceiveUser='" + ReceiveUser + '\'' +
                ", ReceiveMobile='" + ReceiveMobile + '\'' +
                ", Items=" + Items +
                '}';
    }

    public class ItemModel {

        private float Price;
        private String ProductID;
        private String Comment;
        private int Quantity;
        private String ProductName;

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public float getPrice() {
            return Price;
        }

        public void setPrice(float price) {
            Price = price;
        }

        public String getProductID() {
            return ProductID;
        }

        public void setProductID(String productID) {
            ProductID = productID;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        @Override
        public String toString() {
            return "ItemModel{" +
                    "Price=" + Price +
                    ", ProductID='" + ProductID + '\'' +
                    ", Comment='" + Comment + '\'' +
                    ", Quantity=" + Quantity +
                    ", ProductName='" + ProductName + '\'' +
                    '}';
        }
    }

}
