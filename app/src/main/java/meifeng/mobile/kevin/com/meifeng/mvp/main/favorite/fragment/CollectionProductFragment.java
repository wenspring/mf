package meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ShowableListMenu;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.brand.BrandActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.adapter.FavoriteListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model.CancelCollectionModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model.FavoriteModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model.FavoriteModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.ProductDetailActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import okhttp3.Call;
import okhttp3.Response;

public class CollectionProductFragment extends Fragment {

    private View baseView;
    private static Context context;

    FavoriteListViewAdapter adapter;
    ArrayList<FavoriteModel> list = new ArrayList<>();
    private ListView lvFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseView = inflater.inflate(R.layout.fragment_collect_product, container, false);
        context = this.getActivity();
        setupUI();

        getMyCollectionList();
        return baseView;
    }

    void setupUI() {
        lvFavorite = baseView.findViewById(R.id.lv_favorite);
        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FavoriteModel favoriteModel = list.get(position);
                MallModel.productModel model = new MallModel().getProductModel();

                model.setId(favoriteModel.getProductID());
                model.setPrice(favoriteModel.getProduct().getPrice());
                model.setName(favoriteModel.getProductName());
                model.setThumbNail(favoriteModel.getProduct().getThumbNail());
                Intent intent = new Intent();
                intent.putExtra("k_product", model);
                intent.setClass(getActivity(), ProductDetailActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(lvFavorite);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 1, 1, "取消收藏");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        long id = info.id;//当前项的key标示，独一无二的
        int position = info.position;//当前项在ListView中的位置

        if (!TextUtils.isEmpty(list.get(position).getID())) {
            cancelFavorite(list.get(position).getID());
        }
        return super.onContextItemSelected(item);
    }

    void cancelFavorite(String collectionId) {

        OkHttpApi.cancelCollection(collectionId, new CallBackForOk<CancelCollectionModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(CancelCollectionModelNet response) {
                if (response != null && response.isSuccessed()) {
                    handler.sendMessageDelayed(handler.obtainMessage(2, "取消收藏成功"), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "取消失败"), 0);
                }
            }
        });
    }

    void getMyCollectionList() {
        OkHttpApi.getMyCollectionList(null, 0 + "", 50 + "", 1 + "", new CallBackForOk<FavoriteModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(FavoriteModelNet response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response.getList()), 0);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:

                    break;
                case 1:
                    if (msg.obj != null) {
                        list = (ArrayList<FavoriteModel>) msg.obj;
                        if (list != null) {
                            initAdapterForList();
                        }
                    }
                    break;
                case 2:
                    Toast.makeText(getActivity(), "取消成功", Toast.LENGTH_SHORT).show();
                    getMyCollectionList();
                    break;
            }
        }
    };

    void initAdapterForList() {
        adapter = new FavoriteListViewAdapter(this, SelfApplication.getContext(), list);
        lvFavorite.setAdapter(adapter);
    }

}
