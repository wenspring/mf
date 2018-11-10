package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.view.ViewUtil;

/**
 * Created by kevin.w on 2018/4/15.
 */
public class GridBrandListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MallModel.brandModel> listItem;
    private Context context;

    public GridBrandListAdapter(Context context, List<MallModel.brandModel> listItem) {
        this.context = context;
        this.listItem = listItem;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.brand_list_gvitem, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MallModel.brandModel model = listItem.get(position);
        if (model != null) {
            // 显示品牌名称
            viewHolder.tvBrandName.setText(model.getName());
            // 显示品牌logo
            Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + model.getPhoto()).error(R.mipmap.img_small_def).into(viewHolder.ivBrand);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivBrand;
        TextView tvBrandName;

        public ViewHolder(View itemView) {
            ivBrand = itemView.findViewById(R.id.iv_brand);
            tvBrandName = itemView.findViewById(R.id.tv_brand_name);

            int sWidth = ViewUtil.getScreenWidth(context);
            // 设置ImageView width height
            ViewGroup.LayoutParams ps = ivBrand.getLayoutParams();
            int vWidth = sWidth;//- 5 * 2 + 5 * 3;
            ps.width = vWidth / 5 / 2;
            ps.height = vWidth / 5 / 2;
            ivBrand.setLayoutParams(ps);
        }
    }

}
