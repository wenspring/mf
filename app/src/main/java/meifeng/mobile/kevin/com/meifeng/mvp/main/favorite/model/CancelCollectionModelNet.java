package meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model;

public class CancelCollectionModelNet {
    private String CollectionID;
    private boolean IsSuccessed;

    public boolean isSuccessed() {
        return IsSuccessed;
    }

    public void setSuccessed(boolean successed) {
        IsSuccessed = successed;
    }

    public String getCollectionID() {
        return CollectionID;
    }

    public void setCollectionID(String collectionID) {
        CollectionID = collectionID;
    }

    @Override
    public String toString() {
        return "CancelCollectionModelNet{" +
                "IsSuccessed=" + IsSuccessed +
                '}';
    }
}
