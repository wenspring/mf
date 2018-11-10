package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model;

import java.io.Serializable;

public class ShopingOrderItemModelNet implements Serializable {

    private long ID;
    private long ProductID;
    private String ProductName;
    private String Comment;
    private int Price;
    private int Quantity;
    private String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getProductID() {
        return ProductID;
    }

    public void setProductID(long productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShopingOrderItemModelNet{" +
                "ID=" + ID +
                ", ProductID=" + ProductID +
                ", ProductName='" + ProductName + '\'' +
                ", Comment='" + Comment + '\'' +
                ", Image='" + Image + '\'' +
                ", Price=" + Price +
                ", Quantity=" + Quantity +
                '}';
    }
}
