package meifeng.mobile.kevin.com.meifeng.mvp.main.home.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DecorateOrderModel implements Serializable {

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

    //
    private String ID;
    private String UserID;
    private String Status;
    private String StatusDesc;
    private boolean IsDeleted;
    private ArrayList<Object> DecorationOrderShopItemDtos = new ArrayList<>();
    private String CreatedDate;

    //
    private String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    private ArrayList<String> MaterialIds = new ArrayList<>();
    private ArrayList<String> Images = new ArrayList<>();

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

    public int getMaterialTotal() {
        return MaterialTotal;
    }

    public void setMaterialTotal(int materialTotal) {
        MaterialTotal = materialTotal;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusDesc() {
        return StatusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        StatusDesc = statusDesc;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public ArrayList<Object> getDecorationOrderShopItemDtos() {
        return DecorationOrderShopItemDtos;
    }

    public void setDecorationOrderShopItemDtos(ArrayList<Object> decorationOrderShopItemDtos) {
        DecorationOrderShopItemDtos = decorationOrderShopItemDtos;
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
        return "DecorateOrderModel{" +
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
                ", ID='" + ID + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Status='" + Status + '\'' +
                ", StatusDesc='" + StatusDesc + '\'' +
                ", IsDeleted=" + IsDeleted +
                ", DecorationOrderShopItemDtos=" + DecorationOrderShopItemDtos +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", MaterialIds=" + MaterialIds +
                ", Images=" + Images +
                '}';
    }
}

