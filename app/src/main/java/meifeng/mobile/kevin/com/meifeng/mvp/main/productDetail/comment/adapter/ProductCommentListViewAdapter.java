package meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.comment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.ShopActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public class ProductCommentListViewAdapter extends BaseAdapter {

    private List<CommentModel> listItem;
    private ProductCommentActivity act;
    private LayoutInflater mInflater;

    public ProductCommentListViewAdapter(ProductCommentActivity context, List<CommentModel> listItem) {
        this.act = context;
        this.listItem = listItem;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItem.size() ;
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
            convertView = mInflater.inflate(R.layout.product_comment_listview_item_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        CommentModel model = listItem.get(position);
//        if (model != null) {
//
//        }
        return convertView;
    }

    class ViewHolder {

        ImageView ivUserHead;
        TextView tvUserName;
        TextView tvCommentContent;
        TextView tvCommentTime;

        public ViewHolder(View itemView) {
            ivUserHead = itemView.findViewById(R.id.iv_user_head);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvCommentContent = itemView.findViewById(R.id.tv_comment_content);
            tvCommentTime = itemView.findViewById(R.id.tv_comment_time);
        }
    }
}
