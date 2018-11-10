package meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.TreeMap;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.mvp.login.model.UserInfoModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.view.SelfActivity;
import okhttp3.internal.Util;

/**
 * Created by kevin.w on 2018/5/3.
 */
public class MyInfoListViewAdapter extends BaseAdapter {

    private UserInfoModel userInfoModel;
    private Context context;
    private LayoutInflater mInflater;

    public MyInfoListViewAdapter(Context context, UserInfoModel userInfoModel) {
        this.context = context;
        this.userInfoModel = userInfoModel;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return userInfoModel;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.myinfo_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (position) {
            case 0:
                holder.tvLeftTitle.setText("手机号码");
                //holder.tvRightValue.setText("" + userInfoModel.getMobile());
                holder.tvRightValue.setText("" + SelfApplication.user.getMobile());
                break;
            case 1:
                holder.tvLeftTitle.setText("我的金额");
                holder.tvRightValue.setText("" + 0.0);
                break;
            case 2:
                holder.tvLeftTitle.setText("信用分值");
                holder.tvRightValue.setText("" + 0.0);
                break;
            case 3:
                holder.tvLeftTitle.setText("我的证书");
                holder.tvRightValue.setText("已认证");
                break;
            case 4:
                holder.tvLeftTitle.setText("身份认证");
                holder.tvRightValue.setText("已认证");
                break;
            case 5:
                holder.tvLeftTitle.setText("注册类型");
                holder.tvRightValue.setText("" + (userInfoModel.isBusiness() == true ? "商户" : "普通用户"));
                break;
        }

        return convertView;
    }

    class ViewHolder {

        TextView tvLeftTitle;
        TextView tvRightValue;

        public ViewHolder(View itemView) {
            tvLeftTitle = itemView.findViewById(R.id.tv_left_title);
            tvRightValue = itemView.findViewById(R.id.tv_right_value);

        }
    }

}
