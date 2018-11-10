package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.MyOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.ShopingOrderItemModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.ShopsModelNet;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

/**
 * Created by kevin.w on 2018/5/3.
 */
public class MyOrderListViewAdapter extends BaseAdapter {

    private List<MyOrderModelNet> listItem;
    private Context context;
    private LayoutInflater mInflater;

    public MyOrderListViewAdapter(Context context, List<MyOrderModelNet> listItem) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.myorder_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyOrderModelNet myOrderModelNet = listItem.get(position);

        if (myOrderModelNet != null) {
            holder.tvOrderId.setText("订单编号 : " + myOrderModelNet.getID() + "");
        }

        // 订单状态
        int status = myOrderModelNet.getStatus();
        holder.tvOrderStatus.setText(myOrderModelNet.getStatusDesc());
        holder.tvOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.font_color_red));
        switch (status) {
            case 0: // 已下单，未付款

                break;
            case 1:

                break;
            case 3:// 已完成收货，待评价
                holder.tvOrderStatus.setText("已收货");
                break;
            case 5: //已取消
                holder.tvOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.font_color_999));
                break;
        }

        try {
            String dateString = UtilsMethod.getDateWithFormat("yyyy-MM-dd HH:mm:ss", myOrderModelNet.getCreatedDate());
            holder.tvOrderDateTime.setText(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String content = "";
        float totalPrice = 0.0f;
        if (myOrderModelNet.getShops() != null) {
            for (int i = 0; i < myOrderModelNet.getShops().size(); i++) {

                if (i > 0) {
                    content += "\n";  //换行
                }
                if (myOrderModelNet.getShops().size() > 0) {
                    ShopsModelNet tempShopModel = myOrderModelNet.getShops().get(i);
                    if (tempShopModel != null) {
                        ShopingOrderItemModelNet tempProductModel = tempShopModel.getShopingOrderItemDtos().get(0);
                        if (tempProductModel != null) {
                            content += "" + tempProductModel.getProductName() + "      " + "x " + tempProductModel.getQuantity();

                            totalPrice += tempProductModel.getPrice();
                        }
                    }
                }

            }
        }

        holder.tvOrderContent.setText("" + content);
        holder.tvOrdrTotalPrice.setText("¥ " + totalPrice);

        return convertView;
    }

    class ViewHolder {

        TextView tvOrderId;
        TextView tvOrderDateTime;
        TextView tvOrderContent;
        TextView tvOrdrTotalPrice;
        TextView tvOrderStatus;

        public ViewHolder(View itemView) {
            tvOrderId = itemView.findViewById(R.id.tv_order_no);
            tvOrderDateTime = itemView.findViewById(R.id.tv_order_datetime);
            tvOrderContent = itemView.findViewById(R.id.tv_product_content);
            tvOrdrTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
        }
    }

}
