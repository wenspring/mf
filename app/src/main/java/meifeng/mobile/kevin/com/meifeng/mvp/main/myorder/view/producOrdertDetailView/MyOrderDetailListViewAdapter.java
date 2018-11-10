package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.producOrdertDetailView;

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
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.ShopingOrderItemModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.ShopsModelNet;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;

public class MyOrderDetailListViewAdapter extends BaseAdapter {

    private List<ShopsModelNet> listItem;
    private ProductOrderDetailActivity act;
    private LayoutInflater mInflater;

    public MyOrderDetailListViewAdapter(ProductOrderDetailActivity context, List<ShopsModelNet> listItem) {
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
            convertView = mInflater.inflate(R.layout.create_order_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopsModelNet model = listItem.get(position);
        if (model.getShopingOrderItemDtos().size() > 0) {
            ShopingOrderItemModelNet modelNet = model.getShopingOrderItemDtos().get(0);

            Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + "" + modelNet.getImage()).error(R.mipmap.img_small_def).into(holder.ivProductImg);

            holder.tvProductName.setText(modelNet.getProductName() + "");
            holder.tvProductPrice.setText("¥ " + NumberUtil.getNormalNumber(modelNet.getPrice() + ""));
            holder.tvProductCount.setText("x " + modelNet.getQuantity() + "");
        }

        return convertView;
    }

    class ViewHolder {

        ImageView ivProductImg;
        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductCount;

        public ViewHolder(View itemView) {
            ivProductImg = itemView.findViewById(R.id.iv_product_img);
            tvProductName = itemView.findViewById(R.id.tv_name);
            tvProductPrice = itemView.findViewById(R.id.tv_price);
            tvProductCount = itemView.findViewById(R.id.tv_count);
        }
    }
}
