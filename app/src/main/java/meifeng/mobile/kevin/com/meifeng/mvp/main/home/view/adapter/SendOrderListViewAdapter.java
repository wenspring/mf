package meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.adapter;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.SendOrderFragment;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

/**
 * Created by kevin.w on 2018/5/3.
 */
public class SendOrderListViewAdapter extends BaseAdapter {

    private ArrayList<DecorateOrderModel> listItem;
    private Context context;
    private LayoutInflater mInflater;
    SendOrderFragment.CallBackCancelDecorateOrder callBackCancelDecorateOrder;

    public SendOrderListViewAdapter(Context context, ArrayList<DecorateOrderModel> listItem, SendOrderFragment.CallBackCancelDecorateOrder callBackCancelDecorateOrder) {
        this.context = context;
        this.listItem = listItem;
        this.mInflater = LayoutInflater.from(context);
        this.callBackCancelDecorateOrder = callBackCancelDecorateOrder;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sendorder_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DecorateOrderModel model = listItem.get(position);
        if (model != null) {

            if (model.getImages() != null && model.getImages().size() > 0) {
                Glide.with(context).load(OkHttpApi.sevenNiuDomain + model.getImages().get(0)).error(R.mipmap.img_small_def).into(holder.ivPlaceImg);
            } else {
                holder.ivPlaceImg.setBackgroundResource(R.mipmap.img_loading);
            }

            holder.tvTitle.setText(model.getTitle());
            try {
                String dateStr = UtilsMethod.getDateWithFormat("yyyy-MM-dd HH:mm:ss", model.getCreatedDate());
                holder.tvCreteTime.setText(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (model.isHasOtherPrice()) {
                String otherPriceTemp = NumberUtil.getNormalNumber(model.getOtherPrice() + "");
                holder.tvWorkPrice.setText("¥ " + NumberUtil.getNormalNumber(model.getWorkPrice() + "") + "  (含感谢费 ¥ " + otherPriceTemp + ")" + "");
            } else {
                holder.tvWorkPrice.setText("¥ " + NumberUtil.getNormalNumber(model.getWorkPrice() + ""));
            }

            if (model.isHasMaterial()) {
                holder.tvChooseStatus.setText("[已选装修材料]  地点: " + model.getProvince() + "-" + model.getCity());
            } else {
                holder.tvChooseStatus.setText("[未选装修材料]  " + model.getComment());
            }

            if (Integer.parseInt(model.getStatus()) == 1) {
                holder.tvPayStatus.setTextColor(context.getResources().getColor(R.color.font_color_red));
            } else {
                holder.tvPayStatus.setTextColor(context.getResources().getColor(R.color.font_color_666));
            }
            holder.tvPayStatus.setText(model.getStatusDesc()); // 付款状态
        }

        holder.bOp.setText("取消发布");
        holder.bOp.setEnabled(true);

        if (!TextUtils.isEmpty(model.getStatus())) {
            int status = Integer.parseInt(model.getStatus());
            switch (status) {
                case 1:// 已发布， 未付款
                    holder.bOp.setText("取消发布");
                    holder.bOp.setEnabled(true);
                    holder.bOp.setBackgroundColor(context.getResources().getColor(R.color.font_color_red));
                    break;
                case 2:// 已付款
                    holder.bOp.setText("待接单");
                    holder.bOp.setEnabled(false);
                    holder.bOp.setBackgroundColor(context.getResources().getColor(R.color.app_main_nav_color));
                    break;
                case 3:// 已接单
                    holder.bOp.setText("待装修");
                    holder.bOp.setEnabled(false);
                    holder.bOp.setBackgroundColor(context.getResources().getColor(R.color.btn_bg_color));
                    break;
                case 5:// 已取消
                    holder.bOp.setText("已取消");
                    holder.bOp.setEnabled(false);
                    holder.bOp.setBackgroundColor(context.getResources().getColor(R.color.color_black));
                    break;
                case 6:// 已完成
                    holder.bOp.setText("已完成");
                    holder.bOp.setEnabled(false);
                    holder.bOp.setBackgroundColor(context.getResources().getColor(R.color.font_color_999));
                    break;
                default:
                    holder.bOp.setBackgroundColor(context.getResources().getColor(R.color.font_color_999));
                    break;
            }

        }
        // 操作
        holder.bOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackCancelDecorateOrder != null) {
                    callBackCancelDecorateOrder.cancelDecorateOrder(listItem.get(position).getID());
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView ivPlaceImg;
        TextView tvTitle;
        TextView tvCreteTime;
        TextView tvChooseStatus;
        TextView tvPayStatus;
        TextView tvWorkPrice;
        Button bOp;

        public ViewHolder(View itemView) {
            ivPlaceImg = itemView.findViewById(R.id.iv_place_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCreteTime = itemView.findViewById(R.id.tv_create_time);
            tvChooseStatus = itemView.findViewById(R.id.tv_choose_status);
            tvPayStatus = itemView.findViewById(R.id.tv_pay_status);
            tvWorkPrice = itemView.findViewById(R.id.tv_work_price);
            bOp = itemView.findViewById(R.id.btn_op);
        }
    }


}
