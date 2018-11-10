package meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model;

import java.util.ArrayList;

public class FavoriteModelNet {

    private ArrayList<FavoriteModel> List = new ArrayList<>();

    public ArrayList<FavoriteModel> getList() {
        return List;
    }

    public void setList(ArrayList<FavoriteModel> list) {
        List = list;
    }

    @Override
    public String toString() {
        return "FavoriteModelNet{" +
                "List=" + List +
                '}';
    }
}
