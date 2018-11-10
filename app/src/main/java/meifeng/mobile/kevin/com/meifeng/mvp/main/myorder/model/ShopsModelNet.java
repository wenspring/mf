package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopsModelNet implements Serializable {

    private long ID;
    private String Name;
    private List<ShopingOrderItemModelNet> ShopingOrderItemDtos = new ArrayList<>();

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<ShopingOrderItemModelNet> getShopingOrderItemDtos() {
        return ShopingOrderItemDtos;
    }

    public void setShopingOrderItemDtos(List<ShopingOrderItemModelNet> shopingOrderItemDtos) {
        ShopingOrderItemDtos = shopingOrderItemDtos;
    }

    @Override
    public String toString() {
        return "ShopsModelNet{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", ShopingOrderItemDtos=" + ShopingOrderItemDtos +
                '}';
    }
}
