package meifeng.mobile.kevin.com.meifeng.mvp.login.model;

public class UserInfoModel {

    private String ID;
    private String Name;
    private String Mobile;
    private String FaceOfIDCard;
    private String BackOfIDCard;
    private boolean IsBusiness;
    private String BusinessLicense;
    private String Certificate;
    private String Password;
    private String Head;
    private String CompanyName;
    private String TelePhoneNumber;
    private String UserStatus;
    private String UserStatusDesc;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getFaceOfIDCard() {
        return FaceOfIDCard;
    }

    public void setFaceOfIDCard(String faceOfIDCard) {
        FaceOfIDCard = faceOfIDCard;
    }

    public String getBackOfIDCard() {
        return BackOfIDCard;
    }

    public void setBackOfIDCard(String backOfIDCard) {
        BackOfIDCard = backOfIDCard;
    }

    public boolean isBusiness() {
        return IsBusiness;
    }

    public void setBusiness(boolean business) {
        IsBusiness = business;
    }

    public String getBusinessLicense() {
        return BusinessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        BusinessLicense = businessLicense;
    }

    public String getCertificate() {
        return Certificate;
    }

    public void setCertificate(String certificate) {
        Certificate = certificate;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getTelePhoneNumber() {
        return TelePhoneNumber;
    }

    public void setTelePhoneNumber(String telePhoneNumber) {
        TelePhoneNumber = telePhoneNumber;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

    public String getUserStatusDesc() {
        return UserStatusDesc;
    }

    public void setUserStatusDesc(String userStatusDesc) {
        UserStatusDesc = userStatusDesc;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", FaceOfIDCard='" + FaceOfIDCard + '\'' +
                ", BackOfIDCard='" + BackOfIDCard + '\'' +
                ", IsBusiness=" + IsBusiness +
                ", BusinessLicense='" + BusinessLicense + '\'' +
                ", Certificate='" + Certificate + '\'' +
                ", Password='" + Password + '\'' +
                ", Head='" + Head + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", TelePhoneNumber='" + TelePhoneNumber + '\'' +
                ", UserStatus='" + UserStatus + '\'' +
                ", UserStatusDesc='" + UserStatusDesc + '\'' +
                '}';
    }
}
