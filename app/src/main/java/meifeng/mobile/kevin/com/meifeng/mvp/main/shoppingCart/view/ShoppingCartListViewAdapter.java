package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.adapter.MyOrderListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;

public class ShoppingCartListViewAdapter extends BaseAdapter {

    private List<ShoppingCartModel> listItem;
    private ShoppingCartActivity act;
    private LayoutInflater mInflater;

    public ShoppingCartListViewAdapter(ShoppingCartActivity context, List<ShoppingCartModel> listItem) {
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
            convertView = mInflater.inflate(R.layout.shopping_cart_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShoppingCartModel model = listItem.get(position);

        if (model != null) {
            if (model.isDel()) {
                holder.btnDel.setVisibility(View.VISIBLE);
            } else {
                holder.btnDel.setVisibility(View.GONE);
            }
        }


        MallModel.productModel productModel = model.getProductModel();

        Glide.with(act).load(OkHttpApi.sevenNiuDomain + "" + productModel.getBigPhoto()).error(R.mipmap.img_small_def).into(holder.ivProductImg);

        holder.tvProductName.setText(productModel.getName() + "");
        holder.tvProductprice.setText("¥ " + NumberUtil.getNormalNumber(productModel.getPrice() + ""));
        holder.tvProductCount.setText(productModel.getCountForCart() + "");

        // 数量-1
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productModel.getCountForCart() > 1) {
                    act.opCartProductCount(-1, position);
                }
            }
        });

        // 数量+1
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.opCartProductCount(1, position);
            }
        });

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.deleteItem(position);
            }
        });

        holder.tvProductShopBrand.setText(model.getProductModel().getShopName() + "-" + model.getProductModel().getClassificationName());
        return convertView;
    }

    class ViewHolder {

        ImageView ivProductImg;
        TextView tvProductName;
        TextView tvProductprice;
        TextView tvProductCount;
        Button btnRemove;
        Button btnAdd;
        TextView tvProductShopBrand;
        Button btnDel;

        public ViewHolder(View itemView) {
            ivProductImg = itemView.findViewById(R.id.iv_product_img);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductprice = itemView.findViewById(R.id.tv_product_price);
            tvProductCount = itemView.findViewById(R.id.tv_product_count);
            btnRemove = itemView.findViewById(R.id.btn_remove_count);
            btnAdd = itemView.findViewById(R.id.btn_add_count);
            tvProductShopBrand = itemView.findViewById(R.id.tv_product_shop_brand);
            btnDel = itemView.findViewById(R.id.btn_del); // delete
        }
    }
}
