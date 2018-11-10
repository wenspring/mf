package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.ShopActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public class ChooseMaterilListViewAdapter extends BaseAdapter {

    private List<MallModel.productModel> listItem;
    private ShopActivity act;
    private LayoutInflater mInflater;

    public ChooseMaterilListViewAdapter(ShopActivity context, List<MallModel.productModel> listItem) {
        this.act = context;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.choose_materil_listview_item_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MallModel.productModel productModel = listItem.get(position);
        if (productModel != null) {
//            Glide.with(act).load(OkHttpApi.sevenNiuDomain + brandModel.getPhoto()).error(R.mipmap.img_loading).into(holder.ivBrandPhoto);
//            holder.tvBrandComment.setText(brandModel.getComment());
//            holder.tvBrandName.setText(brandModel.getName());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView ivBrandPhoto;
        TextView tvBrandName;
        TextView tvBrandComment;

        public ViewHolder(View itemView) {
            ivBrandPhoto = itemView.findViewById(R.id.iv_brand_photos);
            tvBrandName = itemView.findViewById(R.id.tv_brand_name);
            tvBrandComment = itemView.findViewById(R.id.tv_brand_comment);
        }
    }
}
