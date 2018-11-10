package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.ProductDetailActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.view.ViewUtil;

public class VPageFragment extends Fragment implements View.OnClickListener {

    View baseView;
    private ImageView ivViewPageImg;
    private String pid;
    private String imgKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseView = inflater.inflate(R.layout.fragment_vpage, container, false);
        findViewById();

        imgKey = getArguments().getString("imgKey");
        pid = getArguments().getString("pid");

        Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + "" + imgKey).error(R.mipmap.img_loading).into(ivViewPageImg);

        return baseView;
    }

    public static VPageFragment newInstance(String imgKey, String pid) {
        Bundle args = new Bundle();
        args.putString("imgKey", imgKey);
        args.putString("pid", pid);
        VPageFragment fragment = new VPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

//        pid = args.getString("pid");
//        imgKey = args.getString("imgKey");
//
//        ivViewPageImg.setBackgroundResource(R.mipmap.testproduct);
        //Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + "" + imgKey).error(R.mipmap.img_loading).into(ivViewPageImg);
    }

    void findViewById() {
        ivViewPageImg = baseView.findViewById(R.id.iv_top_show);
        ivViewPageImg.setOnClickListener(this);
        // 图片正常显示
        ViewUtil.setImageViewNormalShow(ivViewPageImg, this.getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_top_show:
//                MallModel.productModel productModel = new MallModel().getProductModel();
//                productModel.setID(pid);
//                Intent intent = new Intent();
//                intent.setClass(this.getActivity(), ProductDetailActivity.class);
//                intent.putExtra("k_product", productModel);
//                startActivity(intent);
                break;
        }
    }
}
