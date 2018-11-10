package meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model;

public class FavoriteShopModelNet {

    private String Photo;
    private String City;
    private String Name;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "FavoriteShopModelNet{" +
                "Photo='" + Photo + '\'' +
                ", City='" + City + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
