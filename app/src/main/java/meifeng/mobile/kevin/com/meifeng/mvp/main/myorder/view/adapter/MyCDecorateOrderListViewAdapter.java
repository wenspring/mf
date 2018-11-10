package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

public class MyCDecorateOrderListViewAdapter extends BaseAdapter {

    private ArrayList<DecorateOrderModel> listItem;
    private Context act;
    private LayoutInflater mInflater;

    public MyCDecorateOrderListViewAdapter(Context context, ArrayList<DecorateOrderModel> listItem) {
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
            convertView = mInflater.inflate(R.layout.my_c_decorate_order_listview_item_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DecorateOrderModel model = listItem.get(position);
        if (model != null) {
            if (model.getImages() != null && model.getImages().size() > 0) {
                Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + model.getImages().get(0)).error(R.mipmap.img_small_def).into(holder.ivImg);
            }
            holder.tvName.setText(model.getTitle());
            try {
                holder.tvDateTime.setText(UtilsMethod.getDateWithFormat("yyyy-MM-dd Hh:mm:ss", model.getCreatedDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // price
            float total = model.getWorkPrice();
            if (model.isHasOtherPrice()) {
                total += model.getOtherPrice();

                holder.tvPrice.setText("¥ " + NumberUtil.getNormalNumber(total + "") + "  (含感谢费: ¥ )" + NumberUtil.getNormalNumber(model.getOtherPrice() + "") + "");
            } else {
                holder.tvPrice.setText("¥ " + NumberUtil.getNormalNumber(total + ""));
            }

            holder.btnStatus.setText(model.getStatusDesc());

            int status = Integer.parseInt(model.getStatus());
            switch (status) {
                case 6:
                    holder.btnStatus.setBackgroundColor(act.getResources().getColor(R.color.font_color_999));
                    break;
                default:
                    holder.btnStatus.setBackgroundColor(act.getResources().getColor(R.color.btn_bg_color));
                    break;
            }

        }

        return convertView;
    }

    class ViewHolder {
        Button bDel;
        ImageView ivImg;
        TextView tvName;
        TextView tvPrice;
        TextView tvDateTime;
        Button btnStatus;

        public ViewHolder(View itemView) {
            bDel = itemView.findViewById(R.id.btn_del);
            ivImg = itemView.findViewById(R.id.iv_collect_img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDateTime = itemView.findViewById(R.id.tv_datetime);
            btnStatus = itemView.findViewById(R.id.btn_status);
        }
    }
}
