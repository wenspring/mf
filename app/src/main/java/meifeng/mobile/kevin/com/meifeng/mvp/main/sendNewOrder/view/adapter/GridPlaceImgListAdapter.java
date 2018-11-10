package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.callback.CallBackRemovePhotoListener;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.PlaceModel;
import meifeng.mobile.kevin.com.meifeng.utils.file.FileUtils;

/**
 * Created by kevin.w on 2018/4/15.
 */
public class GridPlaceImgListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<PlaceModel> listItem;
    private Context context;
    private CallBackRemovePhotoListener callBackRemovePhotoListener;

    public GridPlaceImgListAdapter(Context context, List<PlaceModel> listItem, CallBackRemovePhotoListener callBackRemovePhotoListener) {
        this.context = context;
        this.listItem = listItem;
        this.mInflater = LayoutInflater.from(context);
        this.callBackRemovePhotoListener = callBackRemovePhotoListener;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.place_list_gvitem, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PlaceModel model = listItem.get(position);
        if (model != null) {
            if (model.isAdd()) {
                viewHolder.ivPlaceImg.setBackgroundResource(R.mipmap.icon_add_photo);
                viewHolder.btnRemove.setVisibility(View.GONE);
            } else if (!TextUtils.isEmpty(model.getImgPath())) {
                Bitmap b = FileUtils.decodeSampledBitmapFromFile(model.getImgPath());
                viewHolder.ivPlaceImg.setImageBitmap(b);
                viewHolder.btnRemove.setVisibility(View.VISIBLE);
            }
        }

        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackRemovePhotoListener != null) {
                    callBackRemovePhotoListener.removeImageForUpload(position);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView ivPlaceImg;
        Button btnRemove;

        public ViewHolder(View itemView) {
            ivPlaceImg = itemView.findViewById(R.id.iv_place_img);
            btnRemove = itemView.findViewById(R.id.btn_remove);

//            int sWidth = ViewUtil.getScreenWidth(context);
//            // 设置ImageView width height
//            ViewGroup.LayoutParams ps = ivPlaceImg.getLayoutParams();
//            int vWidth = sWidth;//- 5 * 2 + 5 * 3;
//            ps.width = vWidth / 5 / 2;
//            ps.height = vWidth / 5 / 2;
//            ivPlaceImg.setLayoutParams(ps);
        }
    }

}
