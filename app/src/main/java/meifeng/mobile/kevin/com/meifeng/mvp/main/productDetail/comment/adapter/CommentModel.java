package meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.comment.adapter;

public class CommentModel {

    private String ID;
    private String CreatedDate;
    private String UserID;
    private String UserHead;
    private String UserName;
    private String OrderID;
    private String ProductID;
    private String Source;
    private String Comment;
    private String Type;

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

    public String getUserHead() {
        return UserHead;
    }

    public void setUserHead(String userHead) {
        UserHead = userHead;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "ID='" + ID + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UserHead='" + UserHead + '\'' +
                ", UserName='" + UserName + '\'' +
                ", OrderID='" + OrderID + '\'' +
                ", ProductID='" + ProductID + '\'' +
                ", Source='" + Source + '\'' +
                ", Comment='" + Comment + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
