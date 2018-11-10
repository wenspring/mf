package meifeng.mobile.kevin.com.meifeng.mvp.login.model;

public class UserModel {

    private String token;
    private String username;
    private String userid;
    private String mobile;
    private String hader;
    private String role;

    private UserInfoModel userInfoModel;

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }

    public void setUserInfoModel(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHader() {
        return hader;
    }

    public void setHader(String hader) {
        this.hader = hader;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", hader='" + hader + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


}
