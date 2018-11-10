package meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.FavoriteActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.fragment.CollectionProductFragment;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model.FavoriteModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.view.ShoppingCartActivity;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

public class FavoriteListViewAdapter extends BaseAdapter {

    private List<FavoriteModel> listItem;
    private CollectionProductFragment act;
    private LayoutInflater mInflater;

    public FavoriteListViewAdapter(CollectionProductFragment fragment, Context context, List<FavoriteModel> listItem) {
        this.act = fragment;
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
            convertView = mInflater.inflate(R.layout.favorite_listview_item_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FavoriteModel model = listItem.get(position);

        if (model != null) {

            L.print("gggg=====", model.toString());
            if (model.getProduct() != null) {
                Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + model.getProduct().getThumbNail()).error(R.mipmap.img_loading).into(holder.ivImg);
                holder.tvPrice.setText("¥ " + NumberUtil.getNormalNumber(model.getProduct().getPrice()));
            }

            holder.tvName.setText(model.getProductName() + "");


            try {
                holder.tvDateTime.setText(UtilsMethod.getDateWithFormat("yyyy-MM-dd HH:mm:ss", model.getCreatedDate() + ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            if (model.isDel()) {
//                holder.bDel.setVisibility(View.VISIBLE);
//            } else {
//                holder.bDel.setVisibility(View.GONE);
//            }
        }

//        holder.bDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //act.deleteItem(position);
//            }
//        });

        return convertView;
    }

    class ViewHolder {
        Button bDel;
        ImageView ivImg;
        TextView tvName;
        TextView tvPrice;
        TextView tvDateTime;

        public ViewHolder(View itemView) {
            bDel = itemView.findViewById(R.id.btn_del);
            ivImg = itemView.findViewById(R.id.iv_collect_img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDateTime = itemView.findViewById(R.id.tv_datetime);
        }
    }
}
