package meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model;

import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model.ShopModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public class FavoriteModel {

    private String ID;
    private String CreatedDate;
    private String UserID;
    private String UserName;
    private String ProductID;
    private String ProductName;
    private String ShopID;
    private String ShopName;
    private int CollectionType;

    //附加字段
    boolean isDel;

    private MallModel.productModel Product;
    private FavoriteShopModelNet Shop;

    public FavoriteShopModelNet getShop() {
        return Shop;
    }

    public void setShop(FavoriteShopModelNet shop) {
        Shop = shop;
    }

    public MallModel.productModel getProduct() {
        return Product;
    }

    public void setProduct(MallModel.productModel product) {
        Product = product;
    }


    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String shopID) {
        ShopID = shopID;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public int getCollectionType() {
        return CollectionType;
    }

    public void setCollectionType(int collectionType) {
        CollectionType = collectionType;
    }

    @Override
    public String toString() {
        return "FavoriteModel{" +
                "ID='" + ID + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UserName='" + UserName + '\'' +
                ", ProductID='" + ProductID + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", ShopID='" + ShopID + '\'' +
                ", ShopName='" + ShopName + '\'' +
                ", CollectionType=" + CollectionType +
                ", Shop=" + Shop +
                ", Product=" + Product +
                '}';
    }
}
