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
public class GridProductListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MallModel.productModel> listItem;
    private Context context;

    public GridProductListAdapter(Context context, List<MallModel.productModel> listItem) {
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
            convertView = mInflater.inflate(R.layout.product_list_gvitem, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MallModel.productModel model = listItem.get(position);
        if (model != null) {
            viewHolder.tvDesc.setText(model.getName());
            viewHolder.tvPrice.setText("¥ " + model.getPrice() + "");
            Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + model.getThumbNail()).error(R.mipmap.img_small_def).into(viewHolder.ivProduct);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView ivProduct;
        TextView tvDesc;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvPrice = itemView.findViewById(R.id.tv_price);

            int sWidth = ViewUtil.getScreenWidth(context);
            // 设置ImageView width height
            ViewGroup.LayoutParams ps = ivProduct.getLayoutParams();
            int vWidth = sWidth;//- 5 * 2 + 5 * 3;
            ps.width = vWidth / 3;
            ps.height = vWidth / 3;
            ivProduct.setLayoutParams(ps);
        }
    }

}