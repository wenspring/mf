package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.adapter;

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
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view.CreateOrderActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;

public class CreateOrderListViewAdapter extends BaseAdapter {

    private List<ShoppingCartModel> listItem;
    private CreateOrderActivity act;
    private LayoutInflater mInflater;

    public CreateOrderListViewAdapter(CreateOrderActivity context, List<ShoppingCartModel> listItem) {
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

        ShoppingCartModel model = listItem.get(position);
        MallModel.productModel productModel = model.getProductModel();

        Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + "" + productModel.getBigPhoto()).error(R.mipmap.img_small_def).into(holder.ivProductImg);

        holder.tvProductName.setText(productModel.getName() + "");
        holder.tvProductPrice.setText("¥ " + NumberUtil.getNormalNumber(productModel.getPrice() + ""));
        holder.tvProductCount.setText("x "+productModel.getCountForCart() + "");


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
