package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyOrderModelNet implements Serializable {

    private long ID;
    private String CreatedDate;
    private String FlowAddress;
    private String ReceiveUser;
    private String ReceiveMobile;
    private long UserID;
    private String UserName;
    private int Status;
    private String StatusDesc;
    private List<ShopsModelNet> Shops = new ArrayList<>();

    //快递物流信息
    private String FlowNumber;
    private String FlowCompany;
    private String FlowCompanyCode;

    public String getFlowNumber() {
        return FlowNumber;
    }

    public void setFlowNumber(String flowNumber) {
        FlowNumber = flowNumber;
    }

    public String getFlowCompany() {
        return FlowCompany;
    }

    public void setFlowCompany(String flowCompany) {
        FlowCompany = flowCompany;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

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

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getStatusDesc() {
        return StatusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        StatusDesc = statusDesc;
    }

    public List<ShopsModelNet> getShops() {
        return Shops;
    }

    public void setShops(List<ShopsModelNet> shops) {
        Shops = shops;
    }

    public String getFlowCompanyCode() {
        return FlowCompanyCode;
    }

    public void setFlowCompanyCode(String flowCompanyCode) {
        FlowCompanyCode = flowCompanyCode;
    }

    @Override
    public String toString() {
        return "MyOrderModelNet{" +
                "ID=" + ID +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", FlowAddress='" + FlowAddress + '\'' +
                ", ReceiveUser='" + ReceiveUser + '\'' +
                ", FlowNumber='" + FlowNumber + '\'' +
                ", FlowCompany='" + FlowCompany + '\'' +
                ", FlowCompanyCode='" + FlowCompanyCode + '\'' +
                ", ReceiveMobile='" + ReceiveMobile + '\'' +
                ", UserID=" + UserID +
                ", UserName='" + UserName + '\'' +
                ", Status=" + Status +
                ", StatusDesc='" + StatusDesc + '\'' +
                ", Shops=" + Shops +
                '}';
    }
}
