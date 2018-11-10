package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model;

import java.io.Serializable;
import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.base.BaseModel;

public class MallModel extends BaseModel implements Serializable {

    private int ClassTotal;
    private int ProductTotal;
    private ArrayList<CarouselModel> Carousels = new ArrayList<>(); // 輪播
    private ArrayList<brandModel> Classifications = new ArrayList<>(); // 品牌
    private ArrayList<productModel> ProductInfos = new ArrayList<>(); // 產品

    public int getClassTotal() {
        return ClassTotal;
    }

    public void setClassTotal(int classTotal) {
        ClassTotal = classTotal;
    }

    public int getProductTotal() {
        return ProductTotal;
    }

    public void setProductTotal(int productTotal) {
        ProductTotal = productTotal;
    }

    public ArrayList<CarouselModel> getCarousels() {
        return Carousels;
    }

    public void setCarousels(ArrayList<CarouselModel> carousels) {
        Carousels = carousels;
    }

    public ArrayList<brandModel> getClassifications() {
        return Classifications;
    }

    public void setClassifications(ArrayList<brandModel> classifications) {
        Classifications = classifications;
    }

    public ArrayList<productModel> getProductInfos() {
        return ProductInfos;
    }

    public void setProductInfos(ArrayList<productModel> productInfos) {
        ProductInfos = productInfos;
    }

    public productModel getProductModel() {
        return new productModel();
    }

    @Override
    public String toString() {
        return "MallModel{" +
                "ClassTotal=" + ClassTotal +
                ", ProductTotal=" + ProductTotal +
                ", Carousels=" + Carousels +
                ", Classifications=" + Classifications +
                ", ProductInfos=" + ProductInfos +
                '}';
    }

    public class CarouselModel implements Serializable {

        private String Id;
        private String Desc;
        private String Photo;
        private int Position;
        private int Sort;
        private String Link;
        private String ProductID;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String desc) {
            Desc = desc;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String photo) {
            Photo = photo;
        }

        public int getPosition() {
            return Position;
        }

        public void setPosition(int position) {
            Position = position;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int sort) {
            Sort = sort;
        }

        public String getLink() {
            return Link;
        }

        public void setLink(String link) {
            Link = link;
        }

        public String getProductID() {
            return ProductID;
        }

        public void setProductID(String productID) {
            ProductID = productID;
        }

        @Override
        public String toString() {
            return "CarouselModel{" +
                    "Id='" + Id + '\'' +
                    ", Desc='" + Desc + '\'' +
                    ", Photo='" + Photo + '\'' +
                    ", Position=" + Position +
                    ", Sort=" + Sort +
                    ", Link='" + Link + '\'' +
                    ", ProductID='" + ProductID + '\'' +
                    '}';
        }
    }

    public class brandModel implements Serializable {

        private String Id;
        private String Name;
        private String Photo;
        private int ParsentId;
        private int Sort;
        private String ShopId;
        private String Comment;

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

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String photo) {
            Photo = photo;
        }

        public int getParsentId() {
            return ParsentId;
        }

        public void setParsentId(int parsentId) {
            ParsentId = parsentId;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int sort) {
            Sort = sort;
        }

        public String getShopId() {
            return ShopId;
        }

        public void setShopId(String shopId) {
            ShopId = shopId;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        @Override
        public String toString() {
            return "brandModel{" +
                    "Id='" + Id + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Photo='" + Photo + '\'' +
                    ", ParsentId=" + ParsentId +
                    ", Sort=" + Sort +
                    ", ShopId='" + ShopId + '\'' +
                    ", Comment='" + Comment + '\'' +
                    '}';
        }
    }

    public class productModel implements Serializable {

        private String ID;
        private String Id;
        private String ClassId;
        private String SonClassId;
        private String ShopId;
        private String ShopID;
        private String Name;
        private String ThumbNail;
        private String BigPhoto;
        //private double DisplayPrice;
        private double Price;
        private String Unit;
        private String SimpleDesc;
        private String DetailDesc;
        private int Sort;
        private String SortName;
        private String SonSortName;
        private String ShopName;
        private String Stock;
        private String ClassificationName;//品牌
        private float Weight;//重量
        private String Use;//用途
        private String Size;//规格
        private String Color;//颜色

        //附加字段， 购物车对应的数量
        private int countForCart;


        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getCountForCart() {
            return countForCart;
        }

        public void setCountForCart(int countForCart) {
            this.countForCart = countForCart;
        }

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String classId) {
            ClassId = classId;
        }

        public String getSonClassId() {
            return SonClassId;
        }

        public void setSonClassId(String sonClassId) {
            SonClassId = sonClassId;
        }

        public String getShopId() {
            return ShopId;
        }

        public void setShopId(String shopId) {
            ShopId = shopId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getThumbNail() {
            return ThumbNail;
        }

        public void setThumbNail(String thumbNail) {
            ThumbNail = thumbNail;
        }

        public String getBigPhoto() {
            return BigPhoto;
        }

        public void setBigPhoto(String bigPhoto) {
            BigPhoto = bigPhoto;
        }


        public double getPrice() {
            return Price;
        }

        public void setPrice(double price) {
            Price = price;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String unit) {
            Unit = unit;
        }

        public String getSimpleDesc() {
            return SimpleDesc;
        }

        public void setSimpleDesc(String simpleDesc) {
            SimpleDesc = simpleDesc;
        }

        public String getDetailDesc() {
            return DetailDesc;
        }

        public void setDetailDesc(String detailDesc) {
            DetailDesc = detailDesc;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int sort) {
            Sort = sort;
        }

        public String getSortName() {
            return SortName;
        }

        public void setSortName(String sortName) {
            SortName = sortName;
        }

        public String getSonSortName() {
            return SonSortName;
        }

        public void setSonSortName(String sonSortName) {
            SonSortName = sonSortName;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String shopName) {
            ShopName = shopName;
        }

        public String getStock() {
            return Stock;
        }

        public void setStock(String stock) {
            Stock = stock;
        }

        public String getClassificationName() {
            return ClassificationName;
        }

        public void setClassificationName(String classificationName) {
            ClassificationName = classificationName;
        }

        public float getWeight() {
            return Weight;
        }

        public void setWeight(float weight) {
            Weight = weight;
        }

        public String getUse() {
            return Use;
        }

        public void setUse(String use) {
            Use = use;
        }

        public String getSize() {
            return Size;
        }

        public void setSize(String size) {
            Size = size;
        }

        public String getColor() {
            return Color;
        }

        public void setColor(String color) {
            Color = color;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getShopID() {
            return ShopID;
        }

        public void setShopID(String shopID) {
            ShopID = shopID;
        }

        @Override
        public String toString() {
            return "productModel{" +
                    "Id='" + Id + '\'' +
                    "ID='" + ID + '\'' +
                    "ShopID='" + ShopID + '\'' +
                    ", ClassId='" + ClassId + '\'' +
                    ", SonClassId='" + SonClassId + '\'' +
                    ", ShopId='" + ShopId + '\'' +
                    ", Name='" + Name + '\'' +
                    ", ThumbNail='" + ThumbNail + '\'' +
                    ", BigPhoto='" + BigPhoto + '\'' +
                    ", Price=" + Price +
                    ", Unit='" + Unit + '\'' +
                    ", SimpleDesc='" + SimpleDesc + '\'' +
                    ", DetailDesc='" + DetailDesc + '\'' +
                    ", Sort=" + Sort +
                    ", SortName='" + SortName + '\'' +
                    ", SonSortName='" + SonSortName + '\'' +
                    ", ShopName='" + ShopName + '\'' +
                    ", Stock='" + Stock + '\'' +
                    ", ClassificationName='" + ClassificationName + '\'' +
                    ", Weight=" + Weight +
                    ", Use='" + Use + '\'' +
                    ", Size='" + Size + '\'' +
                    ", Color='" + Color + '\'' +
                    '}';
        }
    }


}
