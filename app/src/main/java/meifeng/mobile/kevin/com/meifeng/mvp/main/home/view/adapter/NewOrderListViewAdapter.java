package meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.fragment.NewOrderFragment;
import meifeng.mobile.kevin.com.meifeng.utils.NumberUtil;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

/**
 * Created by kevin.w on 2018/5/3.
 */
public class NewOrderListViewAdapter extends BaseAdapter {

    private List<DecorateOrderModel> listItem;
    private Context context;
    private LayoutInflater mInflater;

    public NewOrderListViewAdapter(Context context, List<DecorateOrderModel> listItem) {
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
            convertView = mInflater.inflate(R.layout.neworder_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.btn_robbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewOrderFragment.instance.robbingNow(listItem.get(position).getID() + "");
            }
        });

        // 初始化value
        DecorateOrderModel model = listItem.get(position);
        if (model != null) {

            // 显示图片
            if (model.getImages() != null && model.getImages().size() > 0) {
                Glide.with(SelfApplication.getContext()).load(OkHttpApi.sevenNiuDomain + model.getImages().get(0)).error(R.mipmap.img_small_def).into(holder.ivImage);
            }

            holder.tvUseName.setText(model.getUserName() + "");
            holder.tvUserScore.setText("综合评分：4.9 分");
            try {
                holder.tvDateTime.setText(UtilsMethod.getDateWithFormat("yyyy-MM-dd HH:mm:ss", model.getCreatedDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.tvTitle.setText(model.getTitle());
            holder.tvComment.setText(model.getComment());

            float totalPrice = model.getWorkPrice();
            if (model.isHasOtherPrice()) {// 是否有感谢费
                totalPrice += model.getOtherPrice();
            }

            if (model.isHasOtherPrice()) {
                String otherPriceTemp = NumberUtil.getNormalNumber(model.getOtherPrice() + "");
                holder.tvTotalPrice.setText("¥ " + NumberUtil.getNormalNumber(totalPrice + "") + "" + "  (含感谢费 ¥ " + otherPriceTemp + ")" +"");
            } else {
                holder.tvTotalPrice.setText("¥ " + NumberUtil.getNormalNumber(totalPrice + "") + "");
            }
            holder.tvLocation.setText(model.getProvince() + "-" + model.getCity());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvUseName;
        TextView tvUserScore;
        TextView tvDateTime;
        TextView tvTitle;
        TextView tvComment;
        TextView tvLocation;
        TextView tvTotalPrice;

        Button btn_robbing;

        public ViewHolder(View itemView) {
            ivImage = itemView.findViewById(R.id.iv_image);
            tvUseName = itemView.findViewById(R.id.tv_user_name);
            tvUserScore = itemView.findViewById(R.id.tv_user_score);
            tvDateTime = itemView.findViewById(R.id.tv_date_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            btn_robbing = (Button) itemView.findViewById(R.id.btn_robbing);
        }
    }

}
