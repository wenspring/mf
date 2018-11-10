package meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model;

public class ShopModel {

    private String Photo;
    private String Account;
    private String Password;
    private String Desc;
    private int Area;
    private String Position;
    private String IdCard;
    private String BankCard;
    private String BankCardPhoto;
    private String BusinessLicense;
    private boolean IsSure;
    private String BusinessHoursStar;
    private String BusinessHoursEnd;

    private int Sort;
    private String UserId;
    private String CreateTime;
    private int Type;
    private String DistributionFee;

    private String ChargingFee;
    private String Id;
    private String ID;
    private String Name;
    private String BossName;
    private String DetailDesc;
    private String City;
    private String Mobile;
    private int Size;
    private int CollectionCount;

    public int getCollectionCount() {
        return CollectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        CollectionCount = collectionCount;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getArea() {
        return Area;
    }

    public void setArea(int area) {
        Area = area;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getBankCard() {
        return BankCard;
    }

    public void setBankCard(String bankCard) {
        BankCard = bankCard;
    }

    public String getBankCardPhoto() {
        return BankCardPhoto;
    }

    public void setBankCardPhoto(String bankCardPhoto) {
        BankCardPhoto = bankCardPhoto;
    }

    public String getBusinessLicense() {
        return BusinessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        BusinessLicense = businessLicense;
    }

    public boolean isSure() {
        return IsSure;
    }

    public void setSure(boolean sure) {
        IsSure = sure;
    }

    public String getBusinessHoursStar() {
        return BusinessHoursStar;
    }

    public void setBusinessHoursStar(String businessHoursStar) {
        BusinessHoursStar = businessHoursStar;
    }

    public String getBusinessHoursEnd() {
        return BusinessHoursEnd;
    }

    public void setBusinessHoursEnd(String businessHoursEnd) {
        BusinessHoursEnd = businessHoursEnd;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDistributionFee() {
        return DistributionFee;
    }

    public void setDistributionFee(String distributionFee) {
        DistributionFee = distributionFee;
    }

    public String getChargingFee() {
        return ChargingFee;
    }

    public void setChargingFee(String chargingFee) {
        ChargingFee = chargingFee;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBossName() {
        return BossName;
    }

    public void setBossName(String bossName) {
        BossName = bossName;
    }

    public String getDetailDesc() {
        return DetailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        DetailDesc = detailDesc;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "ShopModel{" +
                "Photo='" + Photo + '\'' +
                ", Account='" + Account + '\'' +
                ", Password='" + Password + '\'' +
                ", Desc='" + Desc + '\'' +
                ", Area=" + Area +
                ", Position='" + Position + '\'' +
                ", IdCard='" + IdCard + '\'' +
                ", BankCard='" + BankCard + '\'' +
                ", BankCardPhoto='" + BankCardPhoto + '\'' +
                ", BusinessLicense='" + BusinessLicense + '\'' +
                ", IsSure=" + IsSure +
                ", BusinessHoursStar='" + BusinessHoursStar + '\'' +
                ", BusinessHoursEnd='" + BusinessHoursEnd + '\'' +
                ", Sort=" + Sort +
                ", UserId='" + UserId + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", Type=" + Type +
                ", DistributionFee='" + DistributionFee + '\'' +
                ", ChargingFee='" + ChargingFee + '\'' +
                ", Id='" + Id + '\'' +
                ", ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", BossName='" + BossName + '\'' +
                ", DetailDesc='" + DetailDesc + '\'' +
                ", City='" + City + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Size=" + Size +
                ", CollectionCount=" + CollectionCount +
                '}';
    }
}
