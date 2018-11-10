package meifeng.mobile.kevin.com.meifeng.mvp.main.brand;

import android.content.Intent;
import android.gesture.GestureLibraries;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.ProductDetailActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.ShoppingMallActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.adapter.GridProductListAdapter;
import okhttp3.Call;
import okhttp3.Response;

public class BrandActivity extends BaseBindActivity {

    MallModel.brandModel brandModel;
    //ToolBar toolBar;
    private GridView gvBrandProduct;
    GridProductListAdapter adapterProduct;

    @BindView(id = R.id.iv_brand_img)
    private ImageView ivBrandImg;

    @BindView(id = R.id.tv_brand_name)
    private TextView tvBrandName;

    @BindView(id = R.id.tv_brand_desc)
    private TextView tvBrandDesc;

    ArrayList<MallModel.productModel> productModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        initData();
        setupUI();

        getProductByBrand();
    }

    void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            brandModel = (MallModel.brandModel) intent.getSerializableExtra("k_brand");

            Glide.with(this).load(OkHttpApi.sevenNiuDomain + brandModel.getPhoto()).error(R.mipmap.img_small_def).into(ivBrandImg);
        }
    }

    void setupUI() {

        gvBrandProduct = findViewById(R.id.gv_brand_product);
        gvBrandProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MallModel.productModel model = productModels.get(position);
                model.setId(model.getID());
                Intent intent = new Intent();
                intent.putExtra("k_product", model);
                intent.setClass(BrandActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }
        });

        //adapterProduct = new GridProductListAdapter(BrandActivity.this, list);
//        toolBar = (ToolBar) findViewById(R.id.toolbar);
//        toolBar.setBackListener(new onBackListener() {
//            @Override
//            public void onToolbarBackClick() {
//                finish();
//            }
//        });
//        toolBar.setTitle("品牌・"+brandModel.getName());

        if (brandModel != null) {
            tvBrandDesc.setText(brandModel.getComment());
            tvBrandName.setText("品牌・" + brandModel.getName());
        }
    }

    void getProductByBrand() {
        OkHttpApi.getProdcutListByBrandId(brandModel.getId(), 1 + "", 15 + "", new CallBackForOk<ArrayList<MallModel.productModel>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ArrayList<MallModel.productModel> response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "数据加载失败"), 0);
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
                    showToastMsg("" + msg.obj);
                    break;
                case 1:

                    if (msg.obj != null) {
                        productModels = (ArrayList<MallModel.productModel>) msg.obj;
                        if (productModels.size() == 0) {
                            showToastMsg("该品牌没有商品");
                        } else {
                            updateProduct();
                        }
                    }

                    break;
            }
        }
    };

    // 刷新列表显示商品
    void updateProduct() {
        adapterProduct = new GridProductListAdapter(BrandActivity.this, productModels);
        gvBrandProduct.setAdapter(adapterProduct);
    }

}
