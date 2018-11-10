package meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;

/**
 * Created by kevin.w on 2018/4/15.
 */
public class LeftMenuListItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> listItem;
    private Context context;

    public LeftMenuListItemAdapter(Context context, List<String> listItem) {
        this.context = context;
        this.listItem = listItem;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItem.size() + 5;
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
            convertView = mInflater.inflate(R.layout.leftmenu_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (position) {
            case 0: {
                viewHolder.tvItemName.setText("已接订单");
                viewHolder.ivItemIcon.setBackgroundResource(R.mipmap.icon_my_decorate);
            }
            break;
            case 1: {
                viewHolder.tvItemName.setText("商品订单");
                viewHolder.ivItemIcon.setBackgroundResource(R.mipmap.icon_my_order);
            }
            break;
            case 2: {
                viewHolder.tvItemName.setText("购物车    ");
                viewHolder.ivItemIcon.setBackgroundResource(R.mipmap.icon_my_car);
            }
            break;
            case 3: {
                viewHolder.tvItemName.setText("我的收藏");
                viewHolder.ivItemIcon.setBackgroundResource(R.mipmap.icon_my_favorite);
            }
            break;
            case 4: {
                viewHolder.tvItemName.setText("个人设置");
                viewHolder.ivItemIcon.setBackgroundResource(R.mipmap.icon_setting);
            }
            break;
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivItemIcon;
        TextView tvItemName;

        public ViewHolder(View itemView) {
            ivItemIcon = itemView.findViewById(R.id.iv_item_img);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }
    }

}
