package meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.comment.adapter;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.comment.adapter.ProductCommentListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import okhttp3.Call;
import okhttp3.Response;

public class ProductCommentActivity extends BaseBindActivity {

    ToolBar toolBar;
    ListView lvProductComment;
    ProductCommentListViewAdapter adapter;

    @BindView(id = R.id.iv_product_img)
    private ImageView ivProductImg;

    @BindView(id = R.id.tv_product_name)
    private TextView tvProductName;

    private MallModel.productModel productModel;
    ArrayList<CommentModel> listComment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_comment);

        initData();
        setupUI();

        getProductComment();
    }

    void initData() {
        if (getIntent() != null) {
            Intent i = getIntent();
            productModel = (MallModel.productModel) i.getSerializableExtra("k_product_model");
        }
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        lvProductComment = findViewById(R.id.lv_product_comment);


        if (productModel != null) {
            Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + productModel.getThumbNail()).error(R.mipmap.img_small_def).into(ivProductImg);
            tvProductName.setText(productModel.getName());
        }
    }

    // 获取商品评价列表信息
    void getProductComment() {

        OkHttpApi.getProductCommentList(productModel.getID() + "", new CallBackForOk<ArrayList<CommentModel>>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(ArrayList<CommentModel> response) {
                if (response != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取数据失败"), 0);
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
                    dismissLoading();
                    showToastMsg("" + msg.obj);
                    break;
                case 1:
                    dismissLoading();
                    if (msg.obj != null) {
                        listComment = (ArrayList<CommentModel>) msg.obj;
                        initCommentListView();
                    }

                    if (listComment.size() == 0) {
                        showToastMsg("暂无评论");
                    }
                    break;
            }
        }
    };

    void initCommentListView() {
        adapter = new ProductCommentListViewAdapter(this, listComment);
        lvProductComment.setAdapter(adapter);
    }

}
