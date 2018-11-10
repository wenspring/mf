package meifeng.mobile.kevin.com.meifeng.mvp.main.shop.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.ShopActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model.ShopModel;

public class ShopInfoListViewAdapter extends BaseAdapter {

    private ShopModel shopModel;
    private ShopActivity act;
    private LayoutInflater mInflater;

    public ShopInfoListViewAdapter(ShopActivity context, ShopModel shopModel) {
        this.act = context;
        this.shopModel = shopModel;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return shopModel;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shop_info_listview_item_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.wvShopDetail.setVisibility(View.GONE);

        switch (position) {
            case 0:
                holder.tvShopTitle.setText("店铺名称");
                holder.tvShopValue.setText("" + shopModel.getName());
                break;
            case 1:
                holder.tvShopTitle.setText("店铺掌柜");
                holder.tvShopValue.setText("" + shopModel.getBossName());
                break;
            case 2:
                holder.tvShopTitle.setText("所在地");
                holder.tvShopValue.setText("" + shopModel.getCity());
                break;
            case 3:
                holder.tvShopTitle.setText("店铺编号");
                holder.tvShopValue.setText("" + shopModel.getID());
                break;
            case 4:
                holder.tvShopTitle.setText("规模");
                holder.tvShopValue.setText("" + shopModel.getSize());
                break;
            case 5:
                holder.tvShopTitle.setText("联系方式");
                holder.tvShopValue.setText("" + shopModel.getMobile());
                break;
            case 6:
                holder.tvShopTitle.setText("收藏");
                holder.tvShopValue.setText("" + shopModel.getCollectionCount());
                break;
            case 7:
                if (!TextUtils.isEmpty(shopModel.getDetailDesc())) {
                    String htmlData = null;
                    try {
                        htmlData = URLDecoder.decode(shopModel.getDetailDesc(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    L.print("html data", htmlData);
                    holder.wvShopDetail.loadData(htmlData, "text/html; charset=UTF-8", null);
                    holder.wvShopDetail.setVisibility(View.VISIBLE);
                } else {
                    holder.wvShopDetail.setVisibility(View.GONE);
                }
                break;
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvShopTitle;
        TextView tvShopValue;
        WebView wvShopDetail;

        public ViewHolder(View itemView) {
            tvShopTitle = itemView.findViewById(R.id.tv_shop_title);
            tvShopValue = itemView.findViewById(R.id.tv_shop_value);
            wvShopDetail = itemView.findViewById(R.id.wv_introduce);
        }
    }
}
