package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model;

import android.graphics.Bitmap;

public class PlaceModel {

    private String imgPath;
    private String imgKey;
    private Bitmap imgBitmap;
    private boolean isAdd = false;

    public String getImgKey() {
        return imgKey;
    }

    public void setImgKey(String imgKey) {
        this.imgKey = imgKey;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }
}
