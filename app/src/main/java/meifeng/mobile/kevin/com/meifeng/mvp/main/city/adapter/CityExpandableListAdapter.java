package meifeng.mobile.kevin.com.meifeng.mvp.main.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.mvp.main.city.model.CityModelNet;

/**
 * Created by kevin.w on 2018/4/5.
 */
public class CityExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<CityModelNet.ProvinceModel> list;
    //private Map<Integer, List<PODetailModel.PoList.ItemList>> poitemlist_child;
    private LayoutInflater mInflater;

    public CityExpandableListAdapter(Context context, ArrayList<CityModelNet.ProvinceModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        //this.polist_group = polist_group;
    }

    /**
     * 分組個數
     */
    @Override
    public int getGroupCount() {
        return list.size();
    }

    /**
     * 分組下，子元素的個數
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getCities().size();
    }

    /**
     * 組顯示數據
     *
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    /**
     * 子元素數據 item
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return list.get(groupPosition).getCities().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * / Indicates whether the child and group IDs are stable across changes to the underlying data
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * // Whether the child at the specified position is selectable
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * griup View
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup = null;
        final CityModelNet.ProvinceModel groupModel = list.get(groupPosition);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_list_item, null);

            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.tv_shengfen = (TextView) convertView.findViewById(R.id.tv_shengfeng);

            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }

        viewHolderGroup.tv_shengfen.setText(groupModel.getName());

        return convertView;
    }

    /**
     * child view
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild = null;
        CityModelNet.ProvinceModel.CityModel childItemModel = list.get(groupPosition).getCities().get(childPosition);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_list_child_item, null);

            viewHolderChild = new ViewHolderChild();
            viewHolderChild.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }

        // 數據顯示
        viewHolderChild.tv_city.setText(childItemModel.getName());
        return convertView;
    }

    /**
     * ViewHolder for group
     */
    private class ViewHolderGroup {
        TextView tv_shengfen;
    }

    /**
     * ViewHolder for child
     */

    private class ViewHolderChild {
        TextView tv_city;
    }
}

